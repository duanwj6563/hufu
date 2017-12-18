package com.feo.application.record;

import com.feo.domain.model.record.ViolationType;
import com.feo.domain.model.record.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 质检Service
 * Created by huang on 2017/11/9.
 */
public interface RecordCommonService {

    /**
     * 质检问题二级联动
     *
     * @param type 质检问题类型
     * @param id   质检问题id
     * @return 质检问题集合
     */
    List<QuestionType> selectRecordQuestionGanged(Integer type, Long id);

    /**
     * 质检违规三级联动
     *
     * @param id 质检违规id
     * @return 质检违规集合
     */
    List<ViolationType> selectRecordViolationGanged(Long id);

    /**
     * sop负责四级组织结构
     *
     * @param id 组织结构id
     * @return 组织结构集合
     */
    Set<Map<String, Object>> selectOrgs(Integer id);

    /**
     * 所有四级组织结构
     *
     * @param id 组织结构id
     * @return 组织结构id
     */
    List<Map<String, Object>> selectAllOrgs(Integer id);
}
