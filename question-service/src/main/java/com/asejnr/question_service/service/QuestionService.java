package com.asejnr.question_service.service;

import com.asejnr.question_service.exception.QuestionNotFoundException;
import com.asejnr.question_service.exception.ResourceNotFoundException;
import com.asejnr.question_service.model.Question;
import com.asejnr.question_service.model.QuestionWrapper;
import com.asejnr.question_service.model.Response;
import com.asejnr.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public boolean existsById(int id) {
        return questionRepository.existsById(id);
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new QuestionNotFoundException("Questions not found");
        }
        return questions;
    }

    public Question createQuestion(Question question) {
        try{
            return questionRepository.save(question);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Invalid question data: " + e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Error creating question: " + e.getMessage());
        }

    }

    public List<Question> getQuestionsByCategoryIgnoreCase(String category) {
        try {
            List<Question> questions = questionRepository.findByCategoryIgnoreCase(category);
            if (questions.isEmpty()) {
                throw new ResourceNotFoundException("No questions found for category: " + category);
            }
            return questions;
        }catch (ResourceNotFoundException e){
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Error fetching questions by category: " + e.getMessage());
        }

    }

    public Question updateQuestion(int id, Question question) {
        Question existingQuestion = questionRepository.findById(id).orElseThrow();
        if(!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException("Question not found");
        }
        existingQuestion.setQuestionTitle(question.getQuestionTitle());
        existingQuestion.setCategory(question.getCategory());
        existingQuestion.setOption1(question.getOption1());
        existingQuestion.setOption2(question.getOption2());
        existingQuestion.setOption3(question.getOption3());
        existingQuestion.setOption4(question.getOption4());
        existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
        existingQuestion.setRightAnswer(question.getRightAnswer());


        return questionRepository.save(existingQuestion);
    }

    public void deleteQuestion(int id) {
        if(!questionRepository.existsById(id)){
            throw new QuestionNotFoundException("Question not found");
        }
        questionRepository.deleteById(id);
    }


    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(String category, int numberOfQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(category, numberOfQuestions);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (Integer questionId : questionIds) {
            questions.add(questionRepository.findById(questionId).get());
        }

        for (Question question : questions) {
          QuestionWrapper questionWrapper = new QuestionWrapper();
          questionWrapper.setId(question.getId());
          questionWrapper.setQuestionTitle(question.getQuestionTitle());
          questionWrapper.setOption1(question.getOption1());
          questionWrapper.setOption2(question.getOption2());
          questionWrapper.setOption3(question.getOption3());
          questionWrapper.setOption4(question.getOption4());

          questionWrappers.add(questionWrapper);
        }
        System.out.println(questionWrappers);
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;

        for (Response response : responses){
            Question question = questionRepository.findById(response.getId()).get();

            if (response.getResponse().equals(question.getRightAnswer()))
                right++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
