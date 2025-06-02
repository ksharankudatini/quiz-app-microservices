package com.appaji.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String categoryName;
    private Integer numberOfQuestions;
    private String titleName;
}
