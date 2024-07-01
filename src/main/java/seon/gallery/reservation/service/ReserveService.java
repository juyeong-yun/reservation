package seon.gallery.reservation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.event.spi.EventManager;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.json.OracleJsonParser.Event;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.dto.ReviewDTO;
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
    public List<ReserveDTO> selectAll(String eventId) {
        List<ReserveEntity> entityList = reserveRepository.findByEventIdWithEvent(eventId);
        log.info("id", eventId);
        List<ReserveDTO> dtoList = new ArrayList<>();
        
        for (ReserveEntity reserve : entityList) {
            EventEntity eventEntity = reserve.getEventEntity();
            if (eventEntity != null) {
                LocalDate eventDate = eventEntity.getEventDate();
                String eventTime = eventEntity.getEventTime();
                ReserveDTO dto = ReserveDTO.toDTO(reserve, eventId, eventDate, eventTime);
                dtoList.add(dto);
            } else {
                // 예외 처리 또는 로깅
                log.warn("EventEntity is null for ReserveEntity with eventId: {}", eventId);
            }
        }
        
        return dtoList;
    }
    
    /**
     * 예약 추가
     * @param reserveDTO
     */
    public void reserveInsert(ReserveDTO reserveDTO) {
        log.info("예약 하러옴");

        EventEntity eventEntity = eventRepository.findById(reserveDTO.getEventId()).get();
        ReserveEntity reserveEntity = ReserveEntity.toEntitiy(reserveDTO, eventEntity);
        reserveRepository.save(reserveEntity);

        if (reserveEntity != null) {
            log.info("저장 완료");
        } else {
            log.info("저장 실패");
        }
    }
    
    
}
