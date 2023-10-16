package io.nirahtech.libraries.database;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import io.nirahtech.libraries.database.sql.Sql;

public final class ReadOnlyCluster extends AbstractCluster implements ReadOnly {

    private final ReadOnlyDatabase readOnlyMaster;
    private final Collection<ReadOnlyDatabase> readOnlyNodes = new HashSet<>();

    public ReadOnlyCluster(final Database master, final Set<Database> replicas) {
        super(master, replicas);
        this.readOnlyMaster = (ReadOnlyDatabase) super.getMaster();
        this.readOnlyNodes.addAll(super.getReplications().stream().map(db -> (ReadOnlyDatabase) db).toList());
    }

    public ReadOnlyDatabase getReadOnlyMaster() {
        return this.readOnlyMaster;
    }
    public Collection<ReadOnlyDatabase> getReadOnlyNodes() {
        return this.readOnlyNodes;
    }

    @Override
    public ResultSet select(Sql sql) {
        return this.getReadOnlyMaster().select(sql);
    }

}
