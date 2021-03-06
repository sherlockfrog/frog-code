package study.community.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.community.dto.Access_TokenDTO;
import study.community.community.dto.GitHubUser;
import study.community.community.mapper.UserMapper;
import study.community.community.model.User;
import study.community.community.provider.GitHubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OAuthController {
    @Autowired
    private GitHubProvider gitHubProvider;

   @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.url}")
    private String client_url;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse response)
    {
        Access_TokenDTO access_tokenDTO = new Access_TokenDTO();
        access_tokenDTO.setCode(code);
        access_tokenDTO.setRedirect_url(client_url);
        access_tokenDTO.setClient_id(client_id);
        access_tokenDTO.setClient_secret(client_secret);
        access_tokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(access_tokenDTO);
        GitHubUser githubUser = gitHubProvider.getUser(accessToken);
        //System.out.println(accessToken);
        if(githubUser!=null && githubUser.getName()!=null)
        {
            //登录成功
            request.getSession().setAttribute("user",githubUser);
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setPhoto(githubUser.getAvatar_url());
            user.setToken(token);
            user.setAccoundId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            userMapper.insert(user);

            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }
        else
        {
            //登录失败

            return "redirect:/";
        }
    }
}
