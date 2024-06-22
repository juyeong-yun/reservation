package seon.gallery.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import seon.gallery.reservation.entity.QnaEntity;
@Repository
public interface QnaRepository extends JpaRepository<QnaEntity,Long>{
    
}
