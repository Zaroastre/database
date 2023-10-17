package io.nirahtech.libraries.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.UUID;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class AppTest {

    static final File WORKSPACE = new File("db-workspace/" + UUID.randomUUID().toString());
    static final int TOTAL_NODES = 2;
    final String DATABASE_NAME = UUID.randomUUID().toString().split("-")[0];

    
    @Test
    void clusterFactoryTest() {
        final HybridCluster hybridCluster = DatabasesClusterFactory.createHybridCluster(WORKSPACE, DATABASE_NAME, TOTAL_NODES);
        assertNotNull(hybridCluster);
        final ReadOnlyCluster roc = hybridCluster.readOnlyCluster();
        assertNotNull(roc);
        final WriteOnlyCluster woc = hybridCluster.writeOnlyCluster();
        assertNotNull(woc);
    }

    @Test
    void sqlGenerationTest() {
        final HybridCluster hybridCluster = DatabasesClusterFactory.createHybridCluster(WORKSPACE, DATABASE_NAME, TOTAL_NODES);
        hybridCluster.manage(Vilain.class);
        hybridCluster.manage(Bag.class);
        for (int i = 0; i < 10; i++) {
            System.out.println("Before");
            Vilain heroe = new Vilain(i+1, UUID.randomUUID().toString(), Race.HUMAN);
            System.out.println(
                hybridCluster.readOnlyCluster().select(Vilain.class)
            );
            hybridCluster.writeOnlyCluster().insert(heroe);
            Bag bag = new Bag();
            bag.setUuid(UUID.randomUUID());
            hybridCluster.writeOnlyCluster().insert(bag);
            heroe.setBag(bag);
            hybridCluster.writeOnlyCluster().update(heroe);
            System.out.println("After");
            System.out.println(
                hybridCluster.readOnlyCluster().select(Vilain.class)
            );
        }
    }
}
