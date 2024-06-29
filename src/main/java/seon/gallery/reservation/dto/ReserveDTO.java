package seon.gallery.reservation.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.entity.ReserveEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ReserveDTO {
	
	private Long reserveId;
	private Long eventId;
	private LocalDateTime eventTime;
	private String reserver;
	private String phone;
	private LocalDateTime reserveDate;
	private String request;
	private int numberOfReserve;
	private int keyring;
	private YesorNo isPay;
	private YesorNo isConfirm;
	private YesorNo isCancle;
	private String cancleReason;
	
	public static ReserveDTO toDTO (ReserveEntity entity) {
		return ReserveDTO.builder()
				.reserveId(entity.getReserveId())
				.eventId(entity.getEventId())
				.eventTime(entity.getEventTime())
				.reserver(entity.getReserver())
				.phone(entity.getPhone())
				.reserveDate(entity.getReserveDate())
				.request(entity.getRequest())
				.numberOfReserve(entity.getNumberOfReserve())
				.keyring(entity.getKeyring())
				.isPay(entity.getIsPay())
				.isConfirm(entity.getIsConfirm())
				.isCancle(entity.getIsCancle())
				.cancleReason(entity.getCancleReason())
				.build();
	}

}
