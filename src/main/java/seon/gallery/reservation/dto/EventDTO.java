package seon.gallery.reservation.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.entity.EventEntity;

@AllArgsConstructor
@NoArgsConstructor 
@Getter
@Setter
@ToString
@Builder
public class EventDTO {

	private Long eventId;
	private LocalDateTime eventTime;
	private YesorNo isFull;
	
	public static EventDTO toDTO(EventEntity entity) {
		return EventDTO.builder()
				.eventId(entity.getEventId())
				.eventTime(entity.getEventTime())
				.isFull(entity.getIsFull())
				.build();
	}
}
