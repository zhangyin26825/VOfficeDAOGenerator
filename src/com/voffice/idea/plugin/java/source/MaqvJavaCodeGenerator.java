package com.voffice.idea.plugin.java.source;

import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 这里增加 maqv 数据库相关实现的一些方法，增加一些默认的导入
 */
public  abstract  class MaqvJavaCodeGenerator extends JavaCodeGeneratorImpl {

    protected String tableName;

    protected String tableComment;

    protected List<ColumnInfo> columnInfos;

    protected MaqvJavaCodeGenerator(TableInfo tableInfo){
        this.tableName=tableInfo.getTableName();
        this.tableComment=tableInfo.getTableComment();
        this.columnInfos=tableInfo.getColumnInfos();
    }

    public String  getClassName(){
        return  getClassName(tableName);
    }

    protected String getAlias() {
        return getAlias(tableName);
    }

    public Boolean  isMultiId(){
        List<ColumnInfo> collect = getPriKey();
        if(collect.size()<2){
            return false;
        }
        return  true;
    }

    public  List<ColumnInfo> getPriKey(){
        List<ColumnInfo> collect = columnInfos.stream().filter(c -> "PRI".equals(c.getColumnKey())).collect(Collectors.toList());
        return collect;
    }

    public abstract  String  getThisClassName();

    protected  void  addLombokImport(){
        addImport("lombok.Getter");
        addImport("lombok.Setter");
    }

    protected  void addMaqvMysqlImport(){
        addImport("com.maqv.mysql.annotation.Column");
        addImport("com.maqv.mysql.annotation.Id");
        addImport("com.maqv.mysql.annotation.Table");
        addImport("com.maqv.mysql.db.DBColumn");
        addImport("com.maqv.mysql.db.DBTable");
    }

    protected  void addDaoImport(){
        addImport("com.maqv.mybatis.core.dao.MultipleIdDao");
        addImport("com.maqv.mybatis.core.dao.SingleIdDao");
        addImport("com.maqv.mybatis.core.dao.impl.SingleIdDaoImpl");
        addImport("com.maqv.mybatis.core.dao.impl.MultipleIdDaoImpl");
    }

    protected  void  addEnumImport(){
        addImport("com.maqv.mybatis.converter.CommonEnum");
    }

    protected  void addSpringImport(){
        addImport("org.springframework.beans.factory.annotation.Autowired");
        addImport("org.springframework.stereotype.Repository");
        addImport("org.springframework.stereotype.Service");
        addImport("org.springframework.stereotype.Controller");
        addImport("org.springframework.stereotype.Component");
    }

    protected  void  addMapperImport(){
        addImport("com.maqv.mybatis.core.dao.mapper.Mapper");
    }
}
