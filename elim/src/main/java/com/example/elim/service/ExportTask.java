package com.example.elim.service;

import com.example.elim.excel.ExcelFunParams;
import com.example.elim.excel.ExcelUtils;
import org.springframework.stereotype.Service;

@Service
public class ExportTask implements Runnable{

    private ExcelFunParams params;

    public ExportTask(ExcelFunParams excelParams){
        this.params = excelParams;
    }
    @Override
    public void run() {
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.exportDate(params);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
