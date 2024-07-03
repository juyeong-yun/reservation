package seon.gallery.reservation.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageNevigator {
    private final int pagePerGroup = 10; //그룹당 몇페이지의 수
    private int pageLimit; // 한 페이지 당 글의 개수
    private int page; // 사용자가 요청한 페이지
    private int totalPages; // 총 페이지 수
    private int totalGroupCount; // 총 그룹의 수
    private int currentGroup; // 요청한 페이지가 속한 그룹
    private int startPageGroup; // 현재 그룹의 첫 번째 글 번호
    private int endPageGroup; // 현재 그룹의 마지막 글번호

    // 기본 생성자
    public PageNevigator(int pageLimit, int page, int totalPages) {

        this.pageLimit = pageLimit;
        this.page = page;
        this.totalPages = totalPages;

        // 총 그룹수 계산 (10페이지가 한 그룹)
        totalGroupCount = (totalPages / pagePerGroup);
        totalPages += (totalPages % pagePerGroup == 0) ? 0 : 1;

        // 사용자가 요청한 페이지의 첫 번째 글번호와 마지막 글번호 계산
        startPageGroup = ((int)(Math.ceil(((double)page / pageLimit))) - 1) * pageLimit + 1;

        endPageGroup = (startPageGroup + pageLimit -1) < totalPages ? 
        (startPageGroup + pageLimit -1) : totalPages;

        // 검색과 함께 사용했을때 검색결과가 하나도 없다면
        // startPageGroup = 1이고 endPageGrouop = 0이 되므로 이런 경우 endPageGrouop = 1로 한다. 
        if (endPageGroup == 0) { endPageGroup = 1;}

        // 요청한 페이지가 속한 그룹
        currentGroup = (page - 1) / pagePerGroup + 1 ;
    
    }

}
