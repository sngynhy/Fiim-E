create table client(
	id varchar2(20) primary key,
	pw varchar2(30) not null,
	email varchar2(50) not null
);

select * from client;
drop table client;
select * from all_tables;

insert into client values('asdf','a12345','abc1234@naver.com');
insert into client values('apple','a12345','apple321@gmail.com');
insert into client values('kiwi777','a12345','kiwi777@naver.com');
insert into client values('admin','1234','admin@naver.com');
CREATE table MOVIE(
	MPK VARCHAR2(20) PRIMARY KEY,
	TITLE VARCHAR2(100) NOT NULL,
	CONTENT VARCHAR2(4000) NOT NULL,
	MTYPE VARCHAR2(20) NOT NULL,
	MDATE VARCHAR2(100) NOT NULL,
	FILENAME VARCHAR2(100) NOT NULL
);

INSERT INTO MOVIE VALUES ('A1117','��ġ','�̷��� �����̴�','ACTION','2002/09/01','file.jsp');

drop table movie;

INSERT INTO MOVIE VALUES('A1001', '��ġ', '�̷��� �����̴�', 'ACTION', SYSDATE, 'FILE1');
INSERT INTO MOVIE VALUES('A1002', '��ġ2', '�̷��� �����̴�2', 'ACTION', SYSDATE, 'FILE2');
INSERT INTO MOVIE VALUES('L1003', '��ȭ1', '�̷��� �����̴�', 'ACTION', SYSDATE, 'FILE3');
INSERT INTO MOVIE VALUES('A1004', '��ȭ2', '�̷��� �����̴�2', 'ACTION', SYSDATE, 'FILE4');
INSERT INTO MOVIE VALUES('L1005', '��ȭ3', '�̷��� �����̴�', 'ACTION', SYSDATE, 'FILE5');
INSERT INTO MOVIE VALUES('D1006', '��ȭ4', '�̷��� �����̴�2', 'ACTION', SYSDATE, 'FILE6');
INSERT INTO MOVIE VALUES('A1007', '��ȭ5', '�̷��� �����̴�', 'ACTION', SYSDATE, 'FILE7');
INSERT INTO MOVIE VALUES('D1008', '��ȭ6', '�̷��� �����̴�2', 'ACTION', SYSDATE, 'FILE8');

SELECT * FROM MOVIE; 

SELECT COUNT(*) FROM MOVIE WHERE MTYPE = '�׼�' AND TITLE LIKE '�𰡵�';

2021/10/07


SELECT * FROM MOVIE WHERE TITLE LIKE '%��%';

create table review(
   rpk int primary key,
   cmt varchar2(4000) not null, -- comment
   id varchar2(20) not null,
   mpk varchar2(20) not null,
   rdate date not null
);

select * from review;
drop table review;

insert into review values(1,'�ʹ� ��ճ׿�!!','apple','A1001',sysdate);
insert into review values(2,'�������̳׿� �̤�','kiwi777','A1004',sysdate);
insert into review values(3,'�ʹ� ��̾��� �����߾�� ...','asdf','D1006',sysdate);

CREATE SEQUENCE review_seq START WITH 1 INCREMENT BY 1 MAXVALUE 1000 CYCLE NOCACHE;
select * from client;