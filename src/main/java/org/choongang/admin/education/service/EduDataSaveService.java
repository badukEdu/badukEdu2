package org.choongang.admin.education.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.education.controllers.RequestEduData;
import org.choongang.admin.education.entities.EduData;
import org.choongang.admin.education.repositories.EduDataRepository;
import org.choongang.file.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class EduDataSaveService {

    private final EduDataRepository eduDataRepository;
    private final FileUploadService fileUploadService;

    /**
     * 교육 자료 등록/수정
     * @param form
     */
    public void save(RequestEduData form) {
        Long num = form.getNum();
        String mode = form.getMode();
        mode= StringUtils.hasText(mode) ? mode : "add";

        EduData eduData = null;
        if(mode.equals("edit") && num != null) {
            eduData = eduDataRepository.findById(num)
                .orElseThrow(EduDataNotFoundException::new);
        } else {
            eduData = new EduData();
            eduData.setGid(form.getGid());
        }

        eduData.setName(form.getName());
        eduData.setDataType(form.getDataType());
        eduData.setContentType(form.getContentType());
        eduData.setServiceType(form.getServiceType());
        eduData.setContent(form.getContent());
        eduData.setFileAddress(form.getFileAddress());

        eduDataRepository.saveAndFlush(eduData);

        fileUploadService.processDone(eduData.getGid());

    }
}
