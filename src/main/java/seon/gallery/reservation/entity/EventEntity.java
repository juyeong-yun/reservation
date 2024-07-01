package seon.gallery.reservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.EventDTO;

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
	private String eventId;

	@Column(name = "event_date")
	private LocalDate eventDate;
	
	@Column(name="event_time")
	private String eventTime;
	
	@Column(name="is_full")
	private boolean isFull;
	
	@OneToMany(mappedBy = "eventEntity", fetch = FetchType.LAZY)
	@OrderBy("event_id")
	private List<ReserveEntity> reserveList;
	
	public static EventEntity toEntity(EventDTO dto) {
		return EventEntity.builder()
				.eventId(dto.getEventId())
				.eventDate(dto.getEventDate())
				.eventTime(dto.getEventTime())
				.isFull(dto.isFull())
				.build();
	}

}
