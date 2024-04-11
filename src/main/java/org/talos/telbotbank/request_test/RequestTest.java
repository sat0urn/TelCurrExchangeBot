package org.talos.telbotbank.request_test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestTest {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
