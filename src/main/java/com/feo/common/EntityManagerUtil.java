package com.feo.common;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 原生sql持久化工具类
 * Created by huang on 2017/12/1.
 */
@Component
public class EntityManagerUtil {

    //持久化依赖
    private EntityManager entityManager;

    //通用map合集方法
    public Map<String, Object> getMap(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        Object object = query.getSingleResult();
        return (Map<String, Object>) object;
    }

    //通用List合集方法
    public List<Map<String, Object>> getList(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List rows = query.getResultList();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object obj : rows) {
            Map row = (Map) obj;
            list.add(row);
        }
        return list;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
