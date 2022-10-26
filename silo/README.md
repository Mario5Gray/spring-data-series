# R2DBC Example

This example shows what to do when using R2DBC with H2. We go through entity decoration, 
custom ID Generation, and entity tests. 
The schema can either be bootstrapped through the [ConnectionFactoryInitializer]() bean, or
a well-placed `resources/schema.sql` file.

Keep in mind that persisting new entities is flexible with Spring JDBC/R2DBC. The document on `custom id generation` 
listed below gives great details about their usage contexts.

What follows thereafter are standard Reactive test semantics such stream subscriptions and 
state verification. We will discuss closely what happens at the `save` stage, then what to
expect from the RDBMS. [H2](https://www.h2database.com/html/main.html) is used as our test database, but nothing prevents one from
dropping in a connection to PostgresSQl. A caveat that accompanies the use of RDBMS's are the
explicit dialect which is possibly best met with minimal usage. However, this example 
uses the H2 Dialect for both schema setup, and ID generation; A little refactor will be needed
to migrate to a separate DB.


## Flyaway

We like [Flyaway](https://flywaydb.org)... why, and why not? Flyway simplifies RDBMS schema management. If the goal
is to ensure proper uniformity across an organization, then Flyway will add value
to your developer cycles. Upstream DBA's can track schema changes while developers can save time by ignoring nitty-gritty details.


## Links

[Custom ID Generation](https://spring.io/blog/2021/09/09/spring-data-jdbc-how-to-use-custom-id-generation)
