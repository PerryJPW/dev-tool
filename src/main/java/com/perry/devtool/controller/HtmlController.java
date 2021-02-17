package com.perry.devtool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Perry
 * @date 2021/2/16
 */
@Controller
public class HtmlController {
    @RequestMapping("index")
    public String index(){
        return "html/index.html" ;
    }
}
