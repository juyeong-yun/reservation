package seon.gallery.reservation.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.entity.NoticeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NoticeDTO {

	private Long noticeId;
	private String category;
	private String title;
	private String detail;
	private LocalDateTime writeDate;
	private boolean isPost;
	private MultipartFile noticeImage;
	private String originalFileName;
	private String savedFileName;

	// 페이징 위한 생성자
	public NoticeDTO(Long noticeId, String category, String title, String detail, LocalDateTime writeDate,
            String originalFileName, String savedFileName) {
				this.noticeId = noticeId;
				this.category = category;
				this.title = title;
				this.detail = detail;
				this.writeDate = writeDate;
				this.originalFileName = originalFileName;
				this.savedFileName = savedFileName;
    }
	
	public static NoticeDTO toDTO(NoticeEntity entity) {
		return NoticeDTO.builder()
				.noticeId(entity.getNoticeId())
				.category(entity.getCategory())
				.title(entity.getTitle())
				.detail(entity.getDetail())
				.writeDate(entity.getWriteDate())
				.isPost(entity.isPost())
				.originalFileName(entity.getOriginalFileName())
				.savedFileName(entity.getSavedFileName())
				.build();
	}

    
}
