package org.choongang.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final static Map<String, List<MenuDetail>> menus;

    static {

        menus = new HashMap<>();


    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}
