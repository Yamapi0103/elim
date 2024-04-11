package com.example.elim.excel;

import com.example.elim.model.Business;
import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtils {

    public void exportDate(List<?> dateList, String templateName){
//        Business business = (Business) dateList.get(0);
        String templatePath = "excelTemplates/" + templateName + ".xlsx";

        try(InputStream is = getClass().getClassLoader()
                .getResource(templatePath).openStream()) {
            try (OutputStream os = new FileOutputStream("D:\\export\\BusinessResult.xlsx")) {
                Context context = new Context();
                context.putVar("businessList", dateList);
//                context.putVar("carNo", business.getCarNo());
//                context.putVar("sDate", business.getDate());
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
}
