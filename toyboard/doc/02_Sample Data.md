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

- board(게시판)       ***TODO : hits(조회수) 칼럼이 추가 됨* **

```sql
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, title, ip_addr, reg_date) VALUES (null, 2, 1, 1, 0, 1, 'Java 게시판에 오신걸 환영합니다!!', '192.168.10.10', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, title, ip_addr, reg_date) VALUES (null, 3, 1, 1, 0, 1, 'JSP 게시판에 오신걸 환영합니다!!', '192.168.10.10', NOW());
INSERT INTO board (id, category_id, member_id, origin_id, depth, reply_seq, title, ip_addr, reg_date) VALUES (null, 4, 1, 1, 0, 1, 'Spring 게시판에 오신걸 환영합니다!!', '192.168.10.10', NOW());
```

- depth = 1 답글
- depth = 2 답글
- comment(댓글)