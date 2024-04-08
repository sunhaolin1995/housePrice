package org.haolin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/hello/{uuid}")
    public String hello(@PathVariable String uuid) {
        System.out.println(uuid);
        return "modelAndView";
    }
}
