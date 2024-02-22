package org.choongang.admin;

import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("org.choongang.admin")
public class AdminMenuAdvice {

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "admin";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("admin");
    }
}
