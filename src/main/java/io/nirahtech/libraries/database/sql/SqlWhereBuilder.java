package io.nirahtech.libraries.database.sql;

public final class SqlWhereBuilder implements Sql {

    private final StringBuilder sqlBuilder;

    public SqlWhereBuilder(final StringBuilder sqlBuilder, final String condition) {
        this.sqlBuilder = sqlBuilder;
        sqlBuilder.append(String.format("WHERE %s ", condition));
    }

    public SqlWhereBuilder and(final String condition) {
        sqlBuilder.append(String.format("AND %s ", condition));
        return this;
    }

    public SqlWhereBuilder or(final String condition) {
        sqlBuilder.append(String.format("OR %s ", condition));
        return this;
    }

    public SqlOptionalBuilder groupBy(final String property) {
        return new SqlOptionalBuilder(this.sqlBuilder).groupBy(property);

    }
    public SqlOptionalBuilder orderByAsc(final String property) {
        return new SqlOptionalBuilder(this.sqlBuilder).orderByAsc(property);
        
    }
    public SqlOptionalBuilder orderByDsc(final String property) {
        return new SqlOptionalBuilder(this.sqlBuilder).orderByDsc(property);
    }

    @Override
    public String toSql() {
        return this.sqlBuilder.toString();
    }
}
