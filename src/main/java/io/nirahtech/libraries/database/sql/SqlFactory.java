package io.nirahtech.libraries.database.sql;

public final class SqlFactory {

    private SqlFactory() {}
    
    public static final SqlSelectBuilder select() {
        return new SqlSelectBuilder();
    }

    public static final SqlInsertBuilder insert() {
        return new SqlInsertBuilder();
    }
}
