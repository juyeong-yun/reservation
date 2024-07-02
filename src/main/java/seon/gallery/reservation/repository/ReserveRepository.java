package seon.gallery.reservation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import seon.gallery.reservation.entity.ReserveEntity;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    
    @Query("SELECT r FROM ReserveEntity r INNER JOIN r.eventEntity")
    List<ReserveEntity> findAllWithEvent();

    // @Query("SELECT r FROM ReserveEntity r JOIN FETCH r.eventEntity e WHERE e.eventId = :eventId")
    // List<ReserveEntity> findByEventIdWithEvent(@Param("eventId") String eventId);

}
