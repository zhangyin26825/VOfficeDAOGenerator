package com.voffice.idea.plugin.file.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;
import com.voffice.idea.plugin.jdbc.TableInfo;
import org.apache.commons.lang.StringUtils;

public class EntityJavaFile extends MysqlJavaFileCreate {


    public EntityJavaFile(TableInfo tableInfo){
        super(tableInfo);
        addLombokImport();
        addMaqvMysqlImport();
    }

    @Override
    public String getClassName() {
        return getBaseEntityName();
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getPoPackage();
    }

    @Override
    public String classCommentCode() {
        StringBuffer stringBuffer=new StringBuffer("Entity for "+tableName);
        if(StringUtils.isNotEmpty(tableComment)){
            stringBuffer.append("("+tableComment+")");
        }
        return comment(stringBuffer.toString(),"");
    }

    @Override
    public String classAnnotation() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("@Setter"+NEWLINE);
        stringBuffer.append("@Getter"+NEWLINE);
        stringBuffer.append("@Table(name = \""+tableName+"\", alias = \""+getTableAlias()+"\")"+NEWLINE);
        return  stringBuffer.toString();
    }

    @Override
    public String classIdentification() {
         return PUBLIC+CLASS+getClassName();
    }

    @Override
    public String classbody() {
        return "";
    }
}
