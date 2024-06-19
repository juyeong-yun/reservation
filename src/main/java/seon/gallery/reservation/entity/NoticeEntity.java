package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.NoticeDTO;
import seon.gallery.reservation.dto.check.YesorNo;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="notice")
public class NoticeEntity {
	
	@Id
	@Column(name="notice_id")
	private Long noticeId;
	
	@Column(name="notice_title", nullable = false)
	private String noticeTitle;
	
	@Column(name="notice_detail", nullable = false)
	private String noticeDetail;
	
	@Column(name="write_date")
	private LocalDateTime writeDate;
	
	@Column(name="is_post")
	@Enumerated(EnumType.STRING)
	private YesorNo isPost;
	
	public static NoticeEntity toEntity(NoticeDTO dto) {
		return NoticeEntity.builder()
				.noticeId(dto.getNoticeId())
				.noticeTitle(dto.getNoticeTitle())
				.noticeDetail(dto.getNoticeDetail())
				.writeDate(dto.getWriteDate())
				.isPost(dto.getIsPost())
				.build();
		
	}

}
