package com.asejnr.question_service.repository;

import com.asejnr.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategoryIgnoreCase(String category);

    @Query(value = "SELECT q.id FROM question q WHERE LOWER(q.category)=LOWER(:category) ORDER BY RANDOM() LIMIT :numberOfQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(@Param("category") String category,@Param("numberOfQuestions") int numberOfQuestions);
}
