package seon.gallery.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seon.gallery.reservation.dto.NoticeDTO;
import seon.gallery.reservation.entity.NoticeEntity;
import seon.gallery.reservation.repository.NoticeRepository;
import seon.gallery.reservation.util.FileService;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    
    /**
     * notice 저장 된걸 불러오는
     * @return
     */
    public List<NoticeDTO> selectAll() {
        List<NoticeEntity> entityList = noticeRepository.findAll();
        List<NoticeDTO> dtoList = new ArrayList<>();

        for (NoticeEntity notice : entityList){
            NoticeDTO dto = NoticeDTO.toDTO(notice);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Value("${spring.servlet.multipart.location}")
    String uploadPath;

    /**
     * notice 작성
     * @param noticeDTO
     */
    public void writeNotice(NoticeDTO noticeDTO) {
        String originalFileName = null;
        String savedFile = null;

        if(noticeDTO.getNoticeImage() == null) {
            log.info("파일이 비어있습니다.");
        }

        if (noticeDTO.getNoticeImage() != null && !noticeDTO.getNoticeImage().isEmpty()) {
            originalFileName = noticeDTO.getNoticeImage().getOriginalFilename();
            savedFile = FileService.saveFile(noticeDTO.getNoticeImage(), uploadPath);

            log.info("파일 이름", originalFileName);

            noticeDTO.setOriginalFileName(originalFileName);
            noticeDTO.setSavedFileName(savedFile);
            
            log.info("=== 파일 저장 완료: {}", originalFileName);
        }

        NoticeEntity noticeEntity = NoticeEntity.toEntity(noticeDTO);
        noticeRepository.save(noticeEntity);

        if (noticeEntity != null) {
            log.info("저장 완료");
        } else {
            log.error("저장 실패");
        }
    }

    public NoticeDTO selectOne(Long noticeId) {
        Optional<NoticeEntity> entity = noticeRepository.findById(noticeId);
    
        if (entity.isPresent()) {
            NoticeEntity noticeEntity = entity.get();
            return NoticeDTO.toDTO(noticeEntity);
        } else {
            // Optional에서 값이 존재하지 않는 경우 처리 (예외처리, 기본값 설정 등)
            return null; // 또는 기본값을 리턴하거나 예외를 던지는 등의 처리를 추가할 수 있습니다.
        }
    }



}
