# SQL문 모음

비지니스 로직을 생각하면서 사용 할 SQL을 작성

### member


- 회원가입
```sql
INSERT INTO member (id, name, email, nickname, password) 
VALUES (null, :name, :email, :nickname, :password);
INSERT INTO member_permission (member_id, permission_id) 
VALUES (:member_id, :permission_id);
```

- 로그인
```sql
SELECT password FROM member WHERE nickname = :nickname;
```

- 회원정보 조회
```sql
SELECT id, name, email, nickname, password 
FROM member WHERE nickname = :nickname;
```

- 회원정보 수정
```sql
UPDATE member SET name = :name, email = :email, password = :password
WHERE id = :id;
```



---

### board

- 게시판 목록 보기
```sql
SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.nickname, b.title, b.ip_addr, b.reg_date 
FROM board AS b INNER JOIN member AS m ON b.member_id = m.id 
WHERE category_id = :category_id 
ORDER BY origin_id DESC, reply_seq ASC;
-- origin_id가 같은 것끼리 내림차순(최신글부터)으로 정렬 후 reply_seq에 대해 오름차순 정렬
```

- 게시글 조회: 이름
```sql
-- 2개의 테이블을 조인하는 경우
SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.nickname, b.title, b.ip_addr, b.reg_date 
FROM board AS b INNER JOIN member AS m ON b.member_id = m.id 
WHERE category_id = :category_id AND m.nickname = :m.nickname
ORDER BY b.origin_id DESC, b.reply_seq ASC;
```

- 게시글 조회: 제목
```sql
-- 2개의 테이블을 조인해서 보여주는 경우
SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.nickname, b.title, b.ip_addr, b.reg_date 
FROM board AS b INNER JOIN member AS m ON b.member_id = m.id 
WHERE b.category_id = :b.category_id AND b.title LIKE %:b.title% 
ORDER BY b.origin_id DESC, b.reply_seq ASC;
-- LIKE %:b.title% 의 경우 java 에서 사용할 때에는 약간의 변경이 필요
-- ex) b.title LIKE :b.title --> "%" + criteria.getKeyword() + "%"
```

- 게시글 조회: 내용
```sql
-- 3개의 테이블을 조인하는 방법
SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.nickname, b.title, b.ip_addr, b.reg_date 
FROM board AS b INNER JOIN member AS m ON b.member_id = m.id 
INNER JOIN board_body AS bb ON b.id = bb.id 
WHERE b.category = :b.category AND bb.content LIKE %:bb.content% AND b.is_deleted = 0 
ORDER BY b.origin_id DESC, b.reply_seq ASC;
```

- 게시글 조회: 제목+내용
```sql
-- 3개의 테이블을 조인하는 방법
SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.nickname, b.title, b.ip_addr, b.reg_date 
FROM board AS b INNER JOIN member AS m ON b.member_id = m.id 
INNER JOIN board_body AS bb ON b.id = bb.id 
WHERE b.category=:b.category AND 
(bb.content LIKE %:bb.content% OR b.title LIKE %:b.title%) AND b.is_deleted = 0
ORDER BY b.origin_id DESC, b.reply_seq ASC;
```

- 게시물 상세보기
```sql
SELECT b.id, b.origin_id, b.depth, b.reply_seq, b.category_id, m.nickname, b.title, b.ip_addr, b.reg_date, bb.content 
FROM board AS b INNER JOIN member AS m ON b.member_id = m.id 
INNER JOIN board_body AS bb ON b.id = bb.id 
WHERE bb.id = :b.id;

-- 댓글 List
SELECT c.id, c.parent_comment_id, c.seq, m.name, c.content, c.ip_addr, c.reg_date 
FROM comment AS c INNER JOIN member AS m ON c.member_id = m.id
WHERE board_id = :board_id 
ORDER BY parent_comment_id DESC, seq ASC;
-- parent_comment_id 을 기준으로 내림차순(최신 글이 위에 보이도록)
-- parent_comment_id는 댓글이면 자신의 id와 동일, 대댓글이면 해당 댓글의 고유id를 parent_comment_id로 한다.
-- seq를 기준으로 오름차순(최신 글이 제일 아래에 보이도록)
-- seq를 사용하고 보니 필요한지 의문... 
-- 댓글의 primary key를 기준으로 오름차순 해도 결과는 동일
```

- 게시물 저장
```sql
-- board 와 관련된 내용을 저장하고 board_body에 해당 board_id에 맞는 본문을 저장한다.
INSERT INTO board (id, origin_id, depth, reply_seq, category_id, member_id, title, ip_addr, reg_date) 
VALUES (null, :origin_id, :depth, :reply_seq, :category_id, :member_id, :title, :ip_addr, NOW());

INSERT INTO board_body (id, content) VALUES (:board_id, :content);
```

- 게시물 수정
```sql
UPDATE board SET title = :title, reg_date = NOW(), ip_addr = :ip_addr 
WHERE member_id = :member_id and id = :id;

UPDATE board_body SET content = :content WHERE id = :id;
```

- 특정 id의 글에 답글이 몇개 존재하는지 조사 (개수가 1보다 크면 답글이 존재하는 것으로 판단)
```sql
SELECT COUNT(*) FROM board WHERE origin_id = :id;
-- count 함수는 db성능을 떨어뜨리는 요인이 된다. 다른 방법이 있을지 생각해보자
```

- 게시물 삭제  
  - 게시물을 삭제하면 답글들은 어떻게 해야 하는가?  
```sql
-- 답글이 존재하는 글인 경우
-- 'is_deleted'를 1로 설정하고, JavaScript를 사용하여 목록에서 title은 '삭제된 글입니다'로 보이도록 하고 클릭이 안되도록 하기로 함.
UPDATE board SET is_deleted = 1 WHERE id = :id';

-- 답글이 존재하지 않는 글인 경우
-- 'is_deleted'를 1로 설정함(목록 조회 쿼리문에서 'is_deleted' 값으로 필터링하여 목록에서 아예 보이지 않도록 함).
UPDATE board SET is_deleted = 1 WHERE id = :id';
```



---


- 답글 저장
  상세 페이지에서 '답글' 버튼을 클릭하면 —> 
```sql
-- id가 :id인 row의 origin_id, depth와 reply_seq를 구한다.
-- 즉, 현재 상세페이지에서 보고있는 글의 origin_id, depth와 reply_seq를 구한다.
SELECT origin_id, depth, reply_seq FROM board 
WHERE id = :id;
-- 구한 origin_id, depth와 reply_seq를 Java 코드에서 변수에 저장
```

```sql
-- 이미 작성된 답글들 사이에 새로운 답글을 넣어야 할 경우, 같은 origin_id를 갖는 하단 답글들의 reply_seq들을 모두 UPDATE해야 한다.
-- 특정 origin_id에 해당하는 row들 중 특정 reply_seq보다 큰 모든 reply_seq들을 1씩 증가시킴
UPDATE board SET reply_seq = reply_seq + 1
WHERE origin_id = :origin_id AND reply_seq > :reply_seq;
```

```sql
-- 위 SELECT문에서 구한 값들을 사용하여 'board' 테이블에 삽입하는 쿼리문
INSERT INTO board (id, origin_id, depth, reply_seq, category_id, member_id, title, ip_addr, reg_date) 
VALUES (null, :origin_id, :depth, :reply_seq, :category_id, :member_id, :title, :ip_addr, :reg_date);
-- :origin_id에는 위의 SELECT 문에서 구한 origin_id를 매핑
-- :depth에는 위의 SELECT문에서 구한 depth에 1을 증가시킨 값을 매핑
-- :reply_seq에는 위에서 저장해놓은 reply_seq 값에 1을 증가시킨 값을 매핑
```
---



- 댓글 저장
```sql
INSERT INTO comment (id, board_id, parent_comment_id, seq, member_id, content, ip_addr, reg_date) 
VALUES (null, :board_id, :parent_comment_id, :seq, :member_id, :content, :ip_addr, NOW());
```

- 댓글 수정
```sql
UPDATE comment SET content = :content, reg_date = :reg_date, ip_addr = :ip_addr 
WHERE member_id = :member_id and id = :id;
```

- 특정 id의 댓글에 대댓글이 몇개 존재하는지 조사 (개수가 1보다 크면 대댓글이 존재하는 것으로 판단)
```sql
SELECT COUNT(*) FROM comment WHERE parent_comment_id = :id;
```

- 댓글 삭제  
  if (대댓글이 존재하는 댓글이면): 'is_deleted'를 1로 설정하고, JavaScript를 사용하여 목록에서 content를 '삭제된 댓글입니다'로 보이도록 하기로 함.  
  else: 'is_deleted'를 1로 설정하고, 목록에서 아예 보이지 않도록 함(목록 조회 쿼리문에서 'is_deleted' 값으로 필터링).
```sql
 UPDATE comment SET is_deleted = 1 WHERE id = :id
```


**고민할 것:**

- 대댓글을 갖고 있는 댓글을 삭제하려는 경우 어떻게 해야 하는가?  
  (1. 삭제 불가능 / 2. '삭제된 댓글입니다' 표시 / 3. 댓글・대댓글 모두 삭제)  
- 즉 댓글이 존재하는지의 여부를 알아야 함.