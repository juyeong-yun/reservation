/**
 * 목차 설정
 * 1. reserve 예약시간 누르면 나오는 모달과 관련된 js
 * 2. 리뷰페이지 글쓰기 버튼 기간 설정
 * 3. board 페이지에서 isLock 에 체크하면 input 박스 보이도록
 */


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


// board 페이지에서 isLock 에 체크하면 input 박스 보이도록
document.addEventListener('DOMContentLoaded', function(){
    var ischeck = document.getElementById('isLock');
    var pwdinput = document.getElementById('qnaPwd');
    
    if (ischeck && pwdinput) { // 요소가 존재하는지 확인
        ischeck.addEventListener('change', function(){
            pwdinput.style.display = this.checked ? 'block' : 'none';
        });
        
        // 페이지 로드 시 초기 상태에 따라 처리
        pwdinput.style.display = ischeck.checked ? 'block' : 'none';
    } else {
        console.error('Cannot find isLock or qnaPwd element.');
    }
});