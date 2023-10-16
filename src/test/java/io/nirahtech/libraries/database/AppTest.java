package io.nirahtech.libraries.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.nirahtech.libraries.database.sql.Sql;
import io.nirahtech.libraries.database.sql.SqlFactory;

/**
 * Unit test for simple App.
 */
class AppTest {
    
    @Test
    void clusterFactoryTest() {
        final File workspace = new File("db-workspace/nme");
        final int totalNodes = 2;
        final String dbName = "nicolas.metivier";
        final HybridCluster hybridCluster = DatabasesClusterFactory.createHybridCluster(workspace, dbName, totalNodes);
        assertNotNull(hybridCluster);
        final ReadOnlyCluster roc = hybridCluster.readOnlyCluster();
        assertNotNull(roc);
        roc.manage(Fifi.class);
        Sql sql = SqlFactory.select().properties("nom").from(Fifi.class);
        roc.select(sql);
        final WriteOnlyCluster woc = hybridCluster.writeOnlyCluster();
        assertNotNull(woc);
        woc.insert(SqlFactory.insert().into(Fifi.class).values(Map.of("name", "Poule!")));
        
    }
}
