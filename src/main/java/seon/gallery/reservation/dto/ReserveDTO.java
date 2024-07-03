package seon.gallery.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.dto.check.reserveState;
import seon.gallery.reservation.entity.ReserveEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ReserveDTO {
	
	private Long reserveId;
	private String eventId; //FK
	private String reserver;
	private String phone;
	private LocalDateTime reserveDate;
	private String request;
	private int numberOfReserve;
	private int keyring;
	private String depositor;
	private boolean isPay;
	private YesorNo isCancel;
	private reserveState reserveState;
	private String cancelReason;
	private LocalDate eventDate;
	private String eventTime;


	// 페이징을 위한 생성자
	public ReserveDTO (Long reserveId, String eventId, String reserver, String phone, LocalDateTime reserveDate, 
	String request, int numberOfReserve, int keyring, String depositor, reserveState reserveState,
	LocalDate eventDate, String eventTime){
		super();
		this.reserveId = reserveId;
		this.eventId = eventId;
		this.reserver = reserver;
		this.phone = phone;
		this.reserveDate = reserveDate;
		this.request = request;
		this.numberOfReserve = numberOfReserve;
		this.keyring = keyring;
		this.depositor = depositor;
		this.reserveState = reserveState;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
	}

	
	public static ReserveDTO toDTO (ReserveEntity entity,String eventId, LocalDate eventDate, String eventTime) {
		return ReserveDTO.builder()
				.reserveId(entity.getReserveId())
				.eventId(eventId)
				.reserver(entity.getReserver())
				.phone(entity.getPhone())
				.reserveDate(entity.getReserveDate())
				.request(entity.getRequest())
				.numberOfReserve(entity.getNumberOfReserve())
				.keyring(entity.getKeyring())
				.depositor(entity.getDepositor())
				.isPay(entity.isPay())
				.isCancel(entity.getIsCancel())
				.reserveState(entity.getReserveState())
				.cancelReason(entity.getCancelReason())
				.eventDate(eventDate)
				.eventTime(eventTime)
				.build();
	}

}
