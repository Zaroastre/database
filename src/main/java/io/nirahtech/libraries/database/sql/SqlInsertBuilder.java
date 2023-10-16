package io.nirahtech.libraries.database.sql;

public final class SqlInsertBuilder implements Sql {

    private final StringBuilder sqlBuilder = new StringBuilder();

    public SqlInsertBuilder() {
        this.sqlBuilder.append("INSERT ");
        
    }

    public SqlInsertValuesBuilder into(Class<?> table) {
        return new SqlInsertValuesBuilder(sqlBuilder, table);
    }


    @Override
    public String toSql() {
        return this.sqlBuilder.toString();
    }
}
