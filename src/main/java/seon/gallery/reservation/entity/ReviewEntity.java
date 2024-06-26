package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import seon.gallery.reservation.dto.ReviewDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name="review")
public class ReviewEntity {
	@SequenceGenerator(
		name = "review_seq"
		, sequenceName = "review_seq"
		,initialValue = 1
		,allocationSize = 1
	)

	@Id
	@GeneratedValue(generator="review_seq")
	@Column(name="review_id")
	private Long reviewId;
	
	@Column(name="reviewer", nullable = false)
	private String reviewer;
	
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Column(name="detail")
	private String detail;
	
	@Column(name="write_date")
	@CreationTimestamp
	private LocalDateTime writeDate;
	
	@Column(name="update_date")
	@LastModifiedDate
	private LocalDateTime updateDate;

	// 첨부파일이 있을때
	@Column(name="original_file_name")
	private String originalFileName;

	@Column(name = "saved_file_name")
	private String savedFileName;


	public static ReviewEntity toEntity(ReviewDTO dto) {
		return ReviewEntity.builder()
				.reviewId(dto.getReviewId())
				.reviewer(dto.getReviewer())
				.phone(dto.getPhone())
				.detail(dto.getDetail())
				.writeDate(dto.getWriteDate())
				.updateDate(dto.getUpdateDate())
				.originalFileName(dto.getOriginalFileName())
				.savedFileName(dto.getSavedFileName())
				.build();
	}

}
