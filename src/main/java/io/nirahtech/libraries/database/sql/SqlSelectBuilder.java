package io.nirahtech.libraries.database.sql;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SqlSelectBuilder implements Sql {

    private final StringBuilder sqlBuilder = new StringBuilder();
    public SqlSelectBuilder() {
        this.sqlBuilder.append("SELECT ");
    }

    public SqlSelectBuilder distinct() {
        this.sqlBuilder.append("DISTINCT ");
        return this;
    }

    public SqlSelectBuilder count() {
        this.sqlBuilder.append("COUNT(*) ");
        return this;
    }

    public SqlSelectBuilder properties(final String... properties) {
        if (Objects.nonNull(properties)) {
            this.sqlBuilder.append(Stream.of(properties).collect(Collectors.joining(", ")));
            this.sqlBuilder.append(" ");
        }
        return this;
    }

    public SqlSelectBuilder sum(final String property) {
        this.sqlBuilder.append(String.format("SUM(%s) ", property));
        return this;
    }
    public SqlSelectBuilder avg(final String property) {
        this.sqlBuilder.append(String.format("AVG(%s) ", property));
        return this;
    }

    public SqlSelectBuilder min(final String property) {
        this.sqlBuilder.append(String.format("MIN(%s) ", property));
        return this;
    }

    public SqlSelectBuilder max(final String property) {
        this.sqlBuilder.append(String.format("MAX(%s) ", property));
        return this;
    }


    public SqlFromBuilder from(final Class<?> table) {
        return new SqlFromBuilder(this.sqlBuilder, table);
    }


    @Override
    public String toSql() {
        return this.sqlBuilder.toString();
    }
}
