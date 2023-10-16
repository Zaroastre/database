package io.nirahtech.libraries.database;

sealed interface AccessMode permits ReadOnly, WriteOnly {
    
}
