package com.feo.application.impl;

import com.feo.application.ChanceSevice;
import com.feo.common.DateUtil;
import com.feo.common.ExcelPOI;
import com.feo.domain.model.enums.record.RecTypeEnums;
import com.feo.domain.model.record.Record;
import com.feo.domain.model.record.UnMatchRecord;
import com.feo.domain.model.record.ViolationInfo;
import com.feo.domain.model.strategy.Strategy;
import com.feo.domain.model.user.UserInfo;
import com.feo.domain.repository.record.ChanceRepository;
import com.feo.domain.repository.record.RecordRepository;
import com.feo.application.strategy.StrategyService;
import com.feo.domain.model.record.Chance;
import com.feo.domain.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ChanceSeviceImpl implements ChanceSevice {

    @Autowired
    ChanceRepository chanceRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    StrategyService strategyService;
    @Autowired
    RecordRepository recordRepository;

    /**
     * 老天网录音对接
     *
     * @throws ParseException
     */
    @Override
    public void speechChanceDataPersistence() throws ParseException {
        String filePath = "D:\\ceshi\\qc_speech_20171203.xlsx";
        List<List<String>> lists = ExcelPOI.getValues(filePath, 1, 19, 2);
        a:for (int i = 0; i < lists.size(); i++) {
            List<String> stringList = lists.get(i);
            Chance chance = chanceRepository.findByPhoneNum(stringList.get(3).trim());
            if (chance == null) {
                chance = new Chance();
            }
            chance.setPhoneNum(stringList.get(3).trim());
            chance.setOrderParentId(stringList.get(0).trim());
            chance.setOrderSonId(stringList.get(1).trim());
            chance.setStuName(stringList.get(2).trim());
            chance.setMajorTypeOneName(stringList.get(4).trim());
            chance.setMajorTypeTwoName(stringList.get(5).trim());
            chance.setSignDate(DateUtil.stringToDate(stringList.get(6).trim()));
            chance.setFee(stringList.get(7).trim());
            Record record =null;
            UnMatchRecord unMatchRecord=null;
            UserInfo userInfo = userInfoRepository.findByName(stringList.get(9).trim());
            if (userInfo==null){
                unMatchRecord=new UnMatchRecord();
                unMatchRecord.setBranchName(stringList.get(11).trim());
                unMatchRecord.setCounselorName(stringList.get(9).trim());
                unMatchRecord.setGroupName(stringList.get(10).trim());
                Set<UnMatchRecord> unMatchRecords = chance.getUnMatchRecords();
                for (UnMatchRecord unMatchRecord1:unMatchRecords){
                    if (unMatchRecord1.getUrl().equals(stringList.get(12).trim())){
                        continue a;
                    }
                }
                unMatchRecord.setUrl(stringList.get(12).trim());
                unMatchRecord.setDate(DateUtil.stringToDate(stringList.get(8).trim()));
                setRecordDefault(unMatchRecord);
                setVio(stringList, unMatchRecord);
                chance.getUnMatchRecords().add(unMatchRecord);
            }else {
                String url=stringList.get(12).trim();
                Set<Record> records = chance.getRecords();
                for (Record record1:records){
                    if (record1.getUrl().equals(url)){
                        continue a;
                    }
                }
                record=new Record();
                record.setUserInfo(userInfo);
                chance.getRecords().add(record);
                decideRecType(chance, record, userInfo);
                record = new Record();
                record.setUrl(stringList.get(12).trim());
                record.setDate(DateUtil.stringToDate(stringList.get(8).trim()));
                setRecordDefault(record);
                setVio(stringList, record);
            }
            chanceRepository.saveAndFlush(chance);
        }
    }

    private void  setVio(List<String> stringList,Record t) {
        for (int j = 13; j < 18; j++) {
            String str = stringList.get(j).trim();
            if (str != null && !str.equals("")) {
                String[] strs = str.split(" - ");
                addRecordVio(strs[0],strs[1]+" - "+strs[2],t);
            }
        }
    }

    private boolean onNoneUserInfo(List<String> stringList, Chance chance) {
        Record record;
        UnMatchRecord record1=new UnMatchRecord();
        record1.setBranchName(stringList.get(11).trim());
        record1.setCounselorName(stringList.get(9).trim());
        record1.setGroupName(stringList.get(10).trim());
        Set<UnMatchRecord> unMatchRecords = chance.getUnMatchRecords();
        for (UnMatchRecord unMatchRecord:unMatchRecords){
            if (unMatchRecord.getUrl().equals(stringList.get(12).trim())){
                return true;
            }
        }
        record=record1;
        chance.getUnMatchRecords().add(record1);
        return false;
    }

    /**
     * 新天网录音对接
     * @throws ParseException
     */
    public void newSpeechChanceDataPersistence() throws ParseException {
        String filePath = "D:\\ceshi\\qc_newdragnet_20171203.xlsx";
        List<List<String>> lists = ExcelPOI.getValues(filePath, 1, 16, 3);
        a:for (int i = 0; i < lists.size(); i++) {
            List<String> stringList = lists.get(i);
            Chance chance = chanceRepository.findByPhoneNum(stringList.get(3).trim());
            if (chance == null) {
                chance = new Chance();
            }
            chance.setPhoneNum(stringList.get(3).trim());
            chance.setOrderSonId(stringList.get(1).trim());
            chance.setStuName(stringList.get(2).trim());
            chance.setMajorTypeOneName(stringList.get(4).trim());
            chance.setMajorTypeTwoName(stringList.get(5).trim());
            chance.setSignDate(DateUtil.stringToDate(stringList.get(0).trim()));
            chance.setFee(stringList.get(6).trim());
            Record record =null;
            UnMatchRecord unMatchRecord=null;
            UserInfo userInfo = userInfoRepository.findByName(stringList.get(8).trim());
            if (userInfo==null){
                unMatchRecord=new UnMatchRecord();
                unMatchRecord.setBranchName(stringList.get(10).trim());
                unMatchRecord.setCounselorName(stringList.get(8).trim());
                unMatchRecord.setGroupName(stringList.get(9).trim());
                Set<UnMatchRecord> unMatchRecords = chance.getUnMatchRecords();
                for (UnMatchRecord unMatchRecord1:unMatchRecords){
                    if (unMatchRecord1.getUrl().equals(stringList.get(12).trim())){
                        continue a;
                    }
                }
                unMatchRecord.setUrl(stringList.get(12).trim());
                unMatchRecord.setDate(DateUtil.stringToDate(stringList.get(11).trim()));
                setRecordDefault(unMatchRecord);
                setVio(stringList, unMatchRecord);
                chance.getUnMatchRecords().add(unMatchRecord);
            }else {
                String url=stringList.get(12).trim();
                Set<Record> records = chance.getRecords();
                for (Record record1:records){
                    if (record1.getUrl().equals(url)){
                        continue a;
                    }
                }
                record=new Record();
                record.setUserInfo(userInfo);
                chance.getRecords().add(record);
                decideRecType(chance, record, userInfo);
                record = new Record();
                record.setUrl(stringList.get(12).trim());
                record.setDate(DateUtil.stringToDate(stringList.get(11).trim()));
                setRecordDefault(record);
                addRecordVio(stringList.get(13).trim(), stringList.get(14).trim(),record);
            }
            chanceRepository.saveAndFlush(chance);
        }
    }

    private void setRecordDefault(Record record) {
        record.setSelectStatus(0);
        record.setStatus(0);
        record.setIsViolation(0);
        record.setIsGood(0);
    }

    /**
     * 未报名全量录音对接
     * @throws ParseException
     */
    @Transactional
    public void allChanceDataPersistence()throws ParseException{
        String filePath = "D:\\ceshi\\qc_all_without_order_20171203.xlsx";
        List<List<String>> lists = ExcelPOI.getValues(filePath, 1, 10, 3);
        a:for (int i = 0; i < lists.size(); i++) {
            List<String> stringList = lists.get(i);
            Chance chance = chanceRepository.findByPhoneNum(stringList.get(0).trim());
            if (chance == null) {
                chance = new Chance();
            }
            Record record =null;
            UnMatchRecord unMatchRecord=null;
            UserInfo userInfo = userInfoRepository.findByName(stringList.get(4).trim());
            if (userInfo==null){
                unMatchRecord=new UnMatchRecord();
                unMatchRecord.setBranchName(stringList.get(6).trim());
                unMatchRecord.setCounselorName(stringList.get(4).trim());
                unMatchRecord.setGroupName(stringList.get(5).trim());
                Set<UnMatchRecord> unMatchRecords = chance.getUnMatchRecords();
                for (UnMatchRecord unMatchRecord1:unMatchRecords){
                    if (unMatchRecord1.getUrl().equals(stringList.get(2).trim())){
                        continue a;
                    }
                }
                unMatchRecord.setUrl(stringList.get(2).trim());
                unMatchRecord.setDate(DateUtil.stringToDate(stringList.get(1).trim()));
                setRecordDefault(unMatchRecord);
                setVio(stringList, unMatchRecord);
                chance.getUnMatchRecords().add(unMatchRecord);
            }else {
                String url=stringList.get(2).trim();
                if (onHasUserInfo(chance, userInfo, url)) continue a;
            }
            record = new Record();
            record.setUrl(stringList.get(2).trim());
            record.setDate(DateUtil.stringToDate(stringList.get(1).trim()));
            setRecordDefault(record);
            record.setTimeLength(getSeconds(stringList.get(3).trim()));
            addRecordVio(stringList.get(7).trim(), stringList.get(8).trim(),record);
            chanceRepository.saveAndFlush(chance);
        }
    }

    private boolean onHasUserInfo(Chance chance, UserInfo userInfo, String url) {
        Record record;Set<Record> records = chance.getRecords();
        for (Record record1:records){
            if (record1.getUrl().equals(url)){
                return true;
            }
        }
        record=new Record();
        record.setUserInfo(userInfo);
        chance.getRecords().add(record);
        decideRecType(chance, record, userInfo);
        return false;
    }

    private void decideRecType(Chance chance, Record record, UserInfo userInfo) {
        Set<Record> records = chance.getRecords();
        if (records.size() > 0) {
            record.setStrategyId(records.iterator().next().getStrategyId());
            Strategy strategy = strategyService.findById(record.getStrategyId());
            Date recordDate = record.getDate();
            Date strategyStartTime = strategy.getStartTime();
            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.DATE, 7);// num为增加的天数，可以改变的
            strategyStartTime = ca.getTime();
            if (recordDate.after(strategyStartTime)) {
                record.setRecType(RecTypeEnums.FINAL_ASK.getCode());
            } else {
                record.setRecType(RecTypeEnums.SECOND_ASK.getCode());
            }
        } else {
            Strategy strategy = strategyService.getNewStrategy(userInfo);
            record.setStrategyId(strategy.getId());
            record.setRecType(RecTypeEnums.FIRST_ASK.getCode());
        }
        records.add(record);
    }

    private void addRecordVio(String str,String str0, Record record) {
        if (str != null && !str.equals("")) {
//          Time: 2:44.670 to 2:58.139 - 虚构政策:政策暂没改革 - 对所以就是说如果你的计划是打算在呃广呃惠州这边长期发展的话呢那这边赶的老政策所以是一个时间报考胃胀的话国家政策暂没改革之前女士能在这边进行去考试。
            String[] strs1 = str.split(" ");
            ViolationInfo violationInfo = new ViolationInfo();
            violationInfo.setBadTime(getSeconds(strs1[1]));
            violationInfo.setEndTime(getSeconds(strs1[3]));
            String[] strs = str0.split("-");
            String[] str2 = strs[0].split(":");
            violationInfo.setViolationTypeOneName(str2[0]);
            violationInfo.setViolationTypeTwoName(str2[1]);
            violationInfo.setContent(strs[1].trim());
            record.getViolationInfos().add(violationInfo);
        }
    }


    private Integer getSeconds(String str) {
        String[] strs = str.split(":");
        Integer sec;
        if (strs.length == 3) {
            String str1=strs[2];
            BigDecimal bigDecimal = new BigDecimal(str1);
            sec = Integer.parseInt(strs[0]) * 60 * 60 + Integer.parseInt(strs[1]) * 60 + bigDecimal.intValue();
        } else if ((strs.length == 2)) {
            String str1 = strs[1];
            BigDecimal bigDecimal = new BigDecimal(str1);
            sec = Integer.parseInt(strs[0]) * 60 + bigDecimal.intValue();
        } else if ((strs.length == 1)) {
            sec = Integer.parseInt(strs[0].split(".")[0]);
        } else {
            return 0;
        }
        return sec;
    }
}
