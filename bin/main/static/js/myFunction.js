/**
 * 목차 설정
 * 1. reserve 페이지에 달력 사용하는 자바 오픈 api (현재 : 사용 안함 ) = 추후에 삭제 예정
 * 2. ckeditor 불러오는 api
 * 3. reserve 예약시간 누르면 나오는 모달과 관련된 js
 * 4. 리뷰페이지 글쓰기 버튼 기간 설정
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
        .then(editor => {
            console.log('Editor was initialized', editor);
        })
        .catch(error => {
            console.error('Editor initialization failed', error);
        });
});


//// modal 창과 연결된 js
$(function() {
    // Variable to store the href to navigate to upon confirmation
    let targetHref;

    // When modal-open link is clicked
    $(document).on("click", "#modal-open", function(e) {
		
        e.preventDefault(); // Prevent the default action
        targetHref = $(this).data("href"); // Store the href for later
        $("#popup").css('display', 'flex').hide().fadeIn(); // Show the modal
    });

    // When the close button is clicked
    $("#close").click(function() {
        modalClose(); // Call the modal close function
        // window.location.href = targetHref; // Redirect to the stored href
        // 이거에 대한 가는 게 필요한 거구나
    });

    // Modal close function
    function modalClose() {
        $("#popup").fadeOut(); // Fade out effect for closing modal
    }
});


//// 리뷰페이지 글쓰기 버튼 기간 지정
function checkDate() {
    // 지정된 날짜 (이벤트 시작 날짜)
    // 날짜는 여기서 지정 하면 될 거 같다.
    var startDate = new Date('2024-06-21'); // 이벤트 시작 날짜를 여기에 설정
    var currentDate = new Date();

    if (currentDate < startDate) {
        alert('이벤트 참여 기간이 아닙니다. 이벤트는 2024년 7월 1일부터 시작됩니다.');
    } else {
        // 이벤트 시작 후 링크 활성화
        document.getElementById('reviewLink').href = '/reservation/write?from=review';
    }
}

// 페이지 로드 시 날짜를 체크하여 링크를 활성화하거나 비활성화
document.addEventListener('DOMContentLoaded', function() {
    var startDate = new Date('2024-07-01');
    var currentDate = new Date();
    
    if (currentDate >= startDate) {
        document.getElementById('reviewLink').href = '/reservation/write?from=review';
    }
});