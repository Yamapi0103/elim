package com.example.elim.controller;

import com.example.elim.dto.BusinessFilter;
import com.example.elim.dto.ExcelDataTest;
import com.example.elim.excel.JXLSExcelUtil;
import com.example.elim.service.ExcelService;
import com.example.elim.util.FileUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/export")
    public String export(@Valid @RequestBody BusinessFilter filter){
        return excelService.export(filter);
    }

    /**
     * 導入Excel數據
     * @param file
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public void importExcel(MultipartFile file) {
        File f = null;
        try {
            // MultipartFile 轉  file
            f = FileUtil.multipartFileToFile(file);
            List<ExcelDataTest> list = new ArrayList<>();
            Map<String, Object> beans = new HashMap<>();
            beans.put("dataList", list); // dataList 來自配置文件readExcelConfig.xml的items=”dataList”
            JXLSExcelUtil.readExcelData("excelReader/BusinessDataReader.xml", f , beans);
            for (ExcelDataTest data : list) {
                System.out.println("讀到了 === " + data.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                f.delete();
            }
        }
    }

    /**
     * 導入Excel數據
     * @param "file"
     */
    @RequestMapping(value = "/read/export", method = RequestMethod.POST)
    public String importExcel2() {
        try {
            List<ExcelDataTest> dataList = JXLSExcelUtil.read();
            if (CollectionUtils.isEmpty(dataList)) {
                return "讀取檔案失敗";
            } else {
                return excelService.exportOrdererMonthlyReports(dataList);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
