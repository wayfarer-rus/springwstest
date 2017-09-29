package home.wayfarer.springtests.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

    @RequestMapping("/wsclient")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Anonymous") String name, Model model) {
        model.addAttribute("name", name);
        return "wsclient";
    }
}