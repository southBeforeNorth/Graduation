package com.backend.feature.maintenance.notice.repository;

import com.backend.feature.maintenance.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, String> {
}
