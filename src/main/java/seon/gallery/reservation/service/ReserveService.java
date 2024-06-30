package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.ReserveDTO;
import seon.gallery.reservation.dto.ReviewDTO;
import seon.gallery.reservation.entity.ReserveEntity;
import seon.gallery.reservation.entity.ReviewEntity;
import seon.gallery.reservation.repository.ReserveRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;

    public List<ReserveDTO> selectAll() {
        List<ReserveEntity> entityList = reserveRepository.findAll();
        List<ReserveDTO> dtoList = new ArrayList<>();

        for (ReserveEntity reserve : entityList) {
            ReserveDTO dto = ReserveDTO.toDTO(reserve);
            dtoList.add(dto);
        }

        return dtoList;
    }
    
    public void reserveInsert(ReserveDTO reserveDTO) {
        log.info("예약 하러옴");
        
        ReserveEntity reserveEntity = ReserveEntity.toEntitiy(reserveDTO);
        reserveRepository.save(reserveEntity);

        if (reserveEntity != null) {
            log.info("저장 완료");
        } else {
            log.info("저장 실패");
        }
    }
    
    
}
