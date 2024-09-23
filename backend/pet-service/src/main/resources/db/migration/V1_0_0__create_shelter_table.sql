DROP TABLE IF EXISTS shelter CASCADE;
CREATE TABLE shelter
(
    id        uuid      NOT NULL UNIQUE PRIMARY KEY,
    name      TEXT      NOT NULL,
    address   TEXT      NOT NULL,
    owner_id  uuid      NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
