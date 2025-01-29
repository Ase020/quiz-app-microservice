package com.asejnr.question_service.controller;

import com.asejnr.question_service.model.QuestionWrapper;
import com.asejnr.question_service.model.Response;
import com.asejnr.question_service.service.QuestionService;
import com.asejnr.question_service.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() throws Exception {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question newQuestion = questionService.createQuestion(question);
        System.out.println(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion);
    }
    @GetMapping("/category")
    public  ResponseEntity<List<Question>> getQuestionsByCategory(@RequestParam(name = "category") String category) {
        List<Question> questions = questionService.getQuestionsByCategoryIgnoreCase(category);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question, @PathVariable int id) {
        if (!questionService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Question updatedQuestion = questionService.updateQuestion(id,question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id) {
        if(!questionService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String category, @RequestParam int numberOfQuestions ) {
        return questionService.getQuestionIdsForQuiz(category, numberOfQuestions);
    }

    @GetMapping("/get-quiz-questions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestParam List<Integer> questionIds) {
        return questionService.getQuestionsFromIds(questionIds);
    }

    @PostMapping()
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
