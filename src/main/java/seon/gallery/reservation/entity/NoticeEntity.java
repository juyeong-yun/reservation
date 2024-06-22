package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
	@SequenceGenerator(
		name = "notice_seq"
		, sequenceName = "notice_seq"
		, initialValue = 1
		, allocationSize = 1
	)
	
	@Id
	@GeneratedValue(generator = "notice_seq")
	@Column(name="notice_id")
	private Long noticeId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="detail")
	private String detail;
	
	@Column(name="write_date")
	private LocalDateTime writeDate;
	
	@Column(name="is_post")
	@Enumerated(EnumType.STRING)
	private YesorNo isPost;
	
	public static NoticeEntity toEntity(NoticeDTO dto) {
		return NoticeEntity.builder()
				.noticeId(dto.getNoticeId())
				.title(dto.getTitle())
				.detail(dto.getDetail())
				.writeDate(dto.getWriteDate())
				.isPost(dto.getIsPost())
				.build();
		
	}

}
