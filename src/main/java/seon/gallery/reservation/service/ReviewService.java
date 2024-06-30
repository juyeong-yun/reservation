package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.entity.ReviewEntity;
import seon.gallery.reservation.repository.ReviewRepository;
import seon.gallery.reservation.util.FileService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> selectAll() {

        List<ReviewEntity> entityList = reviewRepository.findAll();
        List<ReviewDTO> dtoList = new ArrayList<>();

        for (ReviewEntity review : entityList) {
            ReviewDTO dto = ReviewDTO.toDTO(review);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Value("${spring.servlet.multipart.location}")
    String uploadPath;
    //  없던 폴더가 생겼으니 경로는 맞음

    /**
     * 리뷰 쓰기
     * @param reviewDTO
     */
    public void writeReview(ReviewDTO reviewDTO) {
        log.info("저장경로: {} ", uploadPath);

        String originalFileName = null;
        String savedFile = null;
        if (reviewDTO.getReviewImage() == null) {
            log.info("파일이 비어있습니다.");
        }
        if (reviewDTO.getReviewImage() != null && !reviewDTO.getReviewImage().isEmpty()) {
            originalFileName = reviewDTO.getReviewImage().getOriginalFilename();
            savedFile = FileService.saveFile(reviewDTO.getReviewImage(), uploadPath);

            log.info("파일 이름", originalFileName);

            reviewDTO.setOriginalFileName(originalFileName);
            reviewDTO.setSavedFileName(savedFile);
            
            log.info("=== 파일 저장 완료: {}", originalFileName);
        }

        ReviewEntity reviewEntity = ReviewEntity.toEntity(reviewDTO);
        reviewRepository.save(reviewEntity);

        if (reviewEntity != null) {
            log.info("저장 완료");
        } else {
            log.error("저장 실패");
        }
        
    }

    public ReviewDTO selectOne(Long reviewId) {
        Optional<ReviewEntity> entity = reviewRepository.findById(reviewId);

        if (entity.isPresent()) {
            ReviewEntity reviewEntity = entity.get();
            return ReviewDTO.toDTO(reviewEntity);            
        } else {
            return null;
        }
    }
    
}
