drop table notice;
drop sequence notice_seq

-- 1) 공지사항 게시판
create sequence notice_seq;
create table notice(
    notice_num number constraint notice_seq primary key
    -- ckeditor 로 변경할 예정
    ,notice_title varchar2(200)
    ,notice_detail varchar2(4000)
    ,write_date date default sysdate
);

--2)qna 게시판
create sequence qna_seq;
create table qna(
    qna_num number constraint qna_seq primary key
    ,name varchar2(10)
    -- ckeditor 로 변경할 예정
    ,qna_title varchar2(200)
    ,qna_detail varchar2(4000)
    ,write_date date default sysdate
    ,lock char(1) default 1
);

-- 3) 예약자 게시판
create sequence reserve_seq;
create table notice(
    notice_num number constraint notice_seq primary key
    ,name varchar2(10)
    -- ckeditor 로 변경할 예정
    ,notice_title varchar2(200)
    ,notice_detail varchar2(4000)
    ,write_date date default sysdate
    ,
);

-- 4) 리뷰 게시판
create sequence review_seq;
create table notice(
    notice_num number constraint notice_seq primary key
    ,name varchar2(10)
    -- ckeditor 로 변경할 예정
    ,notice_title varchar2(200)
    ,notice_detail varchar2(4000)
    ,write_date date default sysdate
);