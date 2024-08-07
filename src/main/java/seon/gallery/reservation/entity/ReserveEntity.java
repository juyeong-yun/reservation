package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import seon.gallery.reservation.dto.check.reserveState;

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
	
	//FK
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="event_id")
	private EventEntity eventEntity;
	
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

	@Column(name = "depositor")
	private String depositor;
	
	@Column(name="is_pay")
	private boolean isPay;
	
	@Column(name="is_cancel")
	@Enumerated(EnumType.STRING)
	private YesorNo isCancel;
	
	@Column(name = "reserve_state")
	@Enumerated(EnumType.STRING)
	private reserveState reserveState;

	@Column(name="cancel_reason")
	private String cancelReason;
	
	public static ReserveEntity toEntitiy (ReserveDTO dto, EventEntity eventEntity) {
		return ReserveEntity.builder()
				.reserveId(dto.getReserveId())
				.eventEntity(eventEntity)
				.reserver(dto.getReserver())
				.phone(dto.getPhone())
				.reserveDate(dto.getReserveDate())
				.request(dto.getRequest())
				.numberOfReserve(dto.getNumberOfReserve())
				.keyring(dto.getKeyring())
				.depositor(dto.getDepositor())
				.isPay(dto.isPay())
				.isCancel(dto.getIsCancel())
				.reserveState(dto.getReserveState())
				.cancelReason(dto.getCancelReason())
				.build();
	}

}
