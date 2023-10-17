package io.nirahtech.libraries.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class WriteOnlyCluster extends AbstractCluster implements WriteOnly {

    private final WriteOnlyDatabase writeOnlyMaster;
    private final Collection<WriteOnlyDatabase> writeOnlyNodes = new HashSet<>();
    private BiConsumer<ChangeOperation, Object> onChangeEventLister = null;

    public WriteOnlyCluster(final Database master, final Set<Database> replicas) {
        super(master, replicas);
        this.writeOnlyMaster = (WriteOnlyDatabase) super.getMaster();
        this.writeOnlyNodes.addAll(super.getReplications().stream().map(db -> (WriteOnlyDatabase) db).toList());
    }

    public WriteOnlyDatabase getWriteOnlyMaster() {
        return this.writeOnlyMaster;
    }
    public Collection<WriteOnlyDatabase> getWriteOnlyNodes() {
        return this.writeOnlyNodes;
    }

    final void setOnChangeEventLister(BiConsumer<ChangeOperation, Object> onChangeEventLister) {
        this.onChangeEventLister = onChangeEventLister;
    }

    private final void fireOnChangeEventListerner(final ChangeOperation changeOperation, Object object) {
        System.out.println("fireOnChangeEventListerner");
        if (Objects.nonNull(this.onChangeEventLister)) {
            this.onChangeEventLister.accept(changeOperation, object);
        }
    }

    
    @Override
    public <T> T insert(T data) {
        this.getWriteOnlyMaster().insert(data);
        this.fireOnChangeEventListerner(ChangeOperation.INSERT, data);
        try (ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREAD_FOR_REPLICATIONS)) {
            executorService.submit(() -> {
                this.getWriteOnlyNodes().forEach(node -> node.insert(data));
            });
        }
        return data;

    }

    @Override
    public <T> T update(T data) {
        this.getWriteOnlyMaster().update(data);
        this.fireOnChangeEventListerner(ChangeOperation.UPDATE, data);
        try (ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREAD_FOR_REPLICATIONS)) {
            executorService.submit(() -> {
                this.getWriteOnlyNodes().forEach(node -> node.update(data));
            });
        }
        return data;
    }

    @Override
    public <T> void delete(T data) {
        this.getWriteOnlyMaster().delete(data);
        this.fireOnChangeEventListerner(ChangeOperation.DELETE, data);
        try (ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREAD_FOR_REPLICATIONS)) {
            executorService.submit(() -> {
                this.getWriteOnlyNodes().forEach(node -> node.delete(data));
            });
        }
    }
    
}
