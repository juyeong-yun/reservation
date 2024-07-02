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
	private YesorNo isCancle;
	private reserveState reserveState;
	private String cancleReason;
	private LocalDate eventDate;
	private String eventTime;

	
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
				.isCancle(entity.getIsCancle())
				.reserveState(entity.getReserveState())
				.cancleReason(entity.getCancleReason())
				.eventDate(eventDate)
				.eventTime(eventTime)
				.build();
	}

}
