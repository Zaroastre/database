# **Database**

## Sumary
Creation of a database(s) cluster based on CQRS.

```
                |---------------|
                | HybridCluster |
                |---------------|
                A               A
|------------------|          |-----------------|
| WriteOnlyCluster |          | ReadOnlyCluster |
|------------------|          |-----------------|
        A                           A
|----------------------|  |---------------------|
| WriteOnlyDatabase(s) |  | ReadOnlyDatabase(s) |
|----------------------|  |---------------------|
    - select                    - insert
                                - update
                                - delete

```