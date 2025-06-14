CREATE TABLE activity_stats(
    id bigint PRIMARY KEY,
    stats VARCHAR(1024) NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE NOT NULL
);