package com.sunlands.feo.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.TimeUtil;
import com.sunlands.feo.common.DateUtil;
import com.sunlands.feo.common.JwtTokenUtil;
import com.sunlands.feo.domain.model.reportform.Analyze;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.domain.repository.report.AnalysisRepository;
import com.sunlands.feo.domain.repository.user.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.AntPathMatcher;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
