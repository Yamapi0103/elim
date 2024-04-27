package com.example.elim.controller;

import com.example.elim.dto.ExcelDataTest;
import com.example.elim.excel.JXLSExcelUtil;
import com.example.elim.util.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelController {

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
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public boolean importExcel2() {
        try {
            boolean isOK = JXLSExcelUtil.read();
            return isOK;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
