package seon.gallery.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import seon.gallery.reservation.entity.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>{

    
} 