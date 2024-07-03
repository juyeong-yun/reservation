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
	private String title;
	private String detail;
	private LocalDateTime writeDate;
	private String qnaPwd;
	private String answer;
	private boolean isLock;
	private YesorNo isAnswer;

	// 페이징 위한 생성자
	public QnaDTO(Long qnaId, String qnaName, String title, String detail, LocalDateTime writeDate, 
	String qnaPwd, String answer, boolean isLock) {
		this.qnaId = qnaId;
		this.qnaName = qnaName;
		this.title = title;
		this.detail = detail;
		this.writeDate = writeDate;
		this.qnaPwd = qnaPwd;
		this.answer = answer;
		this.isLock = isLock;
    }
	
	public static QnaDTO toDTO(QnaEntity entity) {
		return QnaDTO.builder()
				.qnaId(entity.getQnaId())
				.qnaName(entity.getQnaName())
				.title(entity.getTitle())
				.detail(entity.getDetail())
				.qnaPwd(entity.getQnaPwd())
				.writeDate(entity.getWriteDate())
				.answer(entity.getAnswer())
				.isLock(entity.isLock())
				.isAnswer(entity.getIsAnswer())
				.build();
	}

    
}
