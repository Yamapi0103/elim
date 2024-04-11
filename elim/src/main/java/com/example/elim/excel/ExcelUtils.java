package com.example.elim.excel;

import com.example.elim.model.Business;
import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExcelUtils {

    public void exportDate(List<Business> dateList, String templateName, int reportType){

        String templatePath = "excelTemplates/" + templateName + ".xlsx";
        Context context = contextPutVar(dateList, reportType);
        File file = getFile(templateName);

        try(InputStream is = getClass().getClassLoader()
                .getResource(templatePath).openStream()) {
            try (OutputStream os = new FileOutputStream(file)) {
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
//        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");  // 12小時制
//        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");  // 24小時制
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

    private Context contextPutVar(List<Business> dateList, int reportType){
        Context context = new Context();
        context.putVar("businessList", dateList);
        if(reportType == 1){
            // do nothing
        } else if (reportType ==2){
            Business business = dateList.get(0);
            context.putVar("carNo", business.getCarNo());
            context.putVar("sDate", business.getDate());
        }
        return context;
    }
}
