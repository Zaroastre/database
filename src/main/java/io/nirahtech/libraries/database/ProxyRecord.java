package io.nirahtech.libraries.database;

public class ProxyRecord<T> {
    private final Class<T> managedRecord;

    public ProxyRecord(final Class<T> managedRecord) {
        this.managedRecord = managedRecord;
    }

    
}
