package io.nirahtech.libraries.database;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

abstract class AbstractCluster implements Cluster {
    protected static final int TOTAL_THREAD_FOR_REPLICATIONS = 2;

    private final Database master;
    private final Collection<Database> nodes = new HashSet<>();

    protected AbstractCluster(final Database master, Set<Database> replicas) {
        this.master = master;
        this.nodes.addAll(replicas);
    }

    @Override
    public Database getMaster() {
        return this.master;
    }
    @Override
    public Set<Database> getReplications() {
        return Collections.unmodifiableSet(new HashSet<>(this.nodes));
    }
    @Override
    public void manage(Class<?>... classesToManage) {
        Stream.of(classesToManage).forEach(classToManage -> {
            this.master.manage(classToManage);
            this.nodes.forEach(node -> {
                node.manage(classToManage);
            });
        });
    }
}
