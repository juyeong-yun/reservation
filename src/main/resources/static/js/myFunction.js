/**
 * 목차 설정
 * 1. reserve 예약시간을 누르면 모달이 나오게
 * 1-1. booking 에서 eventId, EventTime 같이 보내기
 * 2. 관리자 페이지에서 qna, notice detail 볼 수 있는 모달
 * 3. 리뷰페이지 글쓰기 버튼 기간 설정
 * 4. board 페이지에서 isLock 에 체크하면 input 박스 보이도록
 * 5. notice, review, qna ckeditor 불러오는 api
 */


/*****************
 * 1.jquery / reserve 예약시간을 누르면 모달이 나오게
 ******************/
$(function() {
    let targetHref;

    $(document).on("click", "#modal-open", function(e) {
		
        e.preventDefault(); // Prevent the default action
        
        targetHref = $(this).data("href"); // Store the href for later
        // targetHref = $(this).attr("data-href");
        console.log("주소 :", targetHref);

        // $("#popup").fadeIn(); // Show the modal
        $("#popup").css('display', 'flex').hide().fadeIn(); // Show the modal
    });

    // When the close button is clicked
    $("#close").click(function() {
        modalClose(); // Call the modal close function
        if (targetHref) {
            window.location.href = targetHref; // 저장된 href로 페이지를 리디렉션합니다.
        }
    });

    // Modal close function
    function modalClose() {
        $("#popup").fadeOut(); // Fade out effect for closing modal
    }
});

/*****************
 * 1-1.booking 에서 eventId, EventTime 같이 보내기
 ******************/

$(document).ready(function() {
    // 예약 버튼 클릭 시
    $("#reserve").click(function(e) {
        e.preventDefault(); // 기본 동작(페이지 이동) 방지
        
        // 폼 데이터에 eventId와 eventTime 추가
        var eventId = $("input[name='eventId']").val();
        var eventTime = $("input[name='eventTime']").val();
        $("#reserveSubmitForm").append(eventId);
        $("#reserveSubmitForm").append(eventTime);
        
        // 폼 제출
        $("#reserveSubmitForm").submit();
    });
});


/*****************
 * 2. jquery / notice 보기 
 ******************/
$(function(){

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
        document.getElementById('reviewLink').href = '/guest/write';
    }
}


/*****************
 * 4. javascript / board 페이지에서 공지 제목을 누르면 아래 내용이 나오게
 * 여기서는 id는 유일한 하나이기 때뮨에 class를 사용하여 그룹을 만들고 for 문을 돌린다,,!
 ******************/
document.addEventListener('DOMContentLoaded', function() {
    var noticeTitles = document.getElementsByClassName('dropdown-notice');
    var noticeDetails = document.getElementsByClassName('dropdown-detail');
    
    // 모든 상세 내용을 숨깁니다.
    for (var i = 0; i < noticeDetails.length; i++) {
        noticeDetails[i].style.display = 'none';
    }
    
    // 각 제목에 클릭 이벤트 리스너를 추가합니다.
    for (var j = 0; j < noticeTitles.length; j++) {
        noticeTitles[j].addEventListener('click', function() {
            // 현재 클릭된 제목 바로 다음에 오는 요소를 찾습니다.
            var relatedDetail = this.closest('tr').nextElementSibling.querySelector('.dropdown-detail');
            if (relatedDetail) {
                // 상세 내용을 토글합니다.
                if (relatedDetail.style.display === 'none' || relatedDetail.style.display === '') {
                    relatedDetail.style.display = 'table-cell';
                } else {
                    relatedDetail.style.display = 'none';
                }
            }
        });
    }
});

/*****************
 * 5. javascript / notice, review, qna ckeditor 불러오는 api , 유효값 
 ******************/
document.addEventListener("DOMContentLoaded", function() {

    let ckEditorInstanceReview; // CKEditor 인스턴스 변수 선언
    let ckEditorInstanceNotice;

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

        $('#noticeSubmit').click(function(e) {
            e.preventDefault(); // 기본 제출 동작 방지
    
            if (!ckEditorInstanceNotice) {
                console.error("CKEditor 인스턴스가 초기화되지 않았습니다.");
                return;
            }
    
            let title = document.getElementById('noticeTitle').value.trim();
            let ctg = document.getElementById('category').value.trim();
            
            if(ctg.length == 0) {
                alert("카테고리를 작성해주세요.")
                document.getElementById('category').focus();
                return
            }
    
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


/***************
 * 6. jquery/ 예약 페이지에서 이름과 전화번호를 검색하면 예약한 날짜와 시간이 나오도록 설정 
 **************/

$(function(){
    $('document').on("click", "#search", function(searchEvent){ 
        searchEvent.preventDefault();

        let name = document.getElementById("reserver-check").val().trim();
        let phone = document.getElementById("reserver-phone-check").val().trim();

        console.log(name, phone);

        $.ajax({
            type:"Get",
            url: "/guest/reserveCheck",
            data : {
                reserver : name,
                phone : phone
            },
            success: function (res) {
                $(".reserve-result").html(res);
            }, error: function(){
                alert("요청 도중 오류가 발생했습니다. 디엠으로 문의 주세요.");
            }
        })
    }); 
});
