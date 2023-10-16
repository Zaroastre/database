package io.nirahtech.libraries.database.sql;

import java.util.Map;
import java.util.stream.Collectors;

public final class SqlInsertValuesBuilder implements Sql {

    private final StringBuilder sqlBuilder;

    public SqlInsertValuesBuilder(StringBuilder sqlBuilder, Class<?> table) {
        this.sqlBuilder = sqlBuilder;
        this.sqlBuilder.append(String.format("INTO %s ", table.getSimpleName()));
        
    }
    
    public Sql values(Map<String, Object> values) {
        this.sqlBuilder.append("(");
        this.sqlBuilder.append(values.keySet().stream().collect(Collectors.joining(", ")));
        this.sqlBuilder.append(") VALUES (");
        this.sqlBuilder.append(values.values().stream().map(Object::toString).collect(Collectors.joining(", ")));
        this.sqlBuilder.append(");");
        return this;
    }


    @Override
    public String toSql() {
        return this.sqlBuilder.toString();
    }
}
