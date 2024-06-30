package seon.gallery.reservation.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.entity.EventEntity;
import seon.gallery.reservation.repository.EventRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    /**
     * 이벤트 전체 불러오기
     * @param eventDTO
     * @return
     */
    public List<EventDTO> selectAll() {

        List<EventEntity> entityList = eventRepository.findAll();
        List<EventDTO> dtoList = new ArrayList<>();

        for (EventEntity event : entityList) {
            EventDTO dto = new EventDTO(
                event.getEventId(),
                event.getEventDate(),
                event.getEventTime(),
                event.isFull()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 이벤트 추가
     * @param eventDTO
     */
    public void eventInsert (EventDTO eventDTO){
        try {

            // eventId 생성
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    
            String datePart = eventDTO.getEventDate().format(dateFormatter);
    
            String generatedId = datePart +'-'+ eventDTO.getEventTime().replace(":", "");
    
            // EventEntity 생성 및 설정
            EventEntity eventEntity = EventEntity.toEntity(eventDTO);
            eventEntity.setEventId(generatedId);
    
            // EventEntity 저장
            eventRepository.save(eventEntity);
            log.info("저장 완료");
            
        } catch (Exception e) {
            log.error("저장 실패: " + e.getMessage());
            // 예외 처리 또는 로깅 등 필요한 추가 작업
        }
    }
}
