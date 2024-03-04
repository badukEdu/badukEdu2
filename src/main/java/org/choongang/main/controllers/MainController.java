package org.choongang.main.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.service.BoardService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final BoardService boardService;

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("guide");
    }

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[] { "main/main"};
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("noticeList" ,boardService.getTop5Notice());

        return "main/index";
    }

}

