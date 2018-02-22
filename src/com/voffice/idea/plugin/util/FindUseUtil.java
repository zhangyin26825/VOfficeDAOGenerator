package com.voffice.idea.plugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Query;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FindUseUtil {

    private  static Project project;

    private static JavaPsiFacade javaPsiFacade;

    public static  void init(Project p){
        project=p;
        javaPsiFacade=JavaPsiFacade.getInstance(project);
    }

    /**
     * 根据完整的类名 找到idea 中对应的 PsiClass
     * @param qualifiedName
     * @return
     */
    public static PsiClass  findClass(String qualifiedName){
        PsiClass psiClass = javaPsiFacade.findClass(qualifiedName, GlobalSearchScope.allScope(project));
        return psiClass;
    }

    /**
     * 根据完整的类名  找到idea 中的对应的 PsiPackage
     * @param qualifiedName
     * @return
     */
    public static PsiPackage findPackage(String qualifiedName){
        return javaPsiFacade.findPackage(qualifiedName);
    }

    /**
     *  根据完整的类名  找到 所有使用 这个类的对应的 java类
     * @param qualifiedName
     * @return
     */
    public  static Set<PsiJavaFile> findUseages(String qualifiedName){
        PsiClass psiClass = findClass(qualifiedName);
        if (psiClass == null) {
            return Collections.emptySet();
        }
        /**
         * 这里搜索出来的是  使用过这个类的 java代码片段  可能是属性  可能是方法的参数  注解的属性  等等
         * 用 getContainingFile   找到具体的 java类
         *
         */
        Query<PsiReference> search = ReferencesSearch.search(psiClass, GlobalSearchScope.allScope(project));
        Set<PsiJavaFile> set=new HashSet<>();
        search.forEach(p->{
            PsiElement element = p.getElement();
            PsiFile containingFile = element.getContainingFile();
            if(containingFile instanceof  PsiJavaFile){
                set.add((PsiJavaFile)containingFile);
            }
        });
        return set;
    }



}
