package org.talos.telbotbank.request_check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestCheck {
    @GetMapping("/")
    public String index() {
        return "hello";
    }
}
