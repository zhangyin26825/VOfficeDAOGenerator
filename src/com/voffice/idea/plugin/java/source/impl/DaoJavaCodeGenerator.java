package com.voffice.idea.plugin.java.source.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.java.source.MaqvJavaCodeGenerator;
import com.voffice.idea.plugin.jdbc.TableInfo;

public class DaoJavaCodeGenerator extends MaqvJavaCodeGenerator {


    public DaoJavaCodeGenerator(TableInfo tableInfo){
        super(tableInfo);
        addDaoImport();
    }

    @Override
    public String getThisClassName() {
        return getClassName()+"Dao";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getDaoPackage();
    }

    @Override
    public String classCommentCode() {
        return comment(" Dao for "+tableName,"","@author Ben.Ma <xiaobenma020@gmail.com>");
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(PUBLIC+INTERFACE+getThisClassName()+EXTENDS);
        if(isMultiId()){
            stringBuffer.append("MultipleIdDao");
        }else{
            stringBuffer.append("SingleIdDao");
        }
        stringBuffer.append("<").append(getClassName()).append(">");
        return stringBuffer.toString();
    }

    @Override
    public String classbody() {
        return "";
    }
}
