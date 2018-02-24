package com.voffice.idea.plugin.file.impl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;
import com.voffice.idea.plugin.jdbc.TableInfo;

public class DaoImplJavaFile extends MysqlJavaFileCreate {

    public DaoImplJavaFile(TableInfo tableInfo) {
        super(tableInfo);
        addDaoImport();
        addBaseEntityImport();
        addSpringImport();
        addImport(DirectoryManager.getDaoPackage()+"."+getBaseEntityName()+"Dao");
        addImport(DirectoryManager.getMapperPackage()+"."+getBaseEntityName()+"Mapper");
    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"DaoImpl";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getDaoImplPackage();
    }

    @Override
    public String classCommentCode() {
        return comment(" Dao for "+tableName," ",AUTHOR);
    }

    @Override
    public String classAnnotation() {
        return "@Repository";
    }

    @Override
    public String classIdentification() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PUBLIC).append(CLASS).append(getClassName()).append(EXTENDS);
        if(isMultiId()){
            stringBuffer.append("MultipleIdDaoImpl");
        }else if(isNoId()){
            stringBuffer.append("NoIdDaoImpl");
        }else{
            stringBuffer.append("SingleIdDaoImpl");
        }
        stringBuffer.append(getBaseEntityGeneric());
        stringBuffer.append(IMPLEMENTS).append(getBaseEntityName()+"Dao");
        return stringBuffer.toString();
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("@Autowired\n" +
                "    private "+getBaseEntityName()+"Mapper mapper;\n" +
                "\n" +
                "    @Override\n" +
                "    public Mapper<"+getBaseEntityName()+"> getMapper() {\n" +
                "    return mapper;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public Class<"+getBaseEntityName()+"> getPOClazz() {\n" +
                "        return "+getBaseEntityName()+".class;\n" +
                "    }");
        return stringBuffer.toString();
    }
}