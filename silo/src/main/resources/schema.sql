CREATE
SEQUENCE primary_key;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS messages;
CREATE TABLE users
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE messages
(
    id   BIGINT PRIMARY KEY,
    fromId BIGINT NOT NULL,
    toId   BIGINT NOT NULL,
    text VARCHAR(255) NOT NULL
);