package io.nirahtech.libraries.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
    public <T> List<T> select(Class<T> table) {
        return this.getReadOnlyMaster().select(table);
    }

    @Override
    public <T> List<T> search(Class<T> table, Function<Session, Class<T>, Root<T>, CriteriaQuery<T>, List<T>> filter) {
        return this.getReadOnlyMaster().search(table, filter);
    }

}
