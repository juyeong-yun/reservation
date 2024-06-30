package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.dto.check.YesorNo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name="reserve")
public class ReserveEntity {
	@SequenceGenerator(
		name = "reserve_seq"
		, sequenceName = "reserve_seq"
		,initialValue = 1
		, allocationSize = 1
	)
	
	@Id
	@GeneratedValue(generator = "reserve_seq")
	@Column(name="reserve_id")
	private Long reserveId;
	
	@Column(name="event_id")
	private String eventId;
	
	@Column(name="event_time")
	private String eventTime;
	
	@Column(name="reserver", nullable = false)
	private String reserver;
	
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Column(name="reserve_date")
	@CreationTimestamp
	private LocalDateTime reserveDate;

	@Column(name = "request")
	private String request;

	@Column(name = "number_of_reserve")
	private int numberOfReserve;

	@Column(name = "keyring")
	private int keyring;
	
	@Column(name="is_pay")
	private boolean isPay;
	
	@Column(name="is_confirm")
	@Enumerated(EnumType.STRING)
	private YesorNo isConfirm;
	
	@Column(name="is_cancle")
	@Enumerated(EnumType.STRING)
	private YesorNo isCancle;
	
	@Column(name="cancle_reason")
	private String cancleReason;
	
	public static ReserveEntity toEntitiy (ReserveDTO dto) {
		return ReserveEntity.builder()
				.reserveId(dto.getReserveId())
				.eventId(dto.getEventId())
				.eventTime(dto.getEventTime())
				.reserver(dto.getReserver())
				.phone(dto.getPhone())
				.reserveDate(dto.getReserveDate())
				.request(dto.getRequest())
				.numberOfReserve(dto.getNumberOfReserve())
				.keyring(dto.getKeyring())
				.isPay(dto.isPay())
				.isConfirm(dto.getIsConfirm())
				.isCancle(dto.getIsCancle())
				.cancleReason(dto.getCancleReason())
				.build();
	}

}
