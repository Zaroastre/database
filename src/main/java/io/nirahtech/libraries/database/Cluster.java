package io.nirahtech.libraries.database;

import java.util.Set;

public interface Cluster {
    Database getMaster();
    Set<Database> getReplications();
    void manage(Class<?>... classesToManage);
}
