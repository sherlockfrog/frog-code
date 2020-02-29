package study.community.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexControll {
    @GetMapping("hello")
    public String hello(@RequestParam(name="name") String name,Model model)
    {
        model.addAttribute("name",name);
        return "index";
    }

    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/index")
    public String index1(){return "index";}
}
