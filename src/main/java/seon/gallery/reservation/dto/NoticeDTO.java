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
	private String noticeTitle;
	private String noticeDetail;
	private LocalDateTime writeDate;
	private MultipartFile noticeImage;
	private YesorNo isPost;
	
	public static NoticeDTO toDTO(NoticeEntity entity) {
		return NoticeDTO.builder()
				.noticeId(entity.getNoticeId())
				.noticeTitle(entity.getNoticeTitle())
				.noticeDetail(entity.getNoticeDetail())
				.writeDate(entity.getWriteDate())
				.isPost(entity.getIsPost())
				.build();
	}
}
