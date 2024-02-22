package org.choongang.teacher;

import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("org.choongang.teacher")
public class TeacherMenuAdvice {

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "teacher";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("teacher");
    }
}
