package seon.gallery.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import seon.gallery.reservation.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,String> {
	
    
}
