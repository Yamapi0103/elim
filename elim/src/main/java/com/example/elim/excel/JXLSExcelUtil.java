package com.example.elim.excel;

import com.example.elim.dto.ExcelDataTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JXLSExcelUtil {
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

   public static List<ExcelDataTest> read() throws Exception {
        List emptyList = new ArrayList<ExcelDataTest>();
        String xmlConfig = "excelReader/BusinessDataReader.xml";
        XLSReader mainReader = null;
        try(InputStream inputXML = new BufferedInputStream(JXLSExcelUtil.class.getClassLoader().getResourceAsStream(xmlConfig))){
            if (inputXML == null) {
                throw new Exception("讀取配置文件失敗：" + xmlConfig);
            }

            mainReader = ReaderBuilder.buildFromXML(inputXML);

            try(InputStream inputXLS = new BufferedInputStream(new FileInputStream("D:/test456.xlsx"))){
                if (inputXLS == null) {
                    throw new Exception("讀取excel失敗：" + "D:/test456.xlsx");
                }
                List<ExcelDataTest> dataList = new ArrayList<>();
                Map<String, Object> beans = new HashMap<>();
                beans.put("dataList", dataList);
                XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
                for (ExcelDataTest data : dataList) {
                    System.out.println("讀到了 === " + data.toString());
                }
                return readStatus.isStatusOK() ? dataList : emptyList;

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
      }

    /**
     * 讀取Excle數據到bean
     * @param readConfigXmlRelativePathAndName 讀取配置文件
     * @param file 輸入的excle文件
     * @param beans 要封裝數據的bean
     * @return
     * @throws Exception
     */
    public static boolean readExcelData(String readConfigXmlRelativePathAndName, File file, Map<String, Object> beans) throws Exception {
        if (file == null) {
            throw new Exception("Excel文件爲空");
        }
        InputStream inputExcelStream = new FileInputStream(file);
        return readExcelData(readConfigXmlRelativePathAndName, inputExcelStream, beans);
    }

    /**
     * 讀取Excle數據到bean
     * @param readConfigXmlRelativePathAndName 讀取配置文件
     * @param inputExcelStream 輸入的excle inputSream
     * @param beans 要封裝數據的bean
     * @return
     * @throws Exception
     */
    public static boolean readExcelData(String readConfigXmlRelativePathAndName, InputStream inputExcelStream, Map<String, Object> beans) throws Exception {
        InputStream in = JXLSExcelUtil.class.getClassLoader().getResourceAsStream(readConfigXmlRelativePathAndName);
        if (in == null) {
            throw new Exception("配置文件未找到：" + readConfigXmlRelativePathAndName);
        }
        InputStream inputXML = new BufferedInputStream(in);
        XLSReader reader = ReaderBuilder.buildFromXML(inputXML);
        XLSReadStatus readStatus = reader.read(inputExcelStream, beans);
        return readStatus.isStatusOK();
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
