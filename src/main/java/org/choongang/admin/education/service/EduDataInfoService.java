package org.choongang.admin.education.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.choongang.admin.education.controllers.EduDataSearch;
import org.choongang.admin.education.controllers.RequestEduData;
import org.choongang.admin.education.entities.EduData;
import org.choongang.admin.education.entities.QEduData;
import org.choongang.admin.education.repositories.EduDataRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class EduDataInfoService {

    private final FileInfoService fileInfoService;
    private final EduDataRepository eduDataRepository;
    private final HttpServletRequest request;

    /**
     * 교육 자료 검색
     * @return
     */
    public ListData<EduData> getList(EduDataSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QEduData eduData = QEduData.eduData;

        /* 검색 조건 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression nameConds = eduData.name.contains(skey);
            BooleanExpression contentConds = eduData.content.contains(skey);
            if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(nameConds).or(contentConds));

            } else if (sopt.equals("name")) {
                andBuilder.and(nameConds);
            } else if (sopt.equals("content")) {
                andBuilder.and(contentConds);
            }
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<EduData> data = eduDataRepository.findAll(andBuilder, pageable);
        int total = (int)eduDataRepository.count(andBuilder);

        data.getContent().forEach(this::addInfo);

        Pagination pagination = new Pagination(page, total, 10, limit ,request);

        return new ListData<>(data.getContent(), pagination);
    }

    public EduData getById(Long num) {

        EduData data = eduDataRepository.getById(num);
        addInfo(data);

        return data;
    }

    public RequestEduData getForm(Long num) {
        EduData data = getById(num);
        RequestEduData form = new ModelMapper().map(data, RequestEduData.class);
        form.setNum(data.getNum());
        return form;
    }

    private void addInfo(EduData data) {
        List<FileInfo> items = fileInfoService.getListDone(data.getGid());
        if(items != null && !items.isEmpty()) data.setThumbnail(items.get(0));

    }
}
