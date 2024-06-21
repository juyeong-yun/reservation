package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	@Id
	@Column(name="review_id")
	private Long reviewId;
	
	@Column(name="reviewer", nullable = false)
	private String reviewer;
	
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Column(name="title", nullable = false)
	private String title;
	
	@Column(name="detail", nullable = false)
	private String detail;
	
	@Column(name="write_date")
	private LocalDateTime writeDate;
	
	@Column(name="update_date")
	private LocalDateTime updateDate;

	@Column(name="original_file_name")
	private String originalFileName;
	
	public static ReviewEntity toEntity(ReviewDTO dto) {
		return ReviewEntity.builder()
				.reviewId(dto.getReviewId())
				.reviewer(dto.getReviewer())
				.phone(dto.getPhone())
				.title(dto.getTitle())
				.detail(dto.getDetail())
				.writeDate(dto.getWriteDate())
				.updateDate(dto.getUpdateDate())
				.originalFileName(dto.getOriginalFileName())
				.build();
	}

}
