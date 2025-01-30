package com.asejnr.quiz_service.feign;

import com.asejnr.quiz_service.model.QuestionWrapper;
import com.asejnr.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("/api/v1/questions/generate")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String category, @RequestParam int numberOfQuestions );

    @PostMapping("/api/v1/questions/get-quiz-questions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionIds);

    @PostMapping("/api/v1/questions/get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
