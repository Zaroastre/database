package io.nirahtech.libraries.database;

public record HybridCluster (
    ReadOnlyCluster readOnlyCluster,
    WriteOnlyCluster writeOnlyCluster

) {

}
