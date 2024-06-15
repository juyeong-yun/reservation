drop table notice;
drop sequence notice_seq;

-- 1) 공지사항 게시판
create sequence notice_seq;
create table notice(
    notice_num number constraint notice_seq primary key
    -- ckeditor 로 변경할 예정
    ,notice_title varchar2(200)
    ,notice_detail clob
    ,write_date date default sysdate
    ,notice_images clob
    ,is_post char(1)
);

--2)qna 게시판
drop table qna;
drop sequence qna_seq;

create sequence qna_seq;
create table qna(
    qna_num number constraint qna_seq primary key
    ,qna_name varchar2(10)
    ,qna_title varchar2(200)
    ,qna_detail varchar2(4000)
    ,write_date date default sysdate
    ,qna_pwd number(4) 
    ,is_lock char(1) default 0
    ,is_answer char(1) default 0
);



-- 3) 예약자 게시판
drop table reserve;
drop sequence reserve_seq;

create sequence reserve_seq;
create table reserve(
    reserve_num number constraint reserve_seq primary key
    ,reserve_name varchar2(10)
    ,phone varchar2(20)
    ,reserve_date date
    ,is_pay char(1) default 0 
    ,is_confirm char(1) default 0
    ,is_cancle char(1) default 0
    ,cancle_reason varchar2(500)
);

-- 4) 리뷰 게시판
drop table review;
drop sequence review_seq;

create sequence review_seq;
create table review(
    review_num number constraint review_seq primary key
    ,review_name varchar2(10)
    ,review_title varchar2(200)
    ,review_detail clob
    ,write_date date default sysdate
    ,update_date date
    ,review_images clob
);