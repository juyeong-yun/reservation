package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.entity.QnaEntity;
import seon.gallery.reservation.repository.QnaRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaService {
    
    private final QnaRepository qnaRepository;

    /**
     * 모든 질문글 불러오기
     * @return
     */
    public List<QnaDTO> selectAll() {

        List<QnaEntity> entityList = qnaRepository.findAll();
        List<QnaDTO> dtoList = new ArrayList<>();

        for (QnaEntity qna : entityList) {
            QnaDTO dto = new QnaDTO(
                qna.getQnaId(),
                qna.getQnaName(),
                qna.getTitle(),
                qna.getDetail(),
                qna.getWriteDate(),
                qna.getQnaPwd(),
                qna.getAnswer(),
                qna.isLock(),
                qna.getIsAnswer()
            );
            dtoList.add(dto);
        }

        log.info("여기는 list service");

        return dtoList;
    }


    /**
     * 글 작성
     * @param qnaDTO
     */
    public void writeQna(QnaDTO qnaDTO) {
    
        QnaEntity qnaEntity = QnaEntity.toEntity(qnaDTO);
        qnaRepository.save(qnaEntity);
        
        if (qnaEntity != null) {
            log.info("저장 완료");
        } else {
            log.error("저장 실패");
        }
    }

}