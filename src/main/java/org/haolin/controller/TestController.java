package org.haolin.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("test")
public class TestController {
    /**用UUID模拟变量**/
    @RequestMapping("/test1")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        //String res ="redirect:/example/hello/";
        String res ="/example/hello/";
        for (int i = 0; i <55 ; i++) {
          res=res  +UUID.randomUUID();
        }
        try {
            response.sendRedirect(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return res;
    }


}