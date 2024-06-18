package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
	@Id
	@Column(name="reserve_num")
	private Long reserveId;
	
	@Column(name="reserve_name")
	private String reserveName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="reserve_date")
	private LocalDateTime reserveDate;
	
	@Column(name="is_pay")
	private YesorNo isPay;
	
	@Column(name="is_confirm")
	private YesorNo isConfirm;
	
	@Column(name="is_cancle")
	private YesorNo isCancle;
	
	@Column(name="cancle_reason")
	private String cancleReason;
	
	public static ReserveEntity toEntitiy (ReserveDTO dto) {
		return ReserveEntity.builder()
				.reserveName(dto.getReserveName())
				.phone(dto.getPhone())
				.reserveDate(dto.getReserveDate())
				.isPay(dto.getIsPay())
				.isConfirm(dto.getIsConfirm())
				.isCancle(dto.getIsCancle())
				.cancleReason(dto.getCancleReason())
				.build();
	}

}
