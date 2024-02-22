package org.choongang.subscription;

import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("org.choongang.subscription")
public class SubscriptionMenuAdvice {

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "subscription";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("subscription");
    }
}
