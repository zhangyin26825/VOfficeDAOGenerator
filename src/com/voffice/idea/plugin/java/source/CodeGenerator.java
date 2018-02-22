package com.voffice.idea.plugin.java.source;

public interface CodeGenerator extends JavaCodeElement{
    /**
     * 产生一个java文件的源代码
     * @return
     */
    String  produceJavaClassourceCode();
}
