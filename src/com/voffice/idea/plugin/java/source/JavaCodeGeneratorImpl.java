package com.voffice.idea.plugin.java.source;

import java.util.HashSet;
import java.util.Set;

/**
 *  这里优化导入 和  包的方法的简化
 */
public abstract  class JavaCodeGeneratorImpl extends AbstractJavaCodeGenerator {



    private Set<String> importLists;

    public abstract String  getPackageName();

    protected  JavaCodeGeneratorImpl(){
        importLists=new HashSet<>();
    }
    protected  void  addImport(String importDesc){
        importLists.add(importDesc);
    }

    @Override
    public String packageCode() {
        return PACKAGE+getPackageName()+SEMICOLON;
    }

    @Override
    public String importCode() {
        StringBuffer stringBuffer=new StringBuffer();
        for (String importDesc : importLists) {
            stringBuffer.append(IMPORT+importDesc+SEMICOLON+NEWLINE);
        }
        return stringBuffer.toString();
    }


}
