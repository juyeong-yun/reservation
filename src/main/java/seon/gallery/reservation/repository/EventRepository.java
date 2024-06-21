package seon.gallery.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import seon.gallery.reservation.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity,Long> {
    
}
