package org.choongang.education;

import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("org.choongang.education")
public class EducationMenuAdvice {

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "education";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("education");
    }
}
