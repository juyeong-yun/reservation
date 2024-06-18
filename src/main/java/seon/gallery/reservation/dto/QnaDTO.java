package seon.gallery.reservation.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.entity.QnaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class QnaDTO {

	private Long qnaId;
	private String qnaName;
	private String qnaTitle;
	private String qnaDetail;
	private LocalDateTime writeDate;
	private int qnaPwd;
	private String answer;
	private YesorNo isLock;
	private YesorNo isAnswer;
	
	public static QnaDTO toDTO(QnaEntity entity) {
		return QnaDTO.builder()
				.qnaId(entity.getQnaId())
				.qnaName(entity.getQnaName())
				.qnaTitle(entity.getQnaTitle())
				.qnaDetail(entity.getQnaDetail())
				.writeDate(entity.getWriteDate())
				.answer(entity.getAnswer())
				.isLock(entity.getIsLock())
				.isAnswer(entity.getIsAnswer())
				.build();
	}
}
