package com.feo.port.rest.controller.boutique;

import com.feo.application.boutique.BoutiqueSevice;
import com.feo.application.report.ExcelExportService;
import com.feo.port.rest.dto.boutique.BoutiqueSelectCondition;
import com.feo.domain.exception.ServerStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RequestMapping("/boutiques")
@RestController
public class BoutiqueController {

    @Autowired
    private BoutiqueSevice boutiqueSevice;
    @Autowired
    private ExcelExportService excelExportService;

    @ApiOperation(value = "将策略设为或取消精品策略")
    @PostMapping("/strategies/{strategyId}/status")
    public boolean handleBoutiqueStrategy(@PathVariable("strategyId") Long strategyId, @RequestBody Integer type) {
        boolean flag=false;
        if (type == 1) {
           flag = boutiqueSevice.setBoutiqueStrategy(strategyId);
        } else if (type == 0) {
            flag = boutiqueSevice.deleteBoutiqueStrategy(strategyId);
        }
        return flag;
    }

    @ApiOperation(value = "将录音设为或取消精品录音")
    @PostMapping("/records/{recordId}/status")
    public boolean handleBoutiqueRecordStrategy(@PathVariable Long recordId, @RequestBody Integer type) {
        boolean flag=false;
        if (type == 1) {
            flag = boutiqueSevice.setBoutiqueRecord(recordId);
        } else if (type == 0) {
            flag = boutiqueSevice.deleteBoutiqueRecord(recordId);
        }
        return flag;
    }


    @ApiOperation(value = "获取优秀策略列表")
    @GetMapping(value = "/strategies")
    public Map<String,Object> queryBoutiqueStrategy(BoutiqueSelectCondition boutiqueSelectCondition) throws ParseException, InvocationTargetException, IllegalAccessException {
        return  boutiqueSevice.queryBoutiqueStrategy(boutiqueSelectCondition);
    }

    @ApiOperation(value = "获取优秀录音列表")
    @GetMapping("/records")
    public Map<String,Object> queryBoutiqueRecord(BoutiqueSelectCondition boutiqueSelectCondition) throws ParseException, InvocationTargetException, IllegalAccessException {
       return boutiqueSevice.queryBoutiqueRecord(boutiqueSelectCondition);
    }

    @ApiOperation(value = "判断策略是否被当前用户设为精品")
    @GetMapping("/strategies/{strategyId}/status")
    public boolean decideBoutiqueStrategy(@PathVariable Long strategyId){
        Boolean flag=boutiqueSevice.decideBoutiqueStrategy(strategyId);
        return flag;
    }

    @ApiOperation(value = "判断录音是否被当前用户设为精品")
    @GetMapping("/records/{recordId}/status")
    public boolean decideBoutiqueRecord(@PathVariable Long recordId){
        Boolean flag=boutiqueSevice.decideBoutiqueRecord(recordId);
        return flag;
    }
    @GetMapping("/items")
    public List<Map<String, Object>> getItems(){
        List<Map<String, Object>> items = boutiqueSevice.getItems();
        return items;
    }

    @GetMapping("/batchDownloadRecords")
    public ServerStatus batchDownloadRecords(@RequestParam Long[] recordIds, HttpServletRequest request,HttpServletResponse response) throws Exception{
//        excelExportService.record(recordIds,request,response,response.getOutputStream());
//        return ServerStatus.SUCCESS;
        return boutiqueSevice.batchDownloadRecords(recordIds,response,request);
    }
}
