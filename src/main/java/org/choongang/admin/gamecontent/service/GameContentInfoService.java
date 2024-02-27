package org.choongang.admin.gamecontent.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.controllers.GameContentSearch;
import org.choongang.admin.gamecontent.controllers.RequestGameContentData;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.entities.QGameContent;
import org.choongang.admin.gamecontent.repositories.GameContentRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.entities.StudyGroup;
import org.choongang.teacher.group.repositories.StGroupRepository;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class GameContentInfoService {

    private final FileInfoService fileInfoService;
    private final GameContentRepository gameContentRepository;
    private final HttpServletRequest request;
    private final HttpSession session;//표찬-사용중
    private final StGroupRepository stGroupRepository;//표찬-사용중

    /**
     * 게임 콘텐츠 검색
     * @return
     */
    public ListData<GameContent> getList(GameContentSearch search) {

        return getList(search, false);
    }

    public ListData<GameContent> getList(GameContentSearch search, boolean isAll) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QGameContent gameContent = QGameContent.gameContent;

        /* 검색 조건 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression gameTitleConds = gameContent.gameTitle.contains(skey);
            BooleanExpression gameContentConds = gameContent.packageContents.contains(skey);
            if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(gameTitleConds).or(gameContentConds));

            } else if (sopt.equals("gameTitle")) {
                andBuilder.and(gameTitleConds);
            } else if (sopt.equals("packageContents")) {
                andBuilder.and(gameContentConds);
            }
        }

        if (!isAll) { // 구독 신청 가능 상품으로 한정 조회
            andBuilder.and(gameContent.endDate.loe(Expressions.dateTimeTemplate(LocalDate.class, "ADD_MONTHS(SYSDATE, {0})", gameContent.subscriptionMonths.intValue())))
                    .and(gameContent.startDate.goe(LocalDate.now()));

        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<GameContent> data = gameContentRepository.findAll(andBuilder, pageable);
        int total = (int)gameContentRepository.count(andBuilder);

        data.getContent().forEach(this::addInfo);

        Pagination pagination = new Pagination(page, total, 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }

    public GameContent getById(Long num) {

        GameContent data = gameContentRepository.getById(num);
        addInfo(data);

        return data;
    }

    public RequestGameContentData getForm(Long num) {
        GameContent data = getById(num);
        RequestGameContentData form = new ModelMapper().map(data, RequestGameContentData.class);
        form.setNum(data.getNum());
        return form;
    }

    public void addInfo(GameContent data) {
        List<FileInfo> items = fileInfoService.getListDone(data.getGid());
        if(items != null && !items.isEmpty()) data.setThumbnail(items.get(0));

    }

    public List<GameContent> getList(List<Long> nums) {

        QGameContent gameContent = QGameContent.gameContent;
        List<GameContent> items = (List<GameContent>)gameContentRepository.findAll(gameContent.num.in(nums));

        items.forEach(this::addInfo);

        return items;
    }

    /**
     * 결제 요약 정보
     *
     * @param nums : 게임 콘텐츠 번호
     *
     * @return
     *          items : 선택한 게임콘텐츠
     *          totalPayment : 결제 총합
     */
    public Map<String, Object> getOrderSummary(List<Long> nums) {
        List<GameContent> items = getList(nums);

        if (items == null || items.isEmpty()) {
            throw new GameContentNotFoundException();
        }

        long totalPayment = items.stream().mapToLong(GameContent::getSalePrice).sum();
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("totalPayment", totalPayment);

        return data;
    }

    /** [표찬]
     * 해당 게임 컨텐츠로 개설한 스터디 그룹의 최대 구독자 수의 합 리턴
     * @param num - 게임 컨텐츠 num
     * @return
     */
    public Long stgroupCount(Long num){
        List<StudyGroup> list = stGroupRepository.findAll();
        GameContent gameContent = getById(num);
        Long count = 0L;
        Member member = (Member)session.getAttribute("member");
        for(StudyGroup s : list){
            if(member.getUserId().equals(s.getMember().getUserId())){
                if(s.getGameContent().equals(gameContent)){
                    count = count + s.getMaxSubscriber();
                }
            }

        }
        return count;
    }


}
