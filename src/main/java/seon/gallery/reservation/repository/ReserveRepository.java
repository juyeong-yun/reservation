package seon.gallery.reservation.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    
    /**
     * 예약자 이름과 비교하여 해당하는 아이디 조회
     * ++ 페이징 포함
     * @param searchWord
     * @param pageRequest
     * @return
     */
    @Query("SELECT r FROM ReserveEntity r INNER JOIN r.eventEntity e WHERE r.reserver LIKE %:searchWord%")
    Page<ReserveEntity> findAllWithEventAndSearchWord(@Param("searchWord") String searchWord, PageRequest pageRequest);

    /**
     * reserve 페이지에서
     * 이벤트 아이디에 예약한 아이디 개수를 세는 쿼리 (isCancel 은 N을 포함하는)
     * @param eventId
     * @return
     */
    @Query("SELECT COUNT(r) FROM ReserveEntity r WHERE r.eventEntity.eventId =:eventId AND r.isCancel='N'")
    int countEventByReserveId(@Param("eventId") String eventId);
    
    /**
     * (미완성)
	 * 날짜와 시간별 예약 수 세기
	 * @return
	 */
	@Query("SELECT e.eventDate, e.eventTime, COUNT(r) AS count FROM ReserveEntity r INNER JOIN r.eventEntity e WHERE r.isCancel='N' GROUP BY e.eventDate, e.eventTime")
	List<ReserveEntity> countReservationByDateandTime();

	
    /**
     * reserve 페이지에서 예약자의 이름과 번호로 찾아서 조회
     * @param reserver
     * @param phone
     * @return
     */
    @Query("SELECT r FROM ReserveEntity r INNER JOIN r.eventEntity WHERE r.reserver = :reserver AND r.phone = :phone")
    List<ReserveEntity> findSearchReserver(@Param("reserver")String reserver, @Param("phone") String phone);
    
    
    
}
