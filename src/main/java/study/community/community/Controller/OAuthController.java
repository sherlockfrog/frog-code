package study.community.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.community.dto.Access_TokenDTO;
import study.community.community.dto.GitHubUser;
import study.community.community.provider.GitHubProvider;

@Controller
public class OAuthController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.url}")
    private String client_url;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state)
    {
        Access_TokenDTO access_tokenDTO = new Access_TokenDTO();
        access_tokenDTO.setCode(code);
        access_tokenDTO.setRedirect_url(client_url);
        access_tokenDTO.setClient_id(client_id);
        access_tokenDTO.setClient_secret(client_secret);
        access_tokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(access_tokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
