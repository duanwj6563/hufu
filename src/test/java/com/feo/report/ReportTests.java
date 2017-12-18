package com.feo.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feo.domain.repository.report.AnalysisRepository;
import com.feo.domain.model.reportform.Analyze;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duanwj
 * 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReportTests {
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void hello() throws JsonProcessingException {
        Pageable pageable = new PageRequest(1, 3);
        Specification<Analyze> je = new Specification<Analyze>() {
            @Override
            public Predicate toPredicate(Root<Analyze> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.between(root.get("createDate").as(String.class), "2017-11", "2017-11-23"));
                //军团
                predicates.add(criteriaBuilder.equal(root.get("legion"), "俊园"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Analyze> analyzes = analysisRepository.findAll(je, pageable);

        System.out.println("1" + objectMapper.writeValueAsString(analyzes));

    }


}
