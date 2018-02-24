package com.voffice.idea.plugin.file.field.impl;

import com.voffice.idea.plugin.file.field.EnumFieldFilter;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import org.apache.commons.lang.StringUtils;

public class EnumFilter implements EnumFieldFilter {

    @Override
    public boolean isEnum(ColumnInfo columnInfo) {
        return StringUtils.endsWith(columnInfo.getColumnName(), "enum");
    }
}
