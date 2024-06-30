drop table notice;
drop sequence notice_seq;

-- 1) 공지사항 게시판
create sequence notice_seq;
create table notice(
    notice_id number constraint notice_seq primary key
    ,notice_category varchar2(30)
    ,title varchar2(200) default '제목없음'
    ,detail clob
    ,write_date date default sysdate
    ,notice_image clob
    ,original_file_name varchar2(200)
    ,saved_file_name varchar2(200)
    ,is_post char(1) check(is_post in ('N','Y'))
);

select * from notice;

--2)qna 게시판
drop table qna;
drop sequence qna_seq;

create sequence qna_seq;
create table qna(
    qna_id number constraint qna_seq primary key
    ,qna_name varchar2(20) not null
    ,title varchar2(200) default '제목없음'
    ,detail clob
    ,write_date date default sysdate
    ,qna_pwd varchar2(4)
    ,qna_answer varchar2(4000)
    ,is_lock number(1) default 0 check(is_lock in (1,0))
    ,is_answer char(1) default 'N' check(is_answer in ('N','Y'))
);

select * from qna;


drop table event;
drop sequence event_seq;

create sequence event_seq;
CREATE TABLE event (
    event_id VARCHAR2(50) CONSTRAINT event_seq PRIMARY KEY,
    event_date DATE,
    event_time varchar2(10),
    is_full NUMBER(1) DEFAULT 0 CHECK(is_full IN (1,0))
);

select * from event;

-- 관리자가 필요한 걸까 전시 정보에 대해 필요할까 
-- 전시 정보에 대한게 필요하겠네...

-- 3) 예약자 게시판
drop table reserve;
drop sequence reserve_seq;

create sequence reserve_seq;
create table reserve(
    reserve_id number constraint reserve_seq primary key
    ,event_time varchar2(10)
    ,reserver varchar2(20) not null
    ,phone varchar2(20) not null
    ,reserve_date date default sysdate
    ,request varchar2(500) default '요청 없음'
    ,number_of_reserve number(2) default 1
    ,keyring number(2) default 0
    ,is_pay char(1) default 0 check(is_pay in (1,0))
    ,is_confirm char(1) check(is_confirm in ('N','Y'))
    ,is_cancle char(1) default 'N' check(is_cancle in ('N','Y'))
    ,cancle_reason varchar2(1000)
    ,event_id varchar2(50)
    , constraint event_pk foreign key(event_id) REFERENCES event(event_id)
);

select * from reserve;

-- 4) 리뷰 게시판
drop table review;
drop sequence review_seq;

create sequence review_seq;
create table review(
    review_id number constraint review_seq primary key
    ,reviewer varchar2(20) not null
    ,phone varchar2(20) not null
    ,detail clob
    ,write_date date default sysdate
    ,update_date date
    ,review_images clob
    ,original_file_name varchar2(200)
    ,saved_file_name varchar2(200)
);

select * from review;