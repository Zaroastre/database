package io.nirahtech.libraries.database.runtime;

import java.math.BigDecimal;

public final class TableField {
    private final String name;
    private final String type;

    public TableField(final String name, final Class<?> type) {
        this.name = name;
        if (type == String.class || type == Character.class) {
            this.type = "TEXT";
        } else if (type == Integer.class || type == Byte.class || type == Short.class || type == Long.class) {
            this.type = "INTEGER";
        } else if (type == Double.class || type == Float.class) {
            this.type = "REAL";
        } else if (type == BigDecimal.class) {
            this.type = "NUMERIC";
        } else if (type == Boolean.class) {
            this.type = "BOOLEAN";
        }
        else {
            this.type = null;
        }
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }

}
