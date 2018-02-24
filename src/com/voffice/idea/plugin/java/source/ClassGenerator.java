package com.voffice.idea.plugin.java.source;

import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.fileoperation.FileOperation;
import com.voffice.idea.plugin.java.source.field.EnumFieldFilter;
import com.voffice.idea.plugin.java.source.field.FieldGenerator;
import com.voffice.idea.plugin.java.source.impl.DaoJavaCodeGenerator;
import com.voffice.idea.plugin.java.source.impl.MapperJavaCodeGenerator;
import com.voffice.idea.plugin.java.source.impl.PoJavaCodeGenerator;
import com.voffice.idea.plugin.java.source.impl.TableJavaCodeGenerator;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;

import java.util.List;

public class ClassGenerator implements  JavaCodeElement{

    private static String JavaSuffix=".java";

    FieldGenerator fieldGenerator;

    EnumFieldFilter enumFieldFilter;

    TableInfo tableInfo;


    public ClassGenerator(FieldGenerator fieldGenerator, EnumFieldFilter enumFieldFilter, TableInfo tableInfo) {
        this.fieldGenerator = fieldGenerator;
        this.enumFieldFilter = enumFieldFilter;
        this.tableInfo = tableInfo;
    }

    public  void generator(){
        PsiJavaFile poJavaFile = generatorPo();
        PsiJavaFile tableClass = generatorTableClass();
        PsiJavaFile daoClass = generatorDaoClass();
        FileOperation.addJavaImport(daoClass, poJavaFile.getClasses()[0]);
        PsiJavaFile mapperClass = generatorMapperClass();
        FileOperation.addJavaImport(mapperClass, poJavaFile.getClasses()[0]);
    }

    public  PsiJavaFile  generatorPo(){
        CodeGenerator codeGenerator = new PoJavaCodeGenerator(tableInfo);
        String classourceCode = codeGenerator.produceJavaClassourceCode();
        PsiJavaFile psiJavaFile = (PsiJavaFile)FileOperation.createFileExistDel(getClassName(tableInfo.getTableName())+JavaSuffix,
                classourceCode, DirectoryManager.getPsiPoDirectory());
        List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
        for (ColumnInfo columnInfo : columnInfos) {
           if(enumFieldFilter.isEnum(columnInfo)){
               MaqvEnumJavaCodeGenerator enumJavaCodeGenerator=new MaqvEnumJavaCodeGenerator(columnInfo);
               String enumSourceCode = enumJavaCodeGenerator.produceJavaClassourceCode();
               PsiJavaFile enumClass = (PsiJavaFile)FileOperation.createFileExistCancel(enumJavaCodeGenerator.getEnumClassName() + JavaSuffix, enumSourceCode, DirectoryManager.getPsiTypeDirectory());
               FileOperation.addJavaImport(psiJavaFile, enumClass.getClasses()[0]);
               String enumFieldDEsc = enumJavaCodeGenerator.getEnumFieldDEsc();
               FileOperation.addJavaField(psiJavaFile, enumFieldDEsc);
           }else{
               String fieldDesc = fieldGenerator.generatorFieldDesc(columnInfo);
               FileOperation.addJavaField(psiJavaFile, fieldDesc);
           }
        }
        return psiJavaFile;
    }

    public PsiJavaFile generatorTableClass(){
        TableJavaCodeGenerator codeGenerator = new TableJavaCodeGenerator(tableInfo);
        String tableClassSourceCode = codeGenerator.produceJavaClassourceCode();
        PsiJavaFile psiJavaFile = (PsiJavaFile)FileOperation
                .createFileExistDel(codeGenerator.getThisClassName()+JavaSuffix,tableClassSourceCode,DirectoryManager.getPsiTableDirectory());
        return psiJavaFile;
    }

    public  PsiJavaFile  generatorDaoClass(){
        DaoJavaCodeGenerator codeGenerator=new DaoJavaCodeGenerator(tableInfo);
        String daoSourceCode = codeGenerator.produceJavaClassourceCode();
        PsiJavaFile psiJavaFile = (PsiJavaFile)FileOperation
                .createFileExistDel(codeGenerator.getThisClassName()+JavaSuffix,daoSourceCode,DirectoryManager.getPsiDaoDirectory());
        return psiJavaFile;
    }

    public PsiJavaFile  generatorMapperClass(){
        MapperJavaCodeGenerator codeGenerator=new MapperJavaCodeGenerator(tableInfo);
        String daoSourceCode = codeGenerator.produceJavaClassourceCode();
        PsiJavaFile psiJavaFile = (PsiJavaFile)FileOperation
                .createFileExistDel(codeGenerator.getThisClassName()+JavaSuffix,daoSourceCode,DirectoryManager.getPsiMapperDirectory());
        return psiJavaFile;

    }
}
