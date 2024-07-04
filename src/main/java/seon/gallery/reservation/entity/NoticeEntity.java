package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
	
	@Column(name = "notice_category")
	private String category;

	@Column(name="title")
	private String title;
	
	@Column(name="detail")
	private String detail;
	
	@Column(name="write_date")
	@CreationTimestamp
	private LocalDateTime writeDate;
	
	@Column(name="is_post")
	private boolean isPost;

	@Column(name="original_file_name")
	private String originalFileName;

	@Column(name = "saved_file_name")
	private String savedFileName;
	
	public static NoticeEntity toEntity(NoticeDTO dto) {
		return NoticeEntity.builder()
				.noticeId(dto.getNoticeId())
				.category(dto.getCategory())
				.title(dto.getTitle())
				.detail(dto.getDetail())
				.writeDate(dto.getWriteDate())
				.isPost(dto.isPost())
				.originalFileName(dto.getOriginalFileName())
				.savedFileName(dto.getSavedFileName())
				.build();	
	}

}
