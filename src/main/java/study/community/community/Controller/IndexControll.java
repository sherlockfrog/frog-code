package study.community.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.community.dto.QuestionDTO;
import study.community.community.mapper.QuestionMapper;
import study.community.community.mapper.UserMapper;
import study.community.community.model.Question;
import study.community.community.model.User;
import study.community.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexControll {
//    @GetMapping("hello")
//    public String hello(@RequestParam(name="name") String name,Model model)
//    {
//        model.addAttribute("name",name);
//        return "index";
//    }
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies==null || cookies.length ==0)
            return "index";
        for (Cookie cookie : cookies)
        {
            if(cookie.getName().equals("token"))
            {
                String token = cookie.getValue();
                User user = userMapper.selectByToken(token);
                if(user!=null)
                    request.getSession().setAttribute("user",user);
            }
        }
        //List<Question> questionlist = questionMapper.selectAll();
        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questions",questionDTOList);
        return "index";
    }
}
