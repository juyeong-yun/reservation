package seon.gallery.reservation.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.check.reserveState;
import seon.gallery.reservation.entity.EventEntity;

@AllArgsConstructor
@NoArgsConstructor 
@Getter
@Setter
@ToString
@Builder
public class EventDTO {

	private String eventId;
	// LocalDateTime 으로 저장하기 위한 패턴 설정
	private LocalDate eventDate;
	private String eventTime;
	private boolean isFull;
	private int reserveCount; // 이벤트에 제출된 예약개수 세기
	

	/**
	 * reserveCount 미존재
	 * @param entity
	 * @return
	 */
	public static EventDTO toDTO(EventEntity entity) {
		return EventDTO.builder()
				.eventId(entity.getEventId())
				.eventDate(entity.getEventDate())
				.eventTime(entity.getEventTime())
				.isFull(entity.isFull())
				.build();
	}

	/**
	 * reserveCount 존재
	 * @param entity
	 * @param reserveCount
	 * @return
	 */
	public static EventDTO toDTO(EventEntity entity, int reserveCount) {
		return EventDTO.builder()
				.eventId(entity.getEventId())
				.eventDate(entity.getEventDate())
				.eventTime(entity.getEventTime())
				.isFull(entity.isFull())
				.reserveCount(reserveCount)
				.build();
	}
}
