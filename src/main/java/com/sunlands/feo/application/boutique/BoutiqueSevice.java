package com.sunlands.feo.application.boutique;

import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.model.boutique.BoutiqueRecord;
import com.sunlands.feo.domain.model.boutique.BoutiqueStrategy;
import com.sunlands.feo.port.rest.dto.boutique.BoutiqueSelectCondition;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface BoutiqueSevice {

    /**
     * 将策略添加为精品
     * @param strategyId
     * @return
     */
    boolean setBoutiqueStrategy(Long strategyId);

    /**
     * 取消精品
     * @param strategyId
     * @return
     */
    boolean deleteBoutiqueStrategy(Long strategyId);

    /**
     * 根据录音id添加精品库
     * @param recordId
     * @return
     */
    boolean setBoutiqueRecord(Long recordId);

    /**
     * 取消精品
     * @param recordId
     * @return
     */
    boolean deleteBoutiqueRecord(Long recordId);

    /**
     * 根据查询条件获取优秀策略列表
     * @param boutiqueSelectCondition
     * @return
     */
    Map<String,Object> queryBoutiqueStrategy(BoutiqueSelectCondition boutiqueSelectCondition) throws ParseException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据查询条件获取优秀录音列表
     * @param boutiqueSelectCondition
     * @return
     */
    public Map<String,Object> queryBoutiqueRecord(BoutiqueSelectCondition boutiqueSelectCondition) throws ParseException, InvocationTargetException, IllegalAccessException;

    Boolean decideBoutiqueRecord(Long recordId);

    Boolean decideBoutiqueStrategy(Long strategyId);

    /**
     * 获取录音类型列表
     * @return
     */
    List<Map<String,Object>> getItems();

    /**
     * 批量下载优秀录音
     * @param records
     * @param response
     * @return
     */
    ServerStatus batchDownloadRecords(Long[] records, HttpServletResponse response, HttpServletRequest request) throws IOException;
}
