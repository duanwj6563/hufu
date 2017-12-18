package com.feo.springBoot;

import com.feo.common.DateUtil;
import com.feo.common.JwtTokenUtil;
import com.feo.domain.model.record.Chance;
import com.feo.domain.model.record.Record;
import com.feo.domain.model.record.ViolationInfo;
import com.feo.domain.model.strategy.*;
import com.feo.domain.model.user.User;
import com.feo.domain.model.user.UserInfo;
import com.feo.domain.repository.record.ChanceRepository;
import com.feo.domain.repository.record.RecordRepository;
import com.feo.domain.repository.strategy.StrategyRepository;
import org.ho.yaml.Yaml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QDKTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    StrategyRepository strategyRepository;

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    ChanceRepository chanceRepository;

    @Test
    public void getTest(){
        System.out.println(jwtTokenUtil.generateToken());
    }

    @Test
    public void yaml(){
        Strategy strategy=new Strategy();
        strategy.setId(11111111l);
        strategy.setCreateDate(new Date());
        strategy.setName("虎符策略");
        User user=new User();
        user.setName("zhangsan");
        user.setUsername("admin");
        user.setId(1);
//        strategy.setUser(user);
        Set<StrategyPhase> strategyPhases=new HashSet<>();
        StrategyPhase first=new FirstStrategyPhase();
        StrategyPhase sec=new SecStrategyPhase();
        StrategyPhase fin=new FinalStrategyPhase();
        strategyPhases.add(first);
        strategyPhases.add(sec);
        strategyPhases.add(fin);
        strategy.setStrategyPhases(strategyPhases);

        File dumpFile = new File("D:\\aaaa\\testYaml.yaml");
        try {
            Yaml.dump(strategy, dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void insertStrategy(){
        for (int i=0;i<10;i++){
            Chance chance = new Chance();
            chance.setStuName("张三");
            chance.setPhoneNum("13912345678");
            chance.setSex(0);
            chance.setIsApply(0);
            chance.setSignDate(new Date());
            chance.setOrderParentId("19343");
            chance.setOrderSonId("1232009213");
            chance.setMajorTypeOneName("自考");
            chance.setMajorTypeTwoName("行专人本（国开）");
            chance.setFee("13999.00");
            chanceRepository.saveAndFlush(chance);
            Chance chance1=chanceRepository.findOne(chance.getId());
//            Set<Record> records = chance1.getRecords();
            ViolationInfo violationInfo = new ViolationInfo();
            violationInfo.setIsViolation(0);
            violationInfo.setBadTime(90);
            violationInfo.setViolationTypeOneId(1L);
            violationInfo.setViolationTypeOneName("违规违规一");
            violationInfo.setViolationTypeTwoId(2L);
            violationInfo.setViolationTypeTwoName("违规违规二");
            violationInfo.setViolationTypeThreeId(3L);
            violationInfo.setViolationTypeThreeName("违规违规三");
            Strategy strategy=new Strategy();
            strategy.setArea(i%4);
            strategy.setFirstProjectId(i%3);
            strategy.setUid(2);
            strategy.setName("测试军团"+i);
            strategy.setCompareKill("竞争对手截杀"+i);
            strategy.setCompareContent("竞争内容"+i);
            strategy.setComparePrice(i+"10000");
            strategy.setAnalyzeAdvantage("竞争优势分析"+i);
            strategy.setCompetitor("竞争对手");
            strategy.setCreateDate(new Date());
            strategy.setStatus(i%4);
            strategy.setSubmitDate(new Date());
            Set<StrategyPhase> strategyPhases = strategy.getStrategyPhases();
            FirstStrategyPhase firstStrategyPhase=new FirstStrategyPhase();
            firstStrategyPhase.setPhase(1);
            SecStrategyPhase secStrategyPhase=new SecStrategyPhase();
            secStrategyPhase.setPhase(2);
            FinalStrategyPhase finalStrategyPhase=new FinalStrategyPhase();
            finalStrategyPhase.setPhase(3);
            Set<SecondProject> secondProjects=new HashSet<>();
            Set<Need> needs=new HashSet<>();
            Set<Need> needs1=new HashSet<>();
            Set<KillStrategy> killStrategies=new HashSet<>();
            Set<KillStrategy> killStrategies2=new HashSet<>();
            Set<KillStrategy> killStrategies3=new HashSet<>();
            Set<ClassSize> classSizes=new HashSet<>();
            Set<ClassSize> classSizes1=new HashSet<>();
            Set<ClassSize> classSizes2=new HashSet<>();
            Set<TriggerOpen> triggerOpens=new HashSet<>();
            Set<AssistTool> assistTools=new HashSet<>();
            Set<SolveFirstProblem> solveFirstProblems=new HashSet<>();
            Set<StrategyLog> strategyLogs=new HashSet<>();
            for(int j=0;j<7;j++){
                for (int m=0;m<7;m++){
                    StrategyLog strategyLog=new StrategyLog();
                    strategyLog.setContent("策略测试日志"+j+m);
                    strategyLog.setDate(new Date());
                    User user=new User();
                    user.setId(m%6+1);
                    strategyLog.setUser(user);
                    strategyLogs.add(strategyLog);
                }
                SecondProject secondProject=new SecondProject();
                secondProject.setDescription(i+"主推专业"+j);
                secondProject.setNumber(j+1);
                secondProject.setFirstProjectName("自考");
                secondProject.setType("主推专业");
                secondProjects.add(secondProject);
                Need need=new Need();
                need.setNeedName(i+"need"+j);
                need.setNumber(j+1);
                needs.add(need);
                Need need1=new Need();
                need1.setNeedName(i+"need"+j);
                need1.setNumber(j+1);
                needs1.add(need1);
                KillStrategy killStrategy=new KillStrategy();
                killStrategy.setDescription(i+"截杀"+j);
                killStrategy.setNumber(j+1);
                killStrategies.add(killStrategy);
                KillStrategy killStrategy2=new KillStrategy();
                killStrategy2.setDescription(i+"截杀"+j);
                killStrategy2.setNumber(j+1);
                killStrategies2.add(killStrategy2);
                KillStrategy killStrategy3=new KillStrategy();
                killStrategy3.setDescription(i+"截杀"+j);
                killStrategy3.setNumber(j+1);
                killStrategies3.add(killStrategy3);
                ClassSize classSize=new ClassSize();
                classSize.setDescription(i+"主推班型"+j);
                classSize.setNumber(j+1);
                classSizes.add(classSize);
                ClassSize classSize1=new ClassSize();
                classSize1.setDescription(i+"主推班型"+j);
                classSize1.setNumber(j+1);
                classSizes1.add(classSize1);
                ClassSize classSize2=new ClassSize();
                classSize2.setDescription(i+"主推班型"+j);
                classSize2.setNumber(j+1);
                classSizes2.add(classSize2);
                TriggerOpen triggerOpen=new TriggerOpen();
                triggerOpen.setNumber(j+1);
                triggerOpen.setTriggerOpen(i+"触发式开场"+j);
                triggerOpens.add(triggerOpen);
                SolveFirstProblem solveFirstProblem=new SolveFirstProblem();
                solveFirstProblem.setNumber(j+1);
                solveFirstProblem.setSolve(i+"解决首咨遗留问题"+j);
                solveFirstProblems.add(solveFirstProblem);
                AssistTool assistTool=new AssistTool();
                assistTool.setNumber(j+1);
                assistTool.setTool(i+"辅助工具"+j);
                assistTools.add(assistTool);
            }

            firstStrategyPhase.setSecondProjects(secondProjects);
            firstStrategyPhase.setNeeds(needs);
            firstStrategyPhase.setMainClassSizes(classSizes2);
            firstStrategyPhase.setKillStrategies(killStrategies2);
            secStrategyPhase.setNeeds(needs1);
            secStrategyPhase.setSolveFirstProblems(solveFirstProblems);
            secStrategyPhase.setKillStrategies(killStrategies);
            secStrategyPhase.setMainClassSizes(classSizes1);
            finalStrategyPhase.setTriggerOpens(triggerOpens);
            finalStrategyPhase.setMainClassSizes(classSizes);
            finalStrategyPhase.setKillStrategies(killStrategies3);
            finalStrategyPhase.setAssistTools(assistTools);
            strategyPhases.add(firstStrategyPhase);
            strategyPhases.add(finalStrategyPhase);
            strategyPhases.add(secStrategyPhase);
            UserInfo userInfo=new UserInfo();
            userInfo.setName("1234");
            userInfo.setUsername("0000");
            Record record = new Record();
            record.setDate(new Date());
            record.setChance(chance1);
            record.getViolationInfos().add(violationInfo);
            record.setIsViolation(i%2);
            record.setItemType(i%3);
            record.setTimeLength(i);
            record.setRecordType(i%4);
            record.setRecType(i%3);
            record.setUrl("http://quanquanniubi.top/music/catchmybreath.m4a");
            Record record1 = new Record();
            record1.setDate(new Date());
            record1.setChance(chance1);
            record1.getViolationInfos().add(violationInfo);
            record1.setIsViolation((i+1)%2);
            record1.setItemType((i+1)%3);
            record1.setTimeLength(i+1);
            record1.setRecordType((i+1)%4);
            record1.setRecType((i+1)%3);
            record1.setUrl("http://quanquanniubi.top/music/catchmybreath.m4a");
            strategy.setStrategyLogs(strategyLogs);
            strategyRepository.saveAndFlush(strategy);
            record.setStrategyId(strategy.getId());
            record1.setStrategyId(strategy.getId());
            record.setUserInfo(userInfo);
            record1.setUserInfo(userInfo);
            chance1.getRecords().add(record);
            chance1.getRecords().add(record1);
            chanceRepository.saveAndFlush(chance1);
        }
    }
    @Test
    public void test1() {

        Chance chance = new Chance();
        chance.setStuName("张三");
        chance.setPhoneNum("13912345678");
        chance.setSex(0);
        chance.setIsApply(0);
        chance.setSignDate(new Date());
        chance.setOrderParentId("19343");
        chance.setOrderSonId("1232009213");
        chance.setMajorTypeOneName("自考");
        chance.setMajorTypeTwoName("行专人本（国开）");
        chance.setFee("13999.00");

        ViolationInfo violationInfo = new ViolationInfo();
        violationInfo.setIsViolation(0);
//        violationInfo.setIsSystem(1);
        violationInfo.setBadTime(90);
        //责任人+++++++++++++++
        violationInfo.setViolationTypeOneId(1L);
        violationInfo.setViolationTypeOneName("违规违规一");
        violationInfo.setViolationTypeTwoId(2L);
        violationInfo.setViolationTypeTwoName("违规违规二");
        violationInfo.setViolationTypeThreeId(3L);
        violationInfo.setViolationTypeThreeName("违规违规三");

        Record record = new Record();
        record.setDate(new Date());
        record.setChance(chance);
        //首咨 recType +++++++++++++++++++++
        //策略id ++++++++++++++++++++++++
        record.getViolationInfos().add(violationInfo);
        record.setIsViolation(0);
        record.setItemType(1);
        record.setTimeLength(90);
        record.setUrl("http://quanquanniubi.top/music/catchmybreath.m4a");
    }

    @Test
    public void test2() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        }else {
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int offset = 1 - dayOfWeek;
            calendar.add(Calendar.DATE, offset + 7);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime();
        String str = DateUtil.dateToString(date);

        System.out.println(str);
    }
}
