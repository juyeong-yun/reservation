package seon.gallery.reservation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.event.spi.EventManager;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.json.OracleJsonParser.Event;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.dto.check.reserveState;
import seon.gallery.reservation.entity.EventEntity;
import seon.gallery.reservation.entity.ReserveEntity;
import seon.gallery.reservation.entity.ReviewEntity;
import seon.gallery.reservation.repository.EventRepository;
import seon.gallery.reservation.repository.ReserveRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReserveService {
    private final EventRepository eventRepository;
    private final ReserveRepository reserveRepository;

    /**
     * 모든 목록 불러오는
     * @param eventId
     * @return
     */
    public List<ReserveDTO> selectAll() {

        List<ReserveEntity> entityList = reserveRepository.findAllWithEvent();
        List<ReserveDTO> dtoList = new ArrayList<>();
        
        for (ReserveEntity reserve : entityList) {
            String eventId = reserve.getEventEntity().getEventId();
            LocalDate eventDate = reserve.getEventEntity().getEventDate();
            String eventTime = reserve.getEventEntity().getEventTime();
            ReserveDTO dto = ReserveDTO.toDTO(reserve, eventId, eventDate, eventTime);
            
            dtoList.add(dto);
        }
        
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

    public ReserveDTO searchReserver(String reserver, String phone) {
        // List<ReserveEntity> entity = reserveRepository.findAllSearchReserver(reserver, phone);

        // if (!entity.isEmpty()) {
        //     ReserveDTO reserveDTO = new ReserveDTO();
        //     reserveDTO.setReserver(entity.getReserver());
        // }
        return null;
    }

    


    
    
}
