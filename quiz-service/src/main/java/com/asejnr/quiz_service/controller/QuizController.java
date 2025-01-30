package com.asejnr.quiz_service.controller;

import com.asejnr.quiz_service.model.QuestionWrapper;
import com.asejnr.quiz_service.model.Quiz;
import com.asejnr.quiz_service.model.Response;
import com.asejnr.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(
            @RequestParam String category,
            @RequestParam int numberOfQuestions,
            @RequestParam String title
            ) {
        Quiz quiz = quizService.createQuiz(category, numberOfQuestions, title).getBody();
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
