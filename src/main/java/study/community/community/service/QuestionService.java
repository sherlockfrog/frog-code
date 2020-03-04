package study.community.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.community.dto.QuestionDTO;
import study.community.community.mapper.QuestionMapper;
import study.community.community.mapper.UserMapper;
import study.community.community.model.Question;
import study.community.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questions = questionMapper.selectAll();
//        System.out.println(questions.get(0).getCommentCount());
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);//拷贝相同名字的属性
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

}
