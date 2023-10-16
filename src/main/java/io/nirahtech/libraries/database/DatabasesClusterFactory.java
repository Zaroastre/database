package io.nirahtech.libraries.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DatabasesClusterFactory {
    private DatabasesClusterFactory() { }

    public static final ReadOnlyCluster createReadOnlyCluster(final File readOnlyWorkspaceFolder, final String databaseName, final int totalReplicas) {
        final Database master = new ReadOnlyDatabase(new File(readOnlyWorkspaceFolder, String.format("%s-master.db", databaseName)));
        final Set<Database> nodes = new HashSet<>();
        for (int counter = 0; counter < totalReplicas; counter++) {
            final ReadOnlyDatabase replica = new ReadOnlyDatabase(new File(readOnlyWorkspaceFolder, String.format("%s-node-%s.db", databaseName, counter+1)));
            nodes.add(replica);
        }
        return new ReadOnlyCluster(master, nodes);
    }

    public static final WriteOnlyCluster createWriteOnlyCluster(final File writeOnlyWorkspaceFolder, final String databaseName, final int totalReplicas) {
        final Database master = new WriteOnlyDatabase(new File(writeOnlyWorkspaceFolder, String.format("%s-master.db", databaseName)));
        final Set<Database> nodes = new HashSet<>();
        for (int counter = 0; counter < totalReplicas; counter++) {
            final WriteOnlyDatabase replica = new WriteOnlyDatabase(new File(writeOnlyWorkspaceFolder, String.format("%s-node-%s.db", databaseName, counter+1)));
            nodes.add(replica);
        }
        return new WriteOnlyCluster(master, nodes);
    }

    public static final HybridCluster createHybridCluster(final File databasesWorkspaceFolder, final String databaseName, final int totalReplicas) {
        final ReadOnlyCluster readOnlyCluster = createReadOnlyCluster(new File(databasesWorkspaceFolder, "ro"), databaseName, totalReplicas);
        final WriteOnlyCluster writeOnlyCluster = createWriteOnlyCluster(new File(databasesWorkspaceFolder, "wo"), databaseName, totalReplicas);
        writeOnlyCluster.setOnChangeEventLister(() -> {
            final ExecutorService executorService = Executors.newFixedThreadPool(2);
            executorService.submit(() -> {
                final File masterFile = writeOnlyCluster.getMaster().getFile();
                readOnlyCluster.getReplications().stream().map(Database::getFile).forEach(roReplicaFile -> {
                    try {
                        Files.copy(masterFile.toPath(), roReplicaFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException exception) {
                        // TODO Auto-generated catch block
                        exception.printStackTrace();
                    }
                });
            });
        });
        return new HybridCluster(readOnlyCluster, writeOnlyCluster);
    }


}
