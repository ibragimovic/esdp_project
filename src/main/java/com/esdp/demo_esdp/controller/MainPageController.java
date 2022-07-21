package com.esdp.demo_esdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    @GetMapping("/main")
    public String getMainPage(Model model,
                              @PageableDefault(size = 3,page=0, sort = "data_add", direction = Sort.Direction.DESC)
                                      Pageable pageable) {
        return "main_page";
    }
}
