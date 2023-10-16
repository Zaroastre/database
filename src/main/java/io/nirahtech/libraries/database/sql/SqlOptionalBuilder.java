package io.nirahtech.libraries.database.sql;

public final class SqlOptionalBuilder implements Sql {

    private final StringBuilder sqlBuilder;

    public SqlOptionalBuilder(final StringBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }


    public SqlOptionalBuilder groupBy(final String property) {
        this.sqlBuilder.append(String.format("GROUP BY %s ", property));
        return this;

    }
    public SqlOptionalBuilder orderByAsc(final String property) {
        this.sqlBuilder.append(String.format("ORDER BY %s ASC ", property));
        return this;
        
    }
    public SqlOptionalBuilder orderByDsc(final String property) {
        this.sqlBuilder.append(String.format("ORDER BY %s DESC ", property));
        return this;
    }

    @Override
    public String toSql() {
        return this.sqlBuilder.toString();
    }

}
