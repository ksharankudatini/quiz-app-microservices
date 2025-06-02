package com.appaji.question_service.service;


import com.appaji.question_service.dao.QuestionDao;
import com.appaji.question_service.model.Question;
import com.appaji.question_service.model.QuestionWrapper;
import com.appaji.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(String categoryName, int numOfQuestions) {
        List<Integer> questionsIds = questionDao.findRandomQuestionsByCategory(categoryName, numOfQuestions);
        return new ResponseEntity<>(questionsIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questionIds) {
        List<QuestionWrapper> questionWrappers = questionIds.stream().map(i -> {
            Question question = questionDao.findById(i).get();
            return new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
        }).toList();
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer count = (int) responses.stream().filter(r -> questionDao.findById(r.getId()).get().getRightAnswer().equals(r.getResponse())).count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
