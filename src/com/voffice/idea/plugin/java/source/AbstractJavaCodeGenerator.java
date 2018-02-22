package com.voffice.idea.plugin.java.source;

/**
 *  这里定义一个 java 文件的主体结构  包  导入   类注释   类注解   类标识信息  类包含的信息
 */
public abstract class AbstractJavaCodeGenerator implements CodeGenerator,JavaCodeElement{

    abstract String  packageCode();

    abstract String  importCode();

    abstract String  classCommentCode();

    abstract String  classAnnotation();

    abstract String  classIdentification();

    abstract String  classbody();

    @Override
    public String produceJavaClassourceCode() {
        StringBuffer sb=new StringBuffer();
        sb.append(packageCode()+NEWLINE);
        sb.append(NEWLINE);
        sb.append(importCode());
        sb.append(NEWLINE);
        sb.append(classCommentCode());
        sb.append(NEWLINE);
        sb.append(classAnnotation()+NEWLINE);
        sb.append(classIdentification()+LEFT_BRACES+NEWLINE);
        sb.append(NEWLINE);
        sb.append(classbody());
        sb.append(NEWLINE);
        sb.append(RIGHT_BRACES);
        return sb.toString();
    }
}
