package io.nirahtech.libraries.database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class DatabasesClusterFactory {
    private DatabasesClusterFactory() {
    }

    public static final ReadOnlyCluster createReadOnlyCluster(final File readOnlyWorkspaceFolder,
            final String databaseName, final int totalReplicas) {
        final File databaseFile = new File(readOnlyWorkspaceFolder, String.format("%s-master.db", databaseName));
        final Database master = new ReadOnlyDatabase(databaseFile);
        final Set<Database> nodes = new HashSet<>();
        for (int counter = 0; counter < totalReplicas; counter++) {
            final ReadOnlyDatabase replica = new ReadOnlyDatabase(
                    new File(readOnlyWorkspaceFolder, String.format("%s-node-%s.db", databaseName, counter + 1)));
            nodes.add(replica);
        }
        return new ReadOnlyCluster(master, nodes);
    }

    public static final WriteOnlyCluster createWriteOnlyCluster(final File writeOnlyWorkspaceFolder,
            final String databaseName, final int totalReplicas) {
        final File databaseFile = new File(writeOnlyWorkspaceFolder, String.format("%s-master.db", databaseName));
        final Database master = new WriteOnlyDatabase(databaseFile);
        final Set<Database> nodes = new HashSet<>();
        for (int counter = 0; counter < totalReplicas; counter++) {
            final WriteOnlyDatabase replica = new WriteOnlyDatabase(
                    new File(writeOnlyWorkspaceFolder, String.format("%s-node-%s.db", databaseName, counter + 1)));
            nodes.add(replica);
        }
        return new WriteOnlyCluster(master, nodes);
    }

    public static final HybridCluster createHybridCluster(final File databasesWorkspaceFolder,
            final String databaseName, final int totalReplicas) {
        final ReadOnlyCluster readOnlyCluster = createReadOnlyCluster(new File(databasesWorkspaceFolder, "ro"),
                databaseName, totalReplicas);
        final WriteOnlyCluster writeOnlyCluster = createWriteOnlyCluster(new File(databasesWorkspaceFolder, "wo"),
                databaseName, totalReplicas);
        writeOnlyCluster.setOnChangeEventLister((changeOperation, data) -> {
            final ExecutorService executorService = Executors.newFixedThreadPool(2);
            executorService.execute(() -> {
                final File masterFile = writeOnlyCluster.getMaster().getFile();
                try {
                        System.out.println("Sync!");
                        readOnlyCluster.getMaster().close();
                        Files.copy(masterFile.toPath(), readOnlyCluster.getMaster().getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException exception) {
                        // TODO Auto-generated catch block
                        exception.printStackTrace();
                    }
                // final WriteOnlyDatabase replicaMaster = (WriteOnlyDatabase) readOnlyCluster.getMaster();
                // switch (changeOperation) {
                //     case INSERT:
                //         replicaMaster.insert(data);
                //         break;
                //     case UPDATE:
                //         replicaMaster.update(data);
                //         break;
                //     case DELETE:
                //         replicaMaster.delete(data);
                //         break;
                //     default:
                //         break;
                // }
                System.out.println("SynchroYes!!");
                readOnlyCluster.getReplications().stream().map(Database::getFile).forEach(roReplicaFile -> {
                    try {
                        System.out.println("Sync!");
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
