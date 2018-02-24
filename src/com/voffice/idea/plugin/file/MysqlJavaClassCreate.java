package com.voffice.idea.plugin.file;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.file.enumclass.EnumJavaFile;
import com.voffice.idea.plugin.file.field.EnumFieldFilter;
import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.file.field.impl.EntityFieldGenerator;
import com.voffice.idea.plugin.file.field.impl.EnumFilter;
import com.voffice.idea.plugin.file.impl.*;
import com.voffice.idea.plugin.fileoperation.FileOperation;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;

import java.util.List;

public class MysqlJavaClassCreate {

    TableInfo tableInfo;

    EnumFieldFilter enumFieldFilter;

    FieldGenerator fieldGenerator;


    public MysqlJavaClassCreate(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        enumFieldFilter=new EnumFilter();
        fieldGenerator=new EntityFieldGenerator();
    }

    public  void  createFiles(){
        EntityJavaFile entityJavaFile = new EntityJavaFile(tableInfo);
        TableJavaFile tableJavaFile = new TableJavaFile(tableInfo);
        MapperJavaFile mapperJavaFile = new MapperJavaFile(tableInfo);
        DaoJavaFile daoJavaFile = new DaoJavaFile(tableInfo);
        DaoImplJavaFile daoImplJavaFile = new DaoImplJavaFile(tableInfo);
        PsiJavaFile entity = (PsiJavaFile)entityJavaFile.create();
        PsiJavaFile table = (PsiJavaFile)tableJavaFile.create();
        PsiJavaFile mapper = (PsiJavaFile)mapperJavaFile.create();
        PsiJavaFile dao = (PsiJavaFile)daoJavaFile.create();
        PsiJavaFile daoImpl = (PsiJavaFile)daoImplJavaFile.create();
        List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
        for (ColumnInfo columnInfo : columnInfos) {
            if(enumFieldFilter.isEnum(columnInfo)){
                EnumJavaFile enumJavaFile = new EnumJavaFile(columnInfo);
                PsiJavaFile enumjavaFile = (PsiJavaFile)enumJavaFile.create();
                FileOperation.addJavaImport(entity, enumjavaFile.getClasses()[0]);
                String fieldDesc = enumJavaFile.generateFieldDesc();
                FileOperation.addJavaField(entity, fieldDesc);
            }else{
                String fieldDesc = fieldGenerator.generatorFieldDesc(columnInfo);
                FileOperation.addJavaField(entity, fieldDesc);

            }
        }
    }
}
