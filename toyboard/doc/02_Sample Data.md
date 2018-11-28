# Sample Data

### 운영될 때 기본적으로 DB에 있어야 할 Data

- permission(권한)

```sql
INSERT INTO permission (id, name) VALUES (null, 'Admin');
INSERT INTO permission (id, name) VALUES (null, 'Secondary Admin');
INSERT INTO permission (id, name) VALUES (null, 'User');
```



### 개발할 때 Test를 위한 data

- member(회원)

```sql
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '관리자', 'admin@fc.com', 'admin' '1234');
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '김성박', 'urstory@fc.com', 'urstory' '1234');
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '신윤철', 'jeremy@fc.com', 'Jeremy' '1234');
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '최재용', 'jdragon@fc.com', 'JDragon' '1234');
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '김규민', 'starkying@fc.com', 'starkying' '1234');
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '김준형', 'crazy@fc.com', 'CrAzy' '1234');
INSERT INTO member (id, name, email, nickname, password) VALUES (null, '주미선', 'misunjoo@fc.com', 'MisunJoo' '1234');
```

- permission(권한)

```sql
INSERT INTO permission (id, name) VALUES (null, 'Admin');
INSERT INTO permission (id, name) VALUES (null, 'Secondary Admin');
INSERT INTO permission (id, name) VALUES (null, 'User');
```

- member_permission(회원_권한)

```sql
INSERT INTO member_permission (member_id, permission_id) VALUES (1, 1);
INSERT INTO member_permission (member_id, permission_id) VALUES (1, 2);
INSERT INTO member_permission (member_id, permission_id) VALUES (1, 3);

INSERT INTO member_permission (member_id, permission_id) VALUES (2, 2);
INSERT INTO member_permission (member_id, permission_id) VALUES (2, 3);

INSERT INTO member_permission (member_id, permission_id) VALUES (3, 3);
INSERT INTO member_permission (member_id, permission_id) VALUES (4, 3);
INSERT INTO member_permission (member_id, permission_id) VALUES (5, 3);
INSERT INTO member_permission (member_id, permission_id) VALUES (6, 3);
INSERT INTO member_permission (member_id, permission_id) VALUES (7, 3);
```

- category(카테고리)

```sql
INSERT INTO category (id, name) VALUES (null, '공지사항');
INSERT INTO category (id, name) VALUES (null, 'Java');
INSERT INTO category (id, name) VALUES (null, 'JSP');
INSERT INTO category (id, name) VALUES (null, 'Spring');
```

- board(게시판)

```sql
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 1, 1, 1, 0, 0, 0, '[필독]게시판 이용 규칙!!', '192.168.10.10', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 1, 2, 0, 0, 0, 'Java 게시판에 오신걸 환영합니다!!', '192.168.10.10', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 3, 1, 3, 0, 0, 0, 'JSP 게시판에 오신걸 환영합니다!!', '192.168.10.10', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 4, 1, 4, 0, 0, 0, 'Spring 게시판에 오신걸 환영합니다!!', '192.168.10.10', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 2, 5, 0, 0, 0, '즐거운 JAVA!' '192.168.10.20', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 6, 6, 0, 0, 0, '잘 부탁드립니다~');
```

- 답글

```sql
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 3, 6, 1, 1, 0, '[RE]화이팅!!', '192.168.10.30', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 6, 6, 2, 2, 0, '[RE][RE]네! 감사합니다 ^^', '192.168.10.60', NOW());

INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 5, 6, 1, 3, 0, '[RE]같이 잘 해봐요', '192.168.10.50', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, hits, title, ip_addr, reg_date) VALUES (null, 2, 6, 6, 2, 4, 0, '[RE][RE]네! 감사합니다 ^^', '192.168.10.60', NOW());
```

- board_body(본문 내용)

```sql
-- depth = 0 인 본문
INSERT INTO board_body (id, content) VALUES (1, '안녕하세요. 저희 게시판을 이용하실 때에는 자유롭게 사용하되 상대방을 비하하거나 불쾌감을 주는 언어는 사용하지 않도록 합시다!!');
INSERT INTO board_body (id, content) VALUES (2, 'Java를 학습하면서 어려웠던 점을 서로 공유해요 ^^~');
INSERT INTO board_body (id, content) VALUES (3, 'JSP를 학습하면서 어려웠던 점을 서로 공유해요 ^^~');
INSERT INTO board_body (id, content) VALUES (4, 'Spring을 학습하면서 어려웠던 점을 서로 공유해요 ^^~');
INSERT INTO board_body (id, content) VALUES (5, '우리 모두 즐겁게 JAVA 공부를 열심히 합시다~ 학습하면서 모르는 부분이 있으면 질문해주세요 :)');
INSERT INTO board_body (id, content) VALUES (6, '프로그래밍을 시작한 지 얼마 되지 않아서 모르는 부분이 많지만 같이 열심히 공부해요 ^^');

-- depth = 1 인 본문
INSERT INTO board_body (id, content) VALUES (7, '포기하지 말고 열심히 같이 해나가요 :)');
INSERT INTO board_body (id, content) VALUES (8, '잘 부탁드릴게요 (_ _)');

-- depth = 2 인 본문
INSERT INTO board_body (id, content) VALUES (9, '서로 도와가면서 열심히 하죠!');
INSERT INTO board_body (id, content) VALUES (10, '잘 부탁드릴게요 (_ _)');
```

- comment(댓글)

```sql
INSERT INTO comment (id, member_id, board_id, parent_comment_id, seq, content, ip_addr, reg_date) VALUES (null, 2, 1, 2, 0, '숙지!', '192.168.10.20', NOW());
INSERT INTO comment (id, member_id, board_id, parent_comment_id, seq, content, ip_addr, reg_date) VALUES (null, 3, 1, 3, 0, '알겠습니다~ :)', '192.168.10.20', NOW());
INSERT INTO comment (id, member_id, board_id, parent_comment_id, seq, content, ip_addr, reg_date) VALUES (null, 6, 1, 4, 0, '넵~~!', '192.168.10.20', NOW());
```

