package seon.gallery.reservation.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.repository.ReviewRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void writeReview(ReviewDTO reviewDTO) {
        // if (!reviewDTO.getReviewImage().isEmpty()) {
        //     originalFileName = reviewDTO.getReviewImage().getOriginalFilename();

        //     reviewDTO.setOriginalFileName(originalFileName);
        // }
    }
}
