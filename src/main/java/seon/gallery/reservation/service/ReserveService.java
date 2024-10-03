package seon.gallery.reservation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.event.spi.EventManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.dto.check.reserveState;
import seon.gallery.reservation.entity.EventEntity;
import seon.gallery.reservation.entity.NoticeEntity;
import seon.gallery.reservation.entity.ReserveEntity;
import seon.gallery.reservation.entity.ReviewEntity;
import seon.gallery.reservation.repository.EventRepository;
import seon.gallery.reservation.repository.ReserveRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReserveService {

    @Value("${user.board.pageLimit}")
	int pageLimit;
    
    private final EventRepository eventRepository;
    private final ReserveRepository reserveRepository;

    /**
     * 모든 목록 불러오는
     * @param eventId
     * @return
     */
    public Page<ReserveDTO> selectAll(Pageable pageable, String searchWord) {
        int page = pageable.getPageNumber() - 1; //페이지의 위치값은 0부터 시작하기 때문

        Page<ReserveEntity> entityList = null;

        if (searchWord == null || searchWord.isEmpty()) {
            entityList = reserveRepository.findAllWithEvent(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "reserveDate")));
        
        } else {
            entityList = reserveRepository.findAllWithEventAndSearchWord(searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "reserveDate")));
        }
        
        Page<ReserveDTO> dtoList = null;

        dtoList = entityList.map(reserve -> new ReserveDTO(
            reserve.getReserveId(),
            reserve.getEventEntity().getEventId(),
            reserve.getReserver(),
            reserve.getPhone(),
            reserve.getReserveDate(),
            reserve.getRequest(),
            reserve.getNumberOfReserve(),
            reserve.getKeyring(),
            reserve.getDepositor(),
            reserve.getReserveState(),
            reserve.getEventEntity().getEventDate(),
            reserve.getEventEntity().getEventTime())
        );
        return dtoList;
    }

    /**
     * 예약폼 추가
     * @param reserveDTO
     */
    public void reserveInsert(@ModelAttribute ReserveDTO reserveDTO) {
        
        EventEntity eventEntity = eventRepository.findById(reserveDTO.getEventId()).get();
        // log.info("eventEntity - {}",eventEntity); 
        
        // 디폴트값 채우기
        if (reserveDTO.getRequest().isEmpty()) {
            reserveDTO.setRequest("요청 없음");
        }
        reserveDTO.setIsCancel(YesorNo.N);
        reserveDTO.setReserveState(reserveState.waiting);
        
        if (reserveDTO.getDepositor()==null || reserveDTO.getDepositor().isEmpty()){
            reserveDTO.setDepositor(reserveDTO.getReserver());
        }

        ReserveEntity reserveEntity = ReserveEntity.toEntitiy(reserveDTO, eventEntity);
        reserveRepository.save(reserveEntity);

        if (reserveEntity != null) {
            log.info("저장 완료");
        } else {
            log.info("저장 실패");
        }
    }


    /**
     * 하나만 고르기
     * @param reserveId
     * @return
     */
    public ReserveDTO selectOne(Long reserveId) {
        Optional<ReserveEntity> entity = reserveRepository.findById(reserveId);

        if (entity.isPresent()) {
            ReserveEntity reserveEntity = entity.get();
            return ReserveDTO.toDTO(reserveEntity, reserveEntity.getEventEntity().getEventId(),
            reserveEntity.getEventEntity().getEventDate(), reserveEntity.getEventEntity().getEventTime());
            
        } else {
            return null;
        }
    }

    /**
     * 해당하는 아이디를 입금완료로 상태변경
     * @param reserveId
     */
    public void completeReservation(Long reserveId) {
        ReserveEntity reserveEntity = reserveRepository.findById(reserveId)
            .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다: " + reserveId));
        
        reserveEntity.setReserveState(reserveState.payed);
        reserveEntity.setPay(true);
        reserveEntity.setIsCancel(YesorNo.N);
        reserveRepository.save(reserveEntity);

        log.info("입금 수정 완료");
    }


    /**
     * 해당하는 id의 예약취소로 상태 변경
     * @param reserveId
     */
    public void cancelReservation(Long reserveId) {
        ReserveEntity reserveEntity = reserveRepository.findById(reserveId)
            .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다: " + reserveId));
        
        reserveEntity.setReserveState(reserveState.cancel);
        reserveEntity.setIsCancel(YesorNo.Y);
        reserveRepository.save(reserveEntity);

        log.info("입금 수정 완료");
    }

    public List<ReserveDTO> searchReserver(String reserver, String phone) {
        List<ReserveEntity> entityList = reserveRepository.findSearchReserver(reserver, phone);
        List<ReserveDTO> dtoList = new ArrayList<>();

        if (entityList.isEmpty()) {
            return null;
        }
        
        for (ReserveEntity reserve : entityList){
            ReserveDTO dto = ReserveDTO.toDTO(reserve, reserve.getEventEntity().getEventId(), 
            reserve.getEventEntity().getEventDate(), reserve.getEventEntity().getEventTime());
            dtoList.add(dto);
            }
            
        return dtoList;
    }

    


    
    
}
