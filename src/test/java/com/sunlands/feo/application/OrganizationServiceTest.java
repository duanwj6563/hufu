package com.sunlands.feo.application;

import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yangchao on 17/11/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationServiceTest {
    @Autowired
    private OrganizationService organizationService;

    @Test
    public void testQueryOrgLevels() throws Exception {

    }

    @Test
    public void testCreateOrg() throws Exception {

    }

    @Test
    public void testQueryOrgsByLevel() throws Exception {
        Object o = organizationService.queryOrgsByLevel(OrganizationLevel.SALES_DEPARTMENT);
        //Object o = organizationService.queryOrgsByLevel(OrganizationLevel.ARMY);
        //ObjectMapper ob = new ObjectMapper();
        //ob.writeValueAsString(o);
        //System.out.println(new ObjectMapper().writeValueAsString(o));
    }
}