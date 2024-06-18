package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

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
	@Column(name="review_num")
	private Long reviewId;
	
	@Column(name="review_name", nullable = false)
	private String reviewName;
	
	@Column(name="phone_endNumber", nullable = false)
	private String phoneEndNumber;
	
	@Column(name="review_title", nullable = false)
	private String reviewTitle;
	
	@Column(name="review_detail", nullable = false)
	private String reviewDetail;
	
	@Column(name="write_date")
	private LocalDateTime writeDate;
	
	@Column(name="update_date")
	private LocalDateTime updateDate;
	
	public static ReviewEntity toEntity(ReviewDTO dto) {
		return ReviewEntity.builder()
				.reviewId(dto.getReviewId())
				.reviewName(dto.getReviewName())
				.phoneEndNumber(dto.getPhoneEndNumber())
				.reviewTitle(dto.getReviewTitle())
				.reviewDetail(dto.getReviewDetail())
				.writeDate(dto.getWriteDate())
				.updateDate(dto.getUpdateDate())
				.build();
	}

}
