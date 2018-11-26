# Create Table

- member(회원) —> '회원'과 '권한'은 다대다 관계

```sql
CREATE TABLE member (
    id BIGINT(20) AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,				-- 회원 이름
    email VARCHAR(50) NOT NULL UNIQUE,
    nickname VARCHAR(50) NOT NULL UNIQUE,	-- 회원이 사용할 닉네임
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

- permission(권한)
```sql
CREATE TABLE permission (
    id BIGINT(20) AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);
```

- member_permission(member-permission pivot table)
```sql
CREATE TABLE member_permission (
    member_id BIGINT(20) NOT NULL,
    permission_id BIGINT(20) NOT NULL,
    PRIMARY KEY (`permission_id`, `member_id`),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);
```

- category(게시판 종류)
```sql
CREATE TABLE category (
    id BIGINT(20) AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);
```

- board(게시글, 답글) —> board_body와 1:1 관계
```sql
CREATE TABLE board (
    id BIGINT(20) AUTO_INCREMENT,
    category_id BIGINT(20) NOT NULL,          		-- 게시판 종류
    member_id BIGINT(20) NOT NULL,             		-- 작성자 id
    origin_id BIGINT(20) NOT NULL,             		-- 어떤 글에 대한 답글인지, 게시판 id는 bigint이므로 똑같이 bigint형이 되야함
    depth INT(20) NOT NULL DEFAULT 0,          		-- For indentation
    reply_seq INT(20) NOT NULL DEFAULT 0,     		-- 같은 depth인 답글끼리의 순서
    hits INT(20) NOT NULL DEFAULT 0,		  		-- 조회수					
    title VARCHAR(100) NOT NULL,               		-- 글 제목
    ip_addr VARCHAR(20) NOT NULL,              		-- 작성자의 IP 주소
    reg_date DATETIME NOT NULL,  					-- 작성일
    is_deleted TINYINT(1) NOT NULL DEFAULT 0,  		-- 삭제된 글인지(≓ boolean). 1이면 삭제된 글
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);
```

- board_body(본문 내용) —> 상세보기 할때만 사용
```sql
CREATE TABLE board_body (
    id BIGINT(20),
    content LONGTEXT,  -- 게시글 본문 내용
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES board(id)
);
```

- comment(댓글)
```sql
CREATE TABLE comment (
    id BIGINT(20) AUTO_INCREMENT,
    member_id BIGINT(20) NOT NULL,             	-- 어떤 member가 작성한 댓글인지
    board_id BIGINT(20) NOT NULL,              	-- 어떤 게시글에 대한 댓글인지
    parent_comment_id BIGINT(20) NOT NULL,     	-- 댓글이면 id와 parent_comment_id 동일, 대댓글이면 parent_comment_id는 원댓글의 id
    seq INT(20) NOT NULL DEFAULT 0,            	-- 한 댓글에 대한 대댓글끼리의 순서
    content TEXT NOT NULL,                     	-- 댓글 내용
    ip_addr VARCHAR(20) NOT NULL,              	-- 작성자의 IP 주소
    reg_date DATETIME NOT NULL,					-- 댓글 작성일
    is_deleted TINYINT(1) DEFAULT 0,  			-- 삭제된 댓글인지(≓ boolean). 1이면 삭제된 글
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (board_id) REFERENCES board(id)
);
```
- file(파일 업로드)  ---> 구현 예정
```sql
CREATE TABLE file
(
  id BIGINT(20) AUTO_INCREMENT,
  board_id BIGINT(20) NOT NULL,
  original_file_name VARCHAR(260) NOT NULL,
  stored_file_name VARCHAR(260) NOT NULL,
  file_size BIGINT(20),
  reg_date  DATETIME,
  del_gb    TINYINT(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (board_id) REFERENCES board(id),
);
```