package com.example.elim.excel;

import java.util.List;
import java.util.Map;

public class ExcelFunParams {

    private String templateName;

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
}
