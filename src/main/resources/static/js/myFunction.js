

// reserve 페이지에 달력 사용하는 자바 오픈 api
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth'
    });
        calendar.render();
    });
