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
        alert('이벤트 참여 기간이 아닙니다. 이벤트는 2024년 8월 1일부터 시작됩니다.');
    } else {
        // 이벤트 시작 후 링크 활성화
        document.getElementById('reviewLink').href = '/reservation/write?from=review';
    }
}

// 페이지 로드 시 날짜를 체크하여 링크를 활성화하거나 비활성화
document.addEventListener('DOMContentLoaded', function() {
    var startDate = new Date('2024-08-01');
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
    }
});


//// ckeditor 불러오는 api
document.addEventListener("DOMContentLoaded", function() {

    let ckEditorInstanceQna; // CKEditor 인스턴스 변수 선언
    let ckEditorInstanceReview; // CKEditor 인스턴스 변수 선언

    // 질문글 에디터
    ClassicEditor
        .create(document.querySelector('#qnaEditor'), {
            language: 'ko',
            enterMode: 'paragraph' // 엔터 키 동작을 문단 생성으로 설정
        })
        .then(editor => {
            ckEditorInstanceQna = editor; // CKEditor 인스턴스 할당
        });

    // 리뷰 에디터
    ClassicEditor
        .create(document.querySelector('#reviewEditor'), {
            language: 'ko',
            enterMode: 'paragraph' // 엔터 키 동작을 문단 생성으로 설정
        })
        .then(editor => {
            ckEditorInstanceReview = editor; // CKEditor 인스턴스 할당

            // ck에디터 커스텀 .. 어댑터 만지는 거
            
        });

    // qna 관한 ckeditor
    $("#qnaSubmit").click(function(e) {
        e.preventDefault(); // 기본 제출 동작 방지

        if (!ckEditorInstanceQna) {
            console.error("CKEditor 인스턴스가 초기화되지 않았습니다.");
            return;
        }

        //qnapwd 는 조금 더 생각해보기
        // 윗 부분 누르면 변하는 것도 같이 생각해 봐야겠다.. ㅎㅎ

        // CKEditor에서 텍스트 가져오기
        let contents = ckEditorInstanceQna.getData().trim();

        if (!contents) {
            alert("내용을 작성해주세요."); // 필드가 비어있으면 제출 방지
            return;
        }

        // 숨겨진 필드에 값 설정
        $("#qnaDetail").val(contents);

        if (confirm("제출하시겠습니까?")) { // 사용자 확인 요청
            $('#qnaSubmitForm').submit(); // 폼 제출
        }
    });

    // 리뷰에 관한 ckeditor
    $("#reviewSubmit").click(function(e) {
        e.preventDefault(); // 기본 제출 동작 방지

        if (!ckEditorInstanceReview) {
            console.error("CKEditor 인스턴스가 초기화되지 않았습니다.");
            return;
        }
        
        let phone = document.getElementById('phone').value.trim(); // #phone 요소의 값 가져오기
        let name = document.getElementById('reviewer').value.trim();

        if (name.length == 0) {
            alert("개인 정보 확인을 위해 이름을 적어주세요."); // 사용자에게 경고 메시지 표시
            document.getElementById('reviewer').focus(); // 포커스를 다시 #phone 입력란으로 이동
            return
        }

        if (phone.length == 0) {
            alert("개인 정보 확인을 위해 핸드폰 번호를 적어주세요."); // 사용자에게 경고 메시지 표시
            document.getElementById('phone').focus(); // 포커스를 다시 #phone 입력란으로 이동
            return
        }
        
        if (phone.length !== 11) {
            alert("핸드폰 번호는 11자리여야 합니다."); // 사용자에게 경고 메시지 표시
            document.getElementById('phone').focus(); // 포커스를 다시 #phone 입력란으로 이동
            return
        }

        // CKEditor에서 텍스트 가져오기
        let contents = ckEditorInstanceReview.getData().trim();
        // console.log(contents);

        if (!contents) {
            alert("내용을 작성해주세요.");// 필드가 비어있으면 제출 방지
            return;
        }

        // 숨겨진 필드에 값 설정
        $("#reviewDetail").val(contents);

        if (confirm("제출하시겠습니까?")) { // 사용자 확인 요청
            $('#reviewSubmitForm').submit(); // 폼 제출
        }
    });
});


