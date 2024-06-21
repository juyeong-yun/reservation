package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

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

    public List<QnaDTO> getqnaList() {
        List<QnaEntity> entityList = qnaRepository.findAll();
        List<QnaDTO> dtoList = new ArrayList<>();

        for (QnaEntity entity : entityList) {
            QnaDTO dto = new QnaDTO();
            
            // QnaEntity에서 QnaDTO로 필드 복사
            dto.setQnaName(entity.getQnaName());
            dto.setTitle(entity.getTitle());
            dto.setWriteDate(entity.getWriteDate());

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
        
        // 디폴트값 생성
        qnaDTO.setIsAnswer(YesorNo.N);
        
        if (qnaDTO.getQnaPwd() != null && !qnaDTO.getQnaPwd().isEmpty()) {
            qnaDTO.setIsLock(YesorNo.Y);

        } else {
            qnaDTO.setIsLock(YesorNo.N);
        }

         // QnaDTO를 QnaEntity로 변환하여 저장
        QnaEntity qnaEntity = QnaEntity.toEntity(qnaDTO) 
        qnaRepository.save(qnaEntity);
        
        if (qnaEntity != null) {
            log.info("저장 완료");
        } 
    }
}
