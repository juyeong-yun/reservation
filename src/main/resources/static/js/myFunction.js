/**
 * 목차 설정
 * 1. reserve 예약시간을 누르면 모달이 나오게
 * 2. 관리자 페이지에서 qna, notice detail 볼 수 있는 모달
 * 3. 리뷰페이지 글쓰기 버튼 기간 설정
 * 4. board 페이지에서 isLock 에 체크하면 input 박스 보이도록
 * 5. notice, review, qna ckeditor 불러오는 api
 */


/*****************
 * 1.reserve 예약시간을 누르면 모달이 나오게
 ******************/
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


/*****************
 * 2. notice 보기 , qna 질문 사항 답변 위한 모달창
 ******************/
$(function(){
    $(document).on("click", "[id^=qnaModal_]", function(e) {
        e.preventDefault(); 

        let qnaId = $(this).data("id");

        $("#qna-popup").css('display', 'flex').hide().fadeIn();

        $.ajax({
            url: '/admin/qnaDetail',
            method: 'get',
            data : {'qnaId' : qnaId},
            success : function(res) {
                $('#q_title').text(res.title);
                $('#q_writeDate').text(res.writeDate);
                $('#q_detail').text(res.detail);
                if (res.answer) {
                    $('#q_answer_row').show(); // 답변이 있으면 보이기
                    $('#q_answer').val(res.answer); // 기존 답변 데이터 textarea에 채우기
                } else {
                    $('#q_answer_row').hide(); // 답변이 없으면 숨기기
                    $('#q_answer').val(''); // textarea 비우기
                }
            }
        });
    });

    $(document).on("click", "[id^=noticeModal_]", function(e) {
        e.preventDefault(); // Prevent the default action
        
        let noticeId = $(this).data("id");

        $("#notice-popup").css('display', 'flex').hide().fadeIn(); // Show the modal
        
        $.ajax({
            url: '/admin/noticeDetail',
            method: 'get',
            data : {'noticeId' : noticeId},
            success : function(res) {
                $('#n_title').text(res.title);
                $('#n_writeDate').text(res.writeDate);
                $('#n_detail').text(res.detail);
            }
        });
    });

    // Modal close function
    $(".popup-wrap").on("click", ".submit", function() {
        modalClose();
    });

    // Modal close function
    function modalClose() {
        $(".popup-wrap").fadeOut(); // Fade out effect for closing modal
    }
});


/*****************
 * 3. 리뷰페이지 글쓰기 버튼 기간 지정
 ******************/
function checkDate() {
    // 지정된 날짜 (이벤트 시작 날짜)
    // 날짜는 여기서 지정 하면 될 거 같다.
    var startDate = new Date('2024-06-28'); // 이벤트 시작 날짜를 여기에 설정
    var currentDate = new Date();

    if (currentDate < startDate) {
        alert('이벤트 참여 기간이 아닙니다. 이벤트는 2024년 8월 16일부터 시작됩니다.');
    } else {
        // 이벤트 시작 후 링크 활성화
        document.getElementById('reviewLink').href = '/guest/write?from=review';
    }
}


/*****************
 * 4. board 페이지에서 isLock 에 체크하면 input 박스 보이도록
 ******************/
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

/*****************
 * 5. notice, review, qna ckeditor 불러오는 api , 유효값 
 ******************/
document.addEventListener("DOMContentLoaded", function() {

    let ckEditorInstanceQna; // CKEditor 인스턴스 변수 선언
    let ckEditorInstanceReview; // CKEditor 인스턴스 변수 선언
    let ckEditorInstanceNotice;

    // qnaEditor 초기화
    const qnaEditorElement = document.querySelector('#qnaEditor');
    if (qnaEditorElement) {
            ClassicEditor
                .create(qnaEditorElement, {
                    language: 'ko',
                    enterMode: 'paragraph'
                })
                .then(editor => {
                    ckEditorInstanceQna = editor;
                })
                .catch(error => {
                    console.error('Error initializing qnaEditor:', error);
                });
        }

        // reviewEditor 초기화
        const reviewEditorElement = document.querySelector('#reviewEditor');
        if (reviewEditorElement) {
            ClassicEditor
                .create(reviewEditorElement, {
                    language: 'ko',
                    enterMode: 'paragraph'
                })
                .then(editor => {
                    ckEditorInstanceReview = editor;
                })
                .catch(error => {
                    console.error('Error initializing reviewEditor:', error);
                });
        }

        // noticeEditor 초기화
        const noticeEditorElement = document.querySelector('#noticeEditor');
        if (noticeEditorElement) {
            ClassicEditor
                .create(noticeEditorElement, {
                    language: 'ko',
                    enterMode: 'paragraph'
                })
                .then(editor => {
                    ckEditorInstanceNotice = editor;
                })
                .catch(error => {
                    console.error('Error initializing noticeEditor:', error);
                });
        }

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

    $('#noticeSubmit').click(function(e) {
        e.preventDefault(); // 기본 제출 동작 방지

        if (!ckEditorInstanceNotice) {
            console.error("CKEditor 인스턴스가 초기화되지 않았습니다.");
            return;
        }

        let title = document.getElementById('noticeTitle').value.trim();

        if (title.length == 0) {
            alert("제목을 작성해주세요."); // 사용자에게 경고 메시지 표시
            document.getElementById('noticeTitle').focus(); 
            return
        }

        // CKEditor에서 텍스트 가져오기
        let contents = ckEditorInstanceNotice.getData().trim();
        // console.log(contents);

        if (!contents) {
            alert("내용을 작성해주세요.");// 필드가 비어있으면 제출 방지
            return;
        }

        // 숨겨진 필드에 값 설정
        $("#noticeDetail").val(contents);

        if (confirm("제출하시겠습니까?")) { // 사용자 확인 요청
            $('#noticeSubmitForm').submit(); // 폼 제출
        }

    });
});
