package seon.gallery.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import seon.gallery.reservation.entity.ReviewEntity;

@Repository
public interface ReserveRepository extends JpaRepository<ReviewEntity, Long> {
    
}
