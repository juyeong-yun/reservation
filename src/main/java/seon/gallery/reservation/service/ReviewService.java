package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.entity.ReviewEntity;
import seon.gallery.reservation.repository.ReviewRepository;
import seon.gallery.reservation.util.FileService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Value("${spring.servlet.multipart.location}")
    String uploadPath;

    public List<ReviewDTO> allReview() {
        List<ReviewEntity> entityList = reviewRepository.findAll();
        List<ReviewDTO> dtoList = new ArrayList<>();

        for (ReviewEntity review : entityList) {
            ReviewDTO dto = new ReviewDTO(
                review.getReviewId(),
                review.getReviewer(),
                review.getPhone(),
                review.getTitle(),
                review.getDetail(),
                review.getWriteDate(),
                review.getUpdateDate(),
                review.getOriginalFileName()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }

    /**
     * 리뷰 쓰기
     * @param reviewDTO
     */
    public void writeReview(ReviewDTO reviewDTO) {
        String originalFileName = null;
        String savedFile = null;

        if(!reviewDTO.getReviewImage().isEmpty()){
            originalFileName = reviewDTO.getReviewImage().getOriginalFilename();
            savedFile = FileService.saveFile(reviewDTO.getReviewImage(), uploadPath);

            reviewDTO.setOriginalFileName(originalFileName);
            reviewDTO.setSaveFileName(savedFile);
        }
        log.info("=== 파일 저장 완료 : ", originalFileName);

        ReviewEntity reviewEntity = ReviewEntity.toEntity(reviewDTO);
        reviewRepository.save(reviewEntity);

        if (reviewEntity != null) { log.info("저장 완료");
        } else { log.error("저장 실패"); }

    }

}
