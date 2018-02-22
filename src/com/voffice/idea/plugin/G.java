package com.voffice.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.generated.GeneratedEntityTable;
import com.voffice.idea.plugin.util.FindUseUtil;

import java.util.Set;

public class G {

    private static Project p;

    public static Project getProject() {
        return p;
    }

    private static boolean inited=false;

    public static  void run(Project project){
        p=project;
        System.out.println("进入项目初始化方法");
        if(inited){
            return;
        }
        inited=true;
        FindUseUtil.init(project);
        /**
         * 找到所有已经生成过实体类的表
         */
        GeneratedEntityTable.initGeneratedEntityTable();

        Set<PsiJavaFile> entityClasses = GeneratedEntityTable.getEntityClasses();
        /**
         * 根据生成的实体类的信息  初始化相关文件所在的目录
         */
        DirectoryManager.initPoPackage(entityClasses,project);

    }
}
