package seon.gallery.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import seon.gallery.reservation.entity.ReviewEntity;

public interface ReserveRepository extends JpaRepository<ReviewEntity, Long> {
    
}
