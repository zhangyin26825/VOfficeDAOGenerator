package com.voffice.idea.plugin.file.field;

import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.jdbc.ColumnInfo;

public interface FieldGenerator extends JavaCodeElement {

    String  generatorFieldDesc(ColumnInfo columnInfo);
}
