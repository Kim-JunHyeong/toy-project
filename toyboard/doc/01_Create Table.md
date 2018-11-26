# Create Table

- member(회원) —> '회원'과 '권한'은 다대다 관계

```sql
CREATE TABLE member (
    id bigint(20) AUTO_INCREMENT,
    name varchar(50) NOT NULL,				-- 회원 이름
    email varchar(50) NOT NULL UNIQUE,
    nickname varchar(50) NOT NULL UNIQUE,	-- 회원이 사용할 닉네임
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
```

- permission(권한)
```sql
CREATE TABLE permission (
    id bigint(20) AUTO_INCREMENT,
    name varchar(20),
    PRIMARY KEY (id)
);
```

- member_permission(member-permission pivot table)
```sql
CREATE TABLE member_permission (
    member_id bigint(20) NOT NULL,
    permission_id bigint(20) NOT NULL,
    PRIMARY KEY (`permission_id`, `member_id`),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);
```

- category(게시판 종류)
```sql
CREATE TABLE category (
    id bigint(20) AUTO_INCREMENT,
    name varchar(20),
    PRIMARY KEY (id)
);
```

- board(게시글, 답글) —> board_body와 1:1 관계
```sql
CREATE TABLE board (
    id bigint(20) AUTO_INCREMENT,
    category_id bigint(20),           -- 게시판 종류
    member_id bigint(20),             -- 작성자 id
    origin_id bigint(20),             -- 어떤 글에 대한 답글인지, 게시판 id는 bigint이므로 똑같이 bigint형이 되야함
    depth int(20),                    -- For indentation
    reply_seq int(20),                -- 같은 depth인 답글끼리의 순서
    title varchar(100),               -- 글 제목
    ip_addr varchar(20),              -- 작성자의 IP 주소
    reg_date date,                    -- 작성일
    is_deleted tinyint(1) DEFAULT 0,  -- 삭제된 글인지(≓ boolean). 1이면 삭제된 글
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);
```

- board_body(본문 내용) —> 상세보기 할때만 사용
```sql
CREATE TABLE board_body (
    id bigint(20),
    content longtext,  -- 게시글 본문 내용
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES board(id)
);
```

- comment(댓글)
```sql
CREATE TABLE comment (
    id bigint(20) AUTO_INCREMENT,
    member_id bigint(20),             -- 어떤 member가 작성한 댓글인지
    board_id bigint(20),              -- 어떤 게시글에 대한 댓글인지
    parent_comment_id bigint(20),     -- 댓글이면 id와 parent_comment_id 동일, 대댓글이면 parent_comment_id는 원댓글의 id
    seq int(20),                      -- 한 댓글에 대한 대댓글끼리의 순서
    content text,                     -- 댓글 내용
    ip_addr varchar(20),              -- 작성자의 IP 주소
    reg_date date,                    -- 댓글 작성일
    is_deleted tinyint(1) DEFAULT 0,  -- 삭제된 댓글인지(≓ boolean). 1이면 삭제된 글
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (board_id) REFERENCES board(id)
);
```
- file(파일 업로드)  ---> 구현 예정
```sql
CREATE TABLE file
(
  idx   bigint(20) NOT NULL AUTO_INCREMENT,
  board_idx bigint(20) NOT NULL,
  original_file_name VARCHAR(260) NOT NULL,
  stored_file_name VARCHAR(260) NOT NULL,
  file_size bigint(20),
  reg_date  date,
  del_gb    tinyint(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (idx)
);
```