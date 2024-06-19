package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.EventDTO;
import seon.gallery.reservation.dto.check.YesorNo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="event")
public class EventEntity {
	
	@Id
	@Column(name="event_id")
	private Long eventId;
	
	@Column(name="event_time", nullable = false)
	private LocalDateTime eventTime;
	
	@Column(name="is_full")
	@Enumerated(EnumType.STRING)
	private YesorNo isFull;
	
	
	public static EventEntity toEntity(EventDTO dto) {
		return EventEntity.builder()
				.eventId(dto.getEventId())
				.eventTime(dto.getEventTime())
				.isFull(dto.getIsFull())
				.build();
	}

}
