package com.PetCare;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/api/sample")
    @ResponseBody
    public String sample1() {
        return "hihi";
    }

    @GetMapping("/sample")
    public String sample2() {
        return "index";
    }

}
