package com.asejnr.quiz_service.service;

import com.asejnr.quiz_service.exception.QuizNotFoundException;
import com.asejnr.quiz_service.feign.QuizInterface;
import com.asejnr.quiz_service.model.QuestionWrapper;
import com.asejnr.quiz_service.model.Quiz;
import com.asejnr.quiz_service.model.Response;
//import com.asejnr.quiz_service.repository.QuestionRepository;
import com.asejnr.quiz_service.repository.QuizRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
     QuizInterface quizInterface;


    public ResponseEntity<Quiz> createQuiz(String category, int numberOfQuestions, String title) {
        List<Integer> questionIds = quizInterface.getQuestionIdsForQuiz(category,numberOfQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setNumberOfQuestions(numberOfQuestions);
        quiz.setQuestionIds(questionIds);

        Quiz savedQuiz = quizRepository.save(quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        try {
            Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));

            List<Integer> questionIds = quiz.getQuestionIds();
            if (questionIds.isEmpty()) {
                throw new QuizNotFoundException("Quiz with id " + id + " not found.");
            }
            List<QuestionWrapper> questionsForUser = quizInterface.getQuestionsFromIds(questionIds).getBody();
            if (questionsForUser == null || questionsForUser.isEmpty()) {
                throw new QuizNotFoundException("Questions with id " + id + " not found.");
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        }catch (QuizNotFoundException e) {
            throw e;
        } catch (FeignException e){
            throw new RuntimeException("Error getting questions for quiz with id " + id, e);
        }
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
       return quizInterface.getScore(responses);
    }
}
