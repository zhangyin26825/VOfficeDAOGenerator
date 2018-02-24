package com.voffice.idea.plugin.java.source.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.java.source.MaqvJavaCodeGenerator;
import com.voffice.idea.plugin.jdbc.TableInfo;

public class DaoImplJavaCodeGenerator extends MaqvJavaCodeGenerator {


    public DaoImplJavaCodeGenerator(TableInfo tableInfo){
        super(tableInfo);
        addDaoImport();
        addSpringImport();
    }

    @Override
    public String getThisClassName() {
        return getClassName()+"DaoImpl";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getDaoImplPackage();
    }

    @Override
    public String classCommentCode() {
        return comment(" Dao for "+tableName,"","@author Ben.Ma <xiaobenma020@gmail.com>");
    }

    @Override
    public String classAnnotation() {
        return "@Repository";
    }

    @Override
    public String classIdentification() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(PUBLIC+CLASS+getThisClassName()+EXTENDS);
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
