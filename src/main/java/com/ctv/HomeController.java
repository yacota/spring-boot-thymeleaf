package com.ctv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jllach
 */
@Controller
class HomeController {

    @RequestMapping("/")
    String index() {
        return "index";
    }

}