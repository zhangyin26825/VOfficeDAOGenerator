package com.voffice.idea.plugin.file.field.impl;

import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.JdbcTypeTranslater;
import org.apache.commons.lang.StringUtils;

public class EntityFieldGenerator implements FieldGenerator {


    @Override
    public boolean isEnum(ColumnInfo columnInfo) {
        return StringUtils.endsWith(columnInfo.getColumnName(), "enum");
    }

    @Override
    public String generatorFieldDesc(ColumnInfo columnInfo) {
        String comment = comment(columnInfo.getColumnComment(), columnInfo.getColumnType());
        StringBuffer sb=new StringBuffer(comment);
        if(StringUtils.equals("PRI", columnInfo.getColumnKey())){
            sb.append("@Id(");
        }else{
            sb.append("@Column(");
        }
        sb.append("name=\""+columnInfo.getColumnName()+"\"");
        if(StringUtils.equals("YES", columnInfo.getIsNullable())){
            sb.append(", nullable = true");
        }
        if(StringUtils.equals("auto_increment", columnInfo.getExtra())){
            sb.append(", autoIncrease = true");
        }
        sb.append(")"+NEWLINE);
        sb.append(PRIVATE);
        if(isEnum(columnInfo)){
            sb.append(StringUtils.capitalize(getFieldName(columnInfo.getColumnName())))
                    .append(" ")
                    .append(getFieldName(columnInfo.getColumnName())).append(SEMICOLON+NEWLINE);
        }else{
            sb.append(JdbcTypeTranslater.getJavaClassName(columnInfo.getJdbcType()))
                    .append(" ")
                    .append(getFieldName(columnInfo.getColumnName())).append(SEMICOLON+NEWLINE);
        }
        return sb.toString();
    }
}
