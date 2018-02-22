package com.voffice.idea.plugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.generated.GeneratedEntityTable;
import com.voffice.idea.plugin.util.FindUseUtil;

import java.util.Set;

public class MyProjectComponent implements ProjectComponent {

    private static Project project;

    public MyProjectComponent(Project p) {
        project = p;
    }

    @Override
    public void projectOpened() {

    }

    public static Project getProject() {
        return project;
    }
}
