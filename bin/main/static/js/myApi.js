/**
 * 1. reserve 페이지에 달력 사용하는 자바 오픈 api (현재 : 사용 안함 ) = 추후에 삭제 예정
 * 2. ckeditor 불러오는 api
 * 
 */


//// reserve 페이지에 달력 사용하는 자바 오픈 api
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendars');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth'
    });
        calendar.render();
});


//// ckeditor 불러오는 api
document.addEventListener("DOMContentLoaded", function() {
    ClassicEditor
        .create(document.querySelector('#editor'), {
            enter : 'paragraph', // 엔터 키 동작을 문단 생성으로 설정
        })
        .catch(error => {
            console.error('Editor initialization failed', error);
        });
});