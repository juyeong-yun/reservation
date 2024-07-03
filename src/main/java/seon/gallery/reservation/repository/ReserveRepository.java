package seon.gallery.reservation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import seon.gallery.reservation.entity.ReserveEntity;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    
    /**
     * 존재하는 이벤트에서 예약한 아이디 모두 조회
     * ++ 페이징 포함
     * @return
     */
    @Query("SELECT r FROM ReserveEntity r INNER JOIN r.eventEntity")
    Page<ReserveEntity> findAllWithEvent(PageRequest pageRequest);
    // List<ReserveEntity> findAllWithEvent();

    /**
     * reserve 페이지에서
     * 이벤트 아이디에 예약한 아이디 개수를 세는 쿼리 (isCancel 은 N을 포함하는)
     * @param eventId
     * @return
     */
    @Query("SELECT COUNT(r) FROM ReserveEntity r WHERE r.eventEntity.eventId =:eventId AND r.isCancel='N'")
    int countEventByReserveId(@Param("eventId") String eventId);

    /**
     * reserve 페이지에서 예약자의 이름과 번호로 찾아서 조회
     * (아직 미완성)
     * @param reserver
     * @param phone
     * @return
     */
    @Query("SELECT r FROM ReserveEntity r " +
    "WHERE r.reserver = :reserver AND r.phone = :phone")
    List<ReserveEntity> findAllSearchReserver(String reserver, String phone);
    
}
