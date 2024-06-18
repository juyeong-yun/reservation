drop table notice;
drop sequence notice_seq;

-- 1) 공지사항 게시판
create sequence notice_seq;
create table notice(
    notice_num number constraint notice_seq primary key
    -- ckeditor 로 변경할 예정
    ,notice_title varchar2(200) not null
    ,notice_detail clob not null
    ,write_date date default sysdate
    ,notice_image clob
    ,is_post char(1) check(is_post in ('N','Y'))
);

select * from notice;

--2)qna 게시판
drop table qna;
drop sequence qna_seq;

create sequence qna_seq;
create table qna(
    qna_num number constraint qna_seq primary key
    ,qna_name varchar2(10) not null
    ,qna_title varchar2(200) not null
    ,qna_detail varchar2(4000) not null
    ,write_date date default sysdate
    ,qna_pwd number(4)
    ,qna_answer varchar2(4000)
    ,is_lock char(1) default 'N' check(is_lock in ('N','Y'))
    ,is_answer char(1) default 'N' check(is_answer in ('N','Y'))
);

select * from qna;

-- 관리자가 필요한 걸까 전시 정보에 대해 필요할까 
-- 전시 정보에 대한게 필요하겠네...

-- 3) 예약자 게시판
drop table reserve;
drop sequence reserve_seq;

create sequence reserve_seq;
create table reserve(
    reserve_num number constraint reserve_seq primary key
    ,event_time date
    ,reserve_name varchar2(10)
    ,phone varchar2(20)
    ,reserve_date date
    ,is_pay char(1) check(is_pay in ('N','Y'))
    ,is_confirm char(1) check(is_confirm in ('N','Y'))
    ,is_cancle char(1) default 'N' check(is_cancle in ('N','Y'))
    ,cancle_reason varchar2(500)
);

select * from reserve;


-- 4) 리뷰 게시판
drop table review;
drop sequence review_seq;

create sequence review_seq;
create table review(
    review_num number constraint review_seq primary key
    ,review_name varchar2(10) not null
    ,review_title varchar2(200) not null
    ,review_detail clob not null
    ,write_date date default sysdate
    ,update_date date
    ,review_images clob
);

select * from review;
