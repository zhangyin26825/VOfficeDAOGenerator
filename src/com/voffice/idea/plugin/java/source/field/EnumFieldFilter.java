package com.voffice.idea.plugin.java.source.field;

import com.voffice.idea.plugin.jdbc.ColumnInfo;

public interface EnumFieldFilter {

    boolean isEnum(ColumnInfo columnInfo);
}
