package com.voffice.idea.plugin.java.source.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.java.source.MaqvJavaCodeGenerator;
import com.voffice.idea.plugin.java.source.field.EnumFieldFilter;
import com.voffice.idea.plugin.java.source.field.FieldGenerator;
import com.voffice.idea.plugin.java.source.field.impl.EntityFieldGenerator;
import com.voffice.idea.plugin.java.source.field.impl.EnumFilter;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体类的java代码生成
 */
public class PoJavaCodeGenerator extends MaqvJavaCodeGenerator {

    FieldGenerator fieldGenerator;

    EnumFieldFilter enumFieldFilter;


    public PoJavaCodeGenerator(TableInfo tableInfo){
        super(tableInfo);
        addLombokImport();
        addMaqvMysqlImport();
        fieldGenerator=new EntityFieldGenerator();
        enumFieldFilter=new EnumFilter();
    }

    @Override
    public String  getPackageName(){
        return DirectoryManager.getPoPackage();
    }

    @Override
    public String  classCommentCode(){
        StringBuffer stringBuffer=new StringBuffer("Entity for "+tableName);
        if(StringUtils.isNotEmpty(tableComment)){
            stringBuffer.append("("+tableComment+")");
        }
       return comment(stringBuffer.toString(),"");
    }

    @Override
    public String  classAnnotation(){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("@Setter"+NEWLINE);
        stringBuffer.append("@Getter"+NEWLINE);
        stringBuffer.append("@Table(name = \""+getClassName()+"\", alias = \""+getAlias()+"\")"+NEWLINE);
        return  stringBuffer.toString();
    }

    @Override
    public String classIdentification() {
        return PUBLIC+CLASS+getClassName();
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer=new StringBuffer();
        List<ColumnInfo> collect = columnInfos.stream().filter(columnInfo -> !enumFieldFilter.isEnum(columnInfo)).collect(Collectors.toList());
        for (ColumnInfo columnInfo : collect) {
            stringBuffer.append(fieldGenerator.generatorFieldDesc(columnInfo));
        }
        return stringBuffer.toString();
    }
}
