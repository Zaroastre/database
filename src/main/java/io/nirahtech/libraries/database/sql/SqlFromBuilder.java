package io.nirahtech.libraries.database.sql;

public final class SqlFromBuilder implements Sql {

    private final StringBuilder sqlBuilder;
    private final Class<?> table;

    public SqlFromBuilder(final StringBuilder sqlBuilder, final Class<?> table) {
        this.table = table;
        this.sqlBuilder = sqlBuilder;
        sqlBuilder.append(String.format("FROM %s ", table.getSimpleName()));
    }

    public SqlFromBuilder join(final Class<?> joinTable, final String primaryKey, final String foreignKey) {
        this.sqlBuilder.append(String.format("JOIN %s ON %s.%s = %s.%s ", this.table.getSimpleName(), table.getSimpleName(), primaryKey, joinTable.getSimpleName(), foreignKey));
        return this;
    }

    public SqlWhereBuilder where(final String condition) {
        return new SqlWhereBuilder(this.sqlBuilder, condition);
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
