package org.choongang.admin.order.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.admin.order.controllers.OrderSearch;
import org.choongang.admin.order.entities.OrderInfo;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.admin.order.entities.QOrderItem;
import org.choongang.admin.order.repositories.OrderInfoRepository;
import org.choongang.admin.order.repositories.OrderItemRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
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
public class OrderInfoService {
    private final HttpServletRequest request;
    private final OrderInfoRepository orderInfoRepository;
    private final GameContentInfoService gameContentInfoService;
    private final OrderItemRepository orderItemRepository;
    private final MemberUtil memberUtil;

    public OrderInfo get(Long orderNo) {
        OrderInfo data = orderInfoRepository.findById(orderNo).orElseThrow(OrderNotFoundException::new);
        List<OrderItem> items = data.getOrderItems();

        items.forEach(this::addOrderItemInfo);

        return data;
    }

    public ListData<OrderItem> getList(OrderSearch search) {
        return getList(search, false);
    }

    public ListData<OrderItem> getList(OrderSearch search, boolean isAll) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QOrderItem orderItem = QOrderItem.orderItem;

        /* 검색 조건 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression gameTitleConds = orderItem.gameTitle.contains(skey);

            if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(gameTitleConds));

            } else if (sopt.equals("gameTitle")) {
                andBuilder.and(gameTitleConds);
            }
        }

        if (!isAll) {
            andBuilder.and(orderItem.orderInfo.member.eq(memberUtil.getMember()));
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<OrderItem> data = orderItemRepository.findAll(andBuilder, pageable);
        int total = (int)orderItemRepository.count(andBuilder);

        data.getContent().forEach(this::addOrderItemInfo);

        Pagination pagination = new Pagination(page, total, 10, limit ,request);

        //////표찬-사용중//////
        for(OrderItem o : data.getContent()){
            gameContentInfoService.stgroupCount(o.getGameContent().getNum());
            GameContent gameContent = o.getGameContent();
            gameContent.setStgroupCount2(gameContent.getMaxSubscriber() - gameContentInfoService.stgroupCount(gameContent.getNum()));
        }
        //////표찬-사용중//////

        return new ListData<>(data.getContent(), pagination);
    }

    private void addOrderItemInfo(OrderItem item) {
        GameContent game = item.getGameContent();
        gameContentInfoService.addInfo(game);

    }
}
