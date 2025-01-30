package com.asejnr.quiz_service.dto;

import lombok.Data;

@Data
public class QuizDto {
    String category;
    int numberOfQuestions;
    String title;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "QuizDto{" +
                "category='" + category + '\'' +
                ", numberOfQuestions=" + numberOfQuestions +
                ", title='" + title + '\'' +
                '}';
    }
}
