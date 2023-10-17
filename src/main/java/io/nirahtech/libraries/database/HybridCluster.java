package io.nirahtech.libraries.database;

import java.util.Objects;
import java.util.stream.Stream;

public final class HybridCluster {
    private final ReadOnlyCluster readOnlyCluster;
    private final WriteOnlyCluster writeOnlyCluster;

    public HybridCluster(final ReadOnlyCluster readOnlyCluster, final WriteOnlyCluster writeOnlyCluster) {
        this.readOnlyCluster = readOnlyCluster;
        this.writeOnlyCluster = writeOnlyCluster;
    }

    public ReadOnlyCluster readOnlyCluster() {
        return readOnlyCluster;
    }
    public WriteOnlyCluster writeOnlyCluster() {
        return writeOnlyCluster;
    }

    public void manage(Class<?>... managedClasses) {
        if (Objects.nonNull(managedClasses)) {
            Stream.of(managedClasses).forEach(managedClass -> {
                this.readOnlyCluster.manage(managedClasses);
                this.writeOnlyCluster.manage(managedClasses);
            });
        }
    }
}
