package org.choongang.guide;

import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice({"org.choongang.guide", "org.choongang.board"})
public class GuideMenuAdvice {

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "guide";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("guide");
    }
}
