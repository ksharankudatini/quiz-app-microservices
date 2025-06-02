package com.appaji.question_service.controller;


import com.appaji.question_service.model.Question;
import com.appaji.question_service.model.QuestionWrapper;
import com.appaji.question_service.model.Response;
import com.appaji.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private Environment environment;

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String categoryName, @RequestParam int numOfQuestions) {
        return questionService.getQuestionIdsForQuiz(categoryName, numOfQuestions);
    }

    @PostMapping("/questions/by-ids")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds) {
        System.err.println("Instance Port Number Invoked :  " + environment.getProperty("local.server.port"));
        return questionService.getQuestionsById(questionIds);
    }

    @PostMapping("/score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}
