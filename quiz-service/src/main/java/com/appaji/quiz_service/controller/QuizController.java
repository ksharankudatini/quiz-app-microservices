package com.appaji.quiz_service.controller;


import com.appaji.quiz_service.model.QuestionWrapper;
import com.appaji.quiz_service.model.QuizDto;
import com.appaji.quiz_service.model.Response;
import com.appaji.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit")
    public ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> responses) {
        return quizService.calculateResult(responses);
    }
}
