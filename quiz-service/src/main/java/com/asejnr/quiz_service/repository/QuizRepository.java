package com.asejnr.quiz_service.repository;

import com.asejnr.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
