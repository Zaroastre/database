package io.nirahtech.libraries.database.runtime;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final record Table (
    Class<?> entity,
    Map<String, TableField> fields
) {

    public String generateCreationSqlQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("CREATE TABLE IF NOT EXISTS %s (", this.entity.getSimpleName()));
        AtomicInteger counter = new AtomicInteger(0);
        this.fields.values().stream().forEach(field -> {
            builder.append(String.format("%s %s", field.getName(), field.getType()));
            if (counter.get() < this.fields.size()-1) {
                builder.append(",");
            }
            builder.append("\n");
            counter.incrementAndGet();
        });
        builder.append(");\n");
        return builder.toString();
    }

    public static final Table from(Class<?> managedClass) {
        final Map<String, TableField> fields = new HashMap<>();
        final Field[] properties = managedClass.getDeclaredFields();
        Stream.of(properties).forEach(field -> {
            fields.put(field.getName(), new TableField(field.getName(), field.getType()));
        });
        return new Table(managedClass, fields);
    }
}
