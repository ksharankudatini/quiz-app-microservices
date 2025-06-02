package com.appaji.quiz_service.service;


import com.appaji.quiz_service.dao.QuizDao;
import com.appaji.quiz_service.feign.QuizInterface;
import com.appaji.quiz_service.model.QuestionWrapper;
import com.appaji.quiz_service.model.Quiz;
import com.appaji.quiz_service.model.QuizDto;
import com.appaji.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(QuizDto quizDto) {
        List<Integer> questions = quizInterface.getQuestionIdsForQuiz(quizDto.getCategoryName(), quizDto.getNumberOfQuestions()).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitleName());
        quiz.setQuestionsIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        try {
            List<Integer> questionsIds = quizDao.findById(id).get().getQuestionsIds();
            return quizInterface.getQuestionsById(questionsIds);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> calculateResult(List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
