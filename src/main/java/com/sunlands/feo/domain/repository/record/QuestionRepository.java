package com.sunlands.feo.domain.repository.record;

import com.sunlands.feo.domain.model.record.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}
