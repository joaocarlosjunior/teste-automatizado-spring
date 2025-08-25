CREATE TABLE IF NOT EXISTS tbl_planets
(
    id    BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(50) NOT NULL,
    climate     VARCHAR(50) NOT NULL,
    terrain     VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT name_unique UNIQUE (name)
);