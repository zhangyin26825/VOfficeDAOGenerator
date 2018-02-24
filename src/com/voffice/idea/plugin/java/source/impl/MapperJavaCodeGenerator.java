package com.voffice.idea.plugin.java.source.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.java.source.MaqvJavaCodeGenerator;
import com.voffice.idea.plugin.jdbc.TableInfo;

public class MapperJavaCodeGenerator extends MaqvJavaCodeGenerator {


    public MapperJavaCodeGenerator(TableInfo tableInfo){
        super(tableInfo);
        addMapperImport();
    }

    @Override
    public String getThisClassName() {
        return getClassName()+"Mapper";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getMapperPackage();
    }

    @Override
    public String classCommentCode() {
        return comment(" Mapper for "+tableName,"","@author Ben.Ma <xiaobenma020@gmail.com>");
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(PUBLIC+INTERFACE+getThisClassName()+EXTENDS);
        stringBuffer.append("Mapper");
        stringBuffer.append("<").append(getClassName()).append(">");
        return stringBuffer.toString();
    }

    @Override
    public String classbody() {
        return "";
    }
}
