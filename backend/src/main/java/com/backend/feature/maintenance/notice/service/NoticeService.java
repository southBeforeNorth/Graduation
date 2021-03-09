package com.backend.feature.maintenance.notice.service;

import com.backend.feature.maintenance.notice.dto.NoticeDTO;
import com.backend.feature.maintenance.notice.entity.Notice;
import com.backend.feature.maintenance.notice.repository.NoticeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public NoticeDTO create(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDTO, notice);
        Notice save = noticeRepository.saveAndFlush(notice);
        NoticeDTO res = new NoticeDTO();
        BeanUtils.copyProperties(save, res);
        return res;
    }

    public NoticeDTO getNoticeDTO() {
        List<Notice> notices = noticeRepository.findAll();
        NoticeDTO res = new NoticeDTO();
        BeanUtils.copyProperties(notices.get(0), res);
        return res;
    }
}
