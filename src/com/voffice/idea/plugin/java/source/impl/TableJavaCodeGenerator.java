package com.voffice.idea.plugin.java.source.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.java.source.MaqvJavaCodeGenerator;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;

/**
 * 生产Table类的代码
 */
public class TableJavaCodeGenerator extends MaqvJavaCodeGenerator {


    public TableJavaCodeGenerator(TableInfo tableInfo){
        super(tableInfo);
        addMaqvMysqlImport();
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getTablePackage();
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classCommentCode() {
        return comment(" DBTable for "+getClassName(),"@author Ben.Ma <xiaobenma020@gmail.com>");
    }

    @Override
    public String classIdentification() {
        return PUBLIC+ INTERFACE+getThisClassName();
    }

    public  String getThisClassName(){
        return  getClassName()+"Table";
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(" DBTable TABLE_NAME = new DBTable(\"")
                .append(tableName)
                .append("\", \"")
                .append(getAlias()).append("\")"+SEMICOLON+NEWLINE);
        stringBuffer.append(NEWLINE);
        for (ColumnInfo columnInfo : columnInfos) {
            stringBuffer.append("DBColumn ").append(columnInfo.getColumnName())
                    .append("= new DBColumn(TABLE_NAME, \"").append(columnInfo.getColumnName()).append("\");"+NEWLINE);
        }
        return stringBuffer.toString();
    }
}
