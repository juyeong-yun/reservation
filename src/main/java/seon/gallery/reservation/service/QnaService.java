package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Value("${user.board.pageLimit}")
	int pageLimit;


    /**
     * 모든 질문글 불러오기
     * @return
     */
    public Page<QnaDTO> selectAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; 

        Page<QnaEntity> entityList = qnaRepository.findAll(PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC, "writeDate")));
        Page<QnaDTO> dtoList = entityList.map(qna -> 
        new QnaDTO(
            qna.getQnaId(),
            qna.getQnaName(),
            qna.getTitle(),
            qna.getDetail(),
            qna.getWriteDate(),
            qna.getQnaPwd(),
            qna.getAnswer(),
            qna.isLock()  
        ));
        // List<QnaEntity> entityList = qnaRepository.findAll();
        // List<QnaDTO> dtoList = new ArrayList<>();

        // for (QnaEntity qna : entityList) {
        //     QnaDTO dto = new QnaDTO(
        //         qna.getQnaId(),
        //         qna.getQnaName(),
        //         qna.getTitle(),
        //         qna.getDetail(),
        //         qna.getWriteDate(),
        //         qna.getQnaPwd(),
        //         qna.getAnswer(),
        //         qna.isLock(),
        //         qna.getIsAnswer()
        //     );
        //     dtoList.add(dto);
        // }

        return dtoList;
    }


    /**
     * 글 작성
     * @param qnaDTO
     */
    public void writeQna(QnaDTO qnaDTO) {
        // 답변 여부
        qnaDTO.setIsAnswer(YesorNo.N);

        QnaEntity qnaEntity = QnaEntity.toEntity(qnaDTO);
        qnaRepository.save(qnaEntity);
        
        if (qnaEntity != null) {
            log.info("저장 완료");
        } else {
            log.error("저장 실패");
        }
    }


    public QnaDTO selectOne(Long qnaId) {
        Optional<QnaEntity> entity = qnaRepository.findById(qnaId);

        if (entity.isPresent()) {
            QnaEntity qnaEntity = entity.get();
            return QnaDTO.toDTO(qnaEntity);
        } else {
            return null;
        }

    }


}
