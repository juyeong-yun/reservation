package seon.gallery.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import seon.gallery.reservation.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {
    
}
