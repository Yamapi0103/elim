package com.example.elim.excel;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExcelUtils {
    public void exportDate(ExcelFunParams excelParams){

        System.out.println("匯出一份EXCEL");

        String templateName = excelParams.getTemplateName();
        String templatePath = "excelTemplates/" + templateName + ".xlsx";
        //Context context = contextPutVar(excelParams.getContextVar());
        File file = getFile(templateName);

        try(InputStream is = getClass().getClassLoader()
                .getResource(templatePath).openStream()) {
            try (OutputStream os = new FileOutputStream(file)) {
                Context context = new Context();
                excelParams.getContextVar().forEach((key, value) -> {
                    context.putVar(key, value);
                });
                JxlsHelper.getInstance().processTemplate(is, os, context);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFile(String fileName){
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmss");
        String getNowStr = sdf.format(now);
        String year = getNowStr.substring(0,4);
        String month = getNowStr.substring(4,6);
        String day = getNowStr.substring(6,8);
        String monthAndDay = getNowStr.substring(4,8);
        String timeStr = getNowStr.substring(8,14);
        String folderPath = "D:/export/" + year + "/" + month + "/" + day;

        File folder = new File(folderPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File file = new File(folderPath + "/" + fileName
                + "_" + timeStr +".xlsx");
        return file;
    }

    private Context contextPutVar(Map<String, Object> varMap){
        Context context = new Context();
        varMap.forEach((key, value) -> {
            context.putVar(key, value);
        });
        return context;
    }
}
