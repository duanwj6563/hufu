package com.sunlands.feo.domain.repository.strategy.impl;

import com.sunlands.feo.common.EntityManagerUtil;
import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import com.sunlands.feo.domain.model.orgInfo.Organization;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.port.rest.dto.CommonPage;
import com.sunlands.feo.port.rest.dto.strategy.SelectStrategyCondition;
import com.sunlands.feo.domain.repository.strategy.StrategyDao;
import com.sunlands.feo.domain.repository.user.UserRepository;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Repository
public class StrategyRepositoryImpl implements StrategyDao {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManagerUtil entityManagerUtil;

    @Override
    public CommonPage queryStrategys(SelectStrategyCondition selectStrategyCondition) {
        StringBuffer stringBuffer=new StringBuffer();
        String sql="select id from hufu_strategy where ";
        stringBuffer.append(getSql(sql,selectStrategyCondition));
        int startNum=selectStrategyCondition.getPageSize()*(selectStrategyCondition.getPageNum()-1);
        stringBuffer.append(" ORDER BY update_time DESC ");
        stringBuffer.append(" limit "+startNum+" , "+selectStrategyCondition.getPageSize());
        List rows = entityManagerUtil.getList(stringBuffer.toString());
        CommonPage page=new CommonPage();
        page.setTotalElements(getTotalElements(selectStrategyCondition));
        page.setSize(selectStrategyCondition.getPageSize());
        page.setTotalPages();
        page.setPageNumber(selectStrategyCondition.getPageNum());
        List<Object> list=new ArrayList<>();
        for (Object obj : rows) {
            Map row = (Map) obj;
            BigInteger id1= (BigInteger)row.get("id");
            long id = id1.longValue();
            list.add(id);
        }
        page.setContent(list);
        return page;
    }

    public BigInteger getTotalElements(SelectStrategyCondition selectStrategyCondition){
        StringBuffer stringBuffer=new StringBuffer();
        String sql="select count(*) from hufu_strategy where ";
        stringBuffer.append(getSql(sql,selectStrategyCondition));
        Map<String, Object> map = entityManagerUtil.getMap(stringBuffer.toString());
        BigInteger count = (BigInteger) map.get("count(*)");
        return count;
    }

    public String getSql(String sql,SelectStrategyCondition selectStrategyCondition){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(sql);
        if (selectStrategyCondition.getArea()!=null){
            stringBuffer.append(" area = "+selectStrategyCondition.getArea()+" and ");
        }
        Integer status=selectStrategyCondition.getStatus();
        Integer auditStatus=selectStrategyCondition.getAuditStatus();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (auditStatus==null){
            if (status==null||status==4){
                if (user.getOrganization().getOrganizationLevel()== OrganizationLevel.ARMY){
                    stringBuffer.append(" ( status >= 0 or uid= "+user.getId()+" ) and ");
                }else {
                    stringBuffer.append(" ( status > 0 or uid= "+user.getId()+" ) and ");
                }
            }else {
                stringBuffer.append(" status = "+status+" and ");
            }
        }else if (auditStatus==0){
            stringBuffer.append(" status > 0 and ");
        }else if (auditStatus==1){
            if (status==null||status==4){
                stringBuffer.append(" status > 1 and ");
            }else {
                stringBuffer.append(" status = "+status+" and ");
            }
        }else if (auditStatus==2){
            stringBuffer.append(" status = "+1+" and ");
        }
        if (selectStrategyCondition.getFirstProjectId()!=null){
            stringBuffer.append(" first_project_id = "+selectStrategyCondition.getFirstProjectId()+" and ");
        }
        List<Organization> organizations = selectStrategyCondition.getOrganizations();
        if (organizations.size()>0){
            String sql1="( ";
            for (Organization organization :
                    organizations) {
                sql1=sql1+organization.getId()+", ";
            }
            int index1 = sql1.lastIndexOf(", ");
            sql1=sql1.substring(0,index1);
            sql1=sql1+")";
            stringBuffer.append(" id in ( select strategy_id from hufu_strategy_applydepart where apply_dept_id in "+sql1+") and ");
        }
        sql=stringBuffer.toString();
        int index = sql.lastIndexOf("and ");
        sql=sql.substring(0,index);
        return sql;
    }
}
