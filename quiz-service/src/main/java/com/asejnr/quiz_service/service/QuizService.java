package com.asejnr.quiz_service.service;

import com.asejnr.quiz_service.feign.QuizInterface;
import com.asejnr.quiz_service.model.QuestionWrapper;
import com.asejnr.quiz_service.model.Quiz;
import com.asejnr.quiz_service.model.Response;
//import com.asejnr.quiz_service.repository.QuestionRepository;
import com.asejnr.quiz_service.repository.QuizRepository;
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
//        Optional<Quiz> quiz = quizRepository.findById(id);
//        if (quiz.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        List<Question> questions = quiz.get().getQuestions();
//        if (questions == null || questions.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
//
//        for (Question question : questions) {
//            QuestionWrapper questionWrapper = new QuestionWrapper(
//                    question.getId(),
//                    question.getQuestionTitle(),
//                    question.getOption1(),
//                    question.getOption2(),
//                    question.getOption3(),
//                    question.getOption4());
//            questionsForUser.add(questionWrapper);
//        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
//            Optional<Quiz> quiz = quizRepository.findById(id);
//            if (quiz.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            List<Question> questions = quiz.get().getQuestions();
//            if (questions == null || questions.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
            int right = 0;
//            int increment = 0;
//            for (Response response : responses) {
//                if (response.getResponse().equals(questions.get(increment).getRightAnswer()))
//                    right++;
//
//                increment++;
//            }
            return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
