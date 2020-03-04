package study.community.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.community.mapper.QuestionMapper;
import study.community.community.mapper.UserMapper;
import study.community.community.model.Question;
import study.community.community.model.User;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish()
    {
        return "publish";
    }
    @PostMapping("/publish")
    public String dopublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @CookieValue(value = "token" , required = false) String token,
            Model model)
    {
        User user = userMapper.selectByToken(token);
        if (user==null)
        {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        {//赋值
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
        }
        questionMapper.insert(question);
        return "redirect:/";
    }
}
