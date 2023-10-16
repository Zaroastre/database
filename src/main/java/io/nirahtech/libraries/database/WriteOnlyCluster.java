package io.nirahtech.libraries.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.nirahtech.libraries.database.sql.Sql;

public final class WriteOnlyCluster extends AbstractCluster implements WriteOnly {

    private final WriteOnlyDatabase writeOnlyMaster;
    private final Collection<WriteOnlyDatabase> writeOnlyNodes = new HashSet<>();
    private Runnable onChangeEventLister = null;

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

    final void setOnChangeEventLister(Runnable onChangeEventLister) {
        this.onChangeEventLister = onChangeEventLister;
    }

    private final void throwOnChangeEventListerner() {
        if (Objects.nonNull(this.onChangeEventLister)) {
            this.onChangeEventLister.run();
        }
    }

    
    @Override
    public int insert(Sql sql) {
        int totalAffectedRows = this.getWriteOnlyMaster().insert(sql);
        this.throwOnChangeEventListerner();
        final ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREAD_FOR_REPLICATIONS);
        executorService.submit(() -> {
            this.getWriteOnlyNodes().forEach(database -> {
                database.insert(sql);
            });
        });
        return totalAffectedRows;

    }

    @Override
    public int update(Sql sql) {
        int totalAffectedRows = this.getWriteOnlyMaster().update(sql);
        this.throwOnChangeEventListerner();
        final ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREAD_FOR_REPLICATIONS);
        executorService.submit(() -> {
            this.getWriteOnlyNodes().forEach(database -> {
                database.update(sql);
            });
        });
        return totalAffectedRows;
    }

    @Override
    public int delete(Sql sql) {
        int totalAffectedRows = this.getWriteOnlyMaster().delete(sql);
        this.throwOnChangeEventListerner();
        final ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREAD_FOR_REPLICATIONS);
        executorService.submit(() -> {
            this.getWriteOnlyNodes().forEach(database -> {
                database.delete(sql);
            });
        });
        return totalAffectedRows;
    }
    
}
