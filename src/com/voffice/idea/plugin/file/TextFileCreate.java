package com.voffice.idea.plugin.file;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.voffice.idea.plugin.fileoperation.FileOperation;

/**
 * 文本文件创建
 */
public interface TextFileCreate extends  FileCreate{

    /**
     * 获取文件的内容
     * @return
     */
    public String   getFileContent();

    /**
     * 获取文件的名字
     * @return
     */
    public String   getFileName();

    /**
     * 文件放置的目录
     * @return
     */
    public PsiDirectory  getDirectory();

    @Override
    default PsiFile create(){
        return FileOperation.createFileExistDel(getFileName(), getFileContent(), getDirectory());
    }
}
