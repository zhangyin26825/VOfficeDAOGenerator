package com.voffice.idea.plugin.java.source;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import org.apache.commons.lang.StringUtils;

public class MaqvEnumJavaCodeGenerator extends JavaCodeGeneratorImpl {

    private ColumnInfo columnInfo;

    public MaqvEnumJavaCodeGenerator(ColumnInfo columnInfo){
        this.columnInfo=columnInfo;
        addImport("com.maqv.mybatis.converter.CommonEnum");
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getTypePackage();
    }

    @Override
    public String classCommentCode() {
        return "";
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        return PUBLIC+ENUM+getEnumClassName()+"  implements "+" CommonEnum<"+getEnumClassName()+">";
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(SEMICOLON);
        stringBuffer.append(getEnumClassName()+"(int value) {\n" +
                "        this.value = value;\n" +
                "    }");
        stringBuffer.append("private int value;");
        stringBuffer.append("@Override\n" +
                "    public int getValue() {\n" +
                "        return value;\n" +
                "    }");
        stringBuffer.append(" public static "+getEnumClassName()+" get(Integer value) {\n" +
                "        if (null == value) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        for ("+getEnumClassName()+" status : values()) {\n" +
                "            if (value.equals(status.getValue())) {\n" +
                "                return status;\n" +
                "            }\n" +
                "        }\n" +
                "        return null;\n" +
                "    }");
        return stringBuffer.toString();
    }

    public String  getEnumClassName(){
        return StringUtils.capitalize(removeUnderline(columnInfo.getColumnName()));
    }

    public  String  getEnumFieldDEsc(){
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
        sb.append(PRIVATE).append(getEnumClassName())
                .append(" ")
                .append(getFieldName(columnInfo.getColumnName())).append(SEMICOLON+NEWLINE);
        return sb.toString();
    }
}
