package seon.gallery.reservation.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.entity.ReviewEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ReviewDTO {

	private Long reviewId;
	private String reviewName;
	private String phoneEndNumber;
	private String reviewTitle;
	private String reviewDetail;
	private LocalDateTime writeDate;
	private LocalDateTime updateDate;
	private MultipartFile reviewImage;
	
	public static ReviewDTO toDTO(ReviewEntity entity) {
		return ReviewDTO.builder()
				.reviewId(entity.getReviewId())
				.reviewName(entity.getReviewName())
				.phoneEndNumber(entity.getPhoneEndNumber())
				.reviewTitle(entity.getReviewTitle())
				.reviewDetail(entity.getReviewDetail())
				.writeDate(entity.getWriteDate())
				.updateDate(entity.getUpdateDate())
				.build();
	}
}
