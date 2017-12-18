package com.feo.domain.repository.record;

import com.feo.domain.model.record.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huang on 2017/11/7.
 */
public interface QuestionTypeRepository extends JpaRepository<QuestionType,Long> {

    List<QuestionType> findAllByParentIdAndType(Long id,Integer type);
}
