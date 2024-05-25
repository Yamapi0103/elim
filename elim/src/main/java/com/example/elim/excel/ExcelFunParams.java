package com.example.elim.excel;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExcelFunParams {

    /**
     * 要讀取的excel模板名稱
     */
    private String templateName;

    /**
     * 匯出目的地的資料夾名稱
     */
    private String folderName;

    /**
     * 匯出的檔案名稱
     */
    private String fileName;

    /**
     *設置excel與data的對應關係
     */
    private Map<String, Object> contextVar;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getContextVar() {
        return contextVar;
    }

    public void setContextVar(Map<String, Object> contextVar) {
        this.contextVar = contextVar;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
