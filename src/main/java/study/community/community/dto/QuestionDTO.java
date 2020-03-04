package study.community.community.dto;

import lombok.Data;
import study.community.community.model.User;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private String tag;
    private String description;
    private User user;
}
