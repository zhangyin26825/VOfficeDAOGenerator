package com.voffice.idea.plugin.java.source.field;

import com.voffice.idea.plugin.java.source.JavaCodeElement;
import com.voffice.idea.plugin.jdbc.ColumnInfo;

public interface FieldGenerator extends JavaCodeElement {

    String  generatorFieldDesc(ColumnInfo columnInfo);
}
