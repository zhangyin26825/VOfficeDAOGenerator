package com.voffice.idea.plugin.java.source;

public interface JavaCodeElement {

    String NEWLINE = "\n";

    String LEFT_BRACES = "{";

    String RIGHT_BRACES = "{";

    String PRIVATE = "private ";

    String PUBLIC = "public ";


    /**
     * 注释
     *
     * @param comments
     * @return
     */
    default String comment(String... comments) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/**" + NEWLINE);
        for (String comment : comments) {
            stringBuffer.append("*" + comment + NEWLINE);
        }
        stringBuffer.append("**/" + NEWLINE);
        return stringBuffer.toString();
    }


}
