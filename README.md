# spring-data-series

An informal demonstration of R2DBC and Kafka Streams with Java17 and Spring Boot 2.7.x.

## From JDBC to R2DBC

First, start with a data silo - call it your typical RDBMS. We love RDBMS's - they're everywher and everyone knows em'. Reactive access to your RDBMS _can_ can offer increased data crunching flexibility. On one hand, everything is a stream; operators are standing by to `map`, `flatmap`, `filter`, and `groupBy` your table data. On the other hand, Configuring R2DBC access with Spring Boot becomes an exercise in remembering nothing more than seperate configuration properites or none at all if you wish to use H2.

The guide under [silo]() gives a brief example upon which a developer may re-use as a low barrier entry to R2DBC. 

### Explore Schema Setup

Simply drop in your schema setup with `resources/schema.sql`. Alternately or Additionaly you may opt into the [ConnectionFactoryInitializer]() class which lets you make house-keeping decisions such as schema-setup, data population, and data cleanup.

### Explore Domain Decoration

Standard Spring Data annotations occur here. Some things like specifying a column or table can be managed through these decorations.
Because the use of `record` classes - which are immutable - we also perform stateless operations upon insertion. Seeing that we cannot just _change_ a value on the target entity, we will use something [BeforeConvertCallback]() that can give us a new object while ensuring before-write changes are made.

### Not Data without an outlet

We need an outlet for the data, so choose Kafka and/or REST.
Currently, this demo lands with a REST endpoint for exposing the data. The next 'topic' will have us sending our data directly to Kafka upon ingest - though we might be tempted to skip DBMS altogether given Kafka's persistence. However this example will use REST endpoints to _feed_ Kafka streams that expose another layer of context sensative information on our domain.

## Enter Kafka

Such data can be published to Kafka streams, where a series of processes can transform the data with contextual relevance. Setup and connect to Kafka is also plain in simple with Spring Boot. 

### First, launch Kafka

You're already operating with a cloud provider, or have an installation - use the docker compose configuration to launch a cluster. 

