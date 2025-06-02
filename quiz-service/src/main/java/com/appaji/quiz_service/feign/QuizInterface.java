package com.appaji.quiz_service.feign;

import com.appaji.quiz_service.model.QuestionWrapper;
import com.appaji.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("/question/generate")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String categoryName, @RequestParam int numOfQuestions);

    @PostMapping("/question/questions/by-ids")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds);

    @PostMapping("/question/score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
