package seon.gallery.reservation.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.QnaDTO;
import seon.gallery.reservation.dto.check.YesorNo;
import seon.gallery.reservation.repository.QnaRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaService {

    private QnaRepository qnaRepository;

    public void writeQna(QnaDTO qnaDTO) {
        
        // 디폴트값 생성
        qnaDTO.setIsAnswer(YesorNo.N);

        if () {
            
        }

    }
    
}
