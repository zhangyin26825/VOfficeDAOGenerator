package com.voffice.idea.plugin.directory;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.voffice.idea.plugin.collector.MostRepeatedElementCollector;
import com.voffice.idea.plugin.util.FindUseUtil;
import org.apache.log4j.Logger;

import java.util.Set;


public class DirectoryManager {

    private static Logger logger = Logger.getLogger(DirectoryManager.class);



    //实体类所在的包
    private static String poPackage;
    //对应的idea中的目录对象   增加java类的必须用 psiTableDirectory.add方法来实现
    private static PsiDirectory psiPoDirectory;

    //table类
    private static String tablePackage;

    private static PsiDirectory psiTableDirectory;
    //枚举类
    private static String typePackage;

    private static PsiDirectory psiTypeDirectory;

    //Dao
    private static String daoPackage;

    private static PsiDirectory psiDaoDirectory;
    //Dao实现类
    private static String daoImplPackage;

    private static PsiDirectory psiDaoImplDirectory;
    //Mapper
    private static String mapperPackage;

    private static PsiDirectory psiMapperDirectory;

    //sql文件放置的目录
    private static PsiDirectory  sqlDiretory;

    //mybatis xml放置的目录
    private static  PsiDirectory xmlMapperDirectory;

    private static Project project;

    public static void initPoPackage(Set<PsiJavaFile> entityClasses,Project p){
        project=p;
        poPackage = entityClasses.stream()
                .map(e -> e.getPackageName())
                .collect(new MostRepeatedElementCollector<>());
        logger.info("实体类所在的目录为"+poPackage);
        PsiPackage psiPoPackage = FindUseUtil.findPackage(poPackage);
        psiPoDirectory=psiPoPackage.getDirectories()[0];
        logger.info("实体类所在的目录为"+poPackage+"对应的目录是否为空 "+(psiPoDirectory==null));
        initDirectorys();
    }


    private static void initDirectorys() {
        PsiPackage psiPoPackage = FindUseUtil.findPackage(poPackage);
        {
            PsiPackage[] subPackages = psiPoPackage.getSubPackages();
            for (PsiPackage subPackage : subPackages) {
                if (subPackage.getName().equals("table")) {
                    tablePackage = subPackage.getQualifiedName();
                    psiTableDirectory = (PsiDirectory) subPackage.getDirectories()[0];
                }
                if (subPackage.getName().equals("type")) {
                    typePackage = subPackage.getQualifiedName();
                    psiTypeDirectory = (PsiDirectory) subPackage.getDirectories()[0];
                }
            }
            logger.info("table类所在的目录为"+tablePackage+"对应的目录是否为空 "+(psiTableDirectory==null));
            logger.info("type所在的目录为"+typePackage+"对应的目录是否为空 "+(psiTypeDirectory==null));
        }
        PsiPackage psiDaoPackage=null;
        {
            PsiPackage parentPackage = psiPoPackage.getParentPackage();
            PsiPackage[] subPackages1 = parentPackage.getSubPackages();
            for (PsiPackage psiPackage : subPackages1) {
                if (psiPackage.getName().equals("dao")) {
                    psiDaoPackage = psiPackage;
                    daoPackage = psiPackage.getQualifiedName();
                    psiDaoDirectory = (PsiDirectory) psiPackage.getDirectories()[0];
                }

            }
            logger.info("dao类所在的目录为"+daoPackage+"对应的目录是否为空 "+(psiDaoDirectory==null));
        }
        PsiPackage[] subPackages = psiDaoPackage.getSubPackages();
        for (PsiPackage subPackage : subPackages) {
            if(subPackage.getName().equals("impl")){
                daoImplPackage=subPackage.getQualifiedName();
                psiDaoImplDirectory=(PsiDirectory) subPackage.getDirectories()[0];
            }
            if(subPackage.getName().equals("mapper")){
                mapperPackage=subPackage.getQualifiedName();
                psiMapperDirectory=(PsiDirectory)subPackage.getDirectories()[0];
            }

        }
        logger.info("daoImpl类所在的目录为"+daoImplPackage+"对应的目录是否为空 "+(psiDaoImplDirectory==null));
        logger.info("Mapper类所在的目录为"+mapperPackage+"对应的目录是否为空 "+(psiMapperDirectory==null));
        initSqlAndMybatisXmlDirectory(psiDaoPackage);


        if(poPackage==null||psiPoDirectory==null||tablePackage==null
                ||psiTableDirectory==null||typePackage==null||psiTypeDirectory==null
                ||daoPackage==null||psiDaoDirectory==null||daoImplPackage==null
                ||psiDaoImplDirectory==null||mapperPackage==null||psiMapperDirectory==null
                ||xmlMapperDirectory==null
                ){
            throw new RuntimeException("初始化目录失败");
        }
    }

    private  static void  initSqlAndMybatisXmlDirectory(PsiPackage psiPackage){
        VirtualFile virtualFile = psiPackage.getDirectories()[0].getVirtualFile();
        Module module = ModuleUtilCore.findModuleForFile(virtualFile, project);
        VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
        for (VirtualFile sourceRoot : sourceRoots) {
            if(sourceRoot.getName().equals("resources")){
                VirtualFile migrations = sourceRoot.findChild("db").findChild("migrations");
                PsiDirectory directory = PsiManager.getInstance(project).findDirectory(sourceRoot);
                sqlDiretory= directory.findSubdirectory("db").findSubdirectory("migrations");
                xmlMapperDirectory=directory.findSubdirectory("com").findSubdirectory("voffice")
                        .findSubdirectory("sz")
                        .findSubdirectory("core")
                        .findSubdirectory("v2")
                        .findSubdirectory("dao")
                        .findSubdirectory("mapper");
                break;
            }
        }
        logger.info("sql对应的目录是否为空 "+(sqlDiretory==null));
        logger.info("xml对应的目录是否为空 "+(xmlMapperDirectory==null));
    }

    public static String getPoPackage() {
        return poPackage;
    }

    public static PsiDirectory getPsiPoDirectory() {
        return psiPoDirectory;
    }

    public static String getTablePackage() {
        return tablePackage;
    }


    public static PsiDirectory getPsiTableDirectory() {
        return psiTableDirectory;
    }



    public static String getTypePackage() {
        return typePackage;
    }


    public static PsiDirectory getPsiTypeDirectory() {
        return psiTypeDirectory;
    }


    public static String getDaoPackage() {
        return daoPackage;
    }


    public static PsiDirectory getPsiDaoDirectory() {
        return psiDaoDirectory;
    }



    public static String getDaoImplPackage() {
        return daoImplPackage;
    }


    public static PsiDirectory getPsiDaoImplDirectory() {
        return psiDaoImplDirectory;
    }



    public static String getMapperPackage() {
        return mapperPackage;
    }



    public static PsiDirectory getPsiMapperDirectory() {
        return psiMapperDirectory;
    }



    public static PsiDirectory getSqlDiretory() {
        return sqlDiretory;
    }



    public static PsiDirectory getXmlMapperDirectory() {
        return xmlMapperDirectory;
    }




}
