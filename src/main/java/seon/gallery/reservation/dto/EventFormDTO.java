package seon.gallery.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.entity.EventEntity;

@AllArgsConstructor
@NoArgsConstructor 
@Getter
@Setter
@ToString
@Builder
public class EventFormDTO {

	/**
	 * 이벤트 생성 30분 마다 자동 저장을 위한 DTO
	 */
	private LocalDate eventDate;
	private String startTime;
	private String endTime;

	public static EventEntity toEntity(EventFormDTO formDTO) {
		return EventEntity.builder()
				.eventDate(formDTO.getEventDate())
				.eventTime(formDTO.getStartTime())
				.eventTime(formDTO.getEndTime())
				.build();
	}
}
