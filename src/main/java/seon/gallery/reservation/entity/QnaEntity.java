package seon.gallery.reservation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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

	@SequenceGenerator(
		name = "qna_seq"
		, sequenceName = "qna_seq"
		, initialValue = 1
		,allocationSize = 1
	)
	
	@Id
	@GeneratedValue(generator = "qna_seq")
	@Column(name="qna_id")
	private Long qnaId;
	
	@Column(name="qna_name", nullable = false)
	private String qnaName;
	
	@Column(name="title")
	private String title;
	
	@Column(name="detail")
	private String detail;
	
	@Column(name="write_date")
	@CreationTimestamp
	private LocalDateTime writeDate;
	
	@Column(name="qna_pwd")
	private String qnaPwd;
	
	@Column(name="qna_answer")
	private String answer;
	
	@Column(name="is_lock")
	private boolean isLock;
	
	@Column(name="is_answer")
	@Enumerated(EnumType.STRING)
	private YesorNo isAnswer;
	
	public static QnaEntity toEntity(QnaDTO dto) {
		return QnaEntity.builder()
				.qnaId(dto.getQnaId())
				.qnaName(dto.getQnaName())
				.title(dto.getTitle())
				.detail(dto.getDetail())
				.writeDate(dto.getWriteDate())
				.qnaPwd(dto.getQnaPwd())
				.answer(dto.getAnswer())
				.isLock(dto.isLock())
				.isAnswer(dto.getIsAnswer())
				.build();
	}
	
}
