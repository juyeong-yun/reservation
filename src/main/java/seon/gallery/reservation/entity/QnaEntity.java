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
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.check.YesorNo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name="qna")
public class QnaEntity {
	
	@Id
	@Column(name="qna_num")
	private Long qnaId;
	
	@Column(name="qna_name")
	private String qnaName;
	
	@Column(name="qna_title")
	private String qnaTitle;
	
	@Column(name="qna_detail")
	private String qnaDetail;
	
	@Column(name="write_date")
	private LocalDateTime writeDate;
	
	@Column(name="qna_pwd")
	private int qnaPwd;
	
	@Column(name="qna_answer")
	private String answer;
	
	@Column(name="is_lock")
	private YesorNo isLock;
	
	@Column(name="is_answer")
	private YesorNo isAnswer;
	
	public static QnaEntity toEntity(QnaDTO dto) {
		return QnaEntity.builder()
				.qnaId(dto.getQnaId())
				.qnaName(dto.getQnaName())
				.qnaTitle(dto.getQnaTitle())
				.qnaDetail(dto.getQnaDetail())
				.writeDate(dto.getWriteDate())
				.answer(dto.getAnswer())
				.isLock(dto.getIsLock())
				.isAnswer(dto.getIsAnswer())
				.build();
	}
	
}