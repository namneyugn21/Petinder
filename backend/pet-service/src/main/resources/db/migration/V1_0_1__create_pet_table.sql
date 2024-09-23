CREATE TYPE STATUS AS ENUM ('FREE', 'ADOPTED', 'BOOKED'); -- pet's status
CREATE TYPE AGE AS ENUM ('BABY', 'YOUNG', 'ADULT', 'SENIOR'); -- pet's age

DROP TABLE IF EXISTS pet CASCADE;
CREATE TABLE pet
(
    id                 uuid             NOT NULL UNIQUE PRIMARY KEY,
    name               TEXT             NOT NULL,
    status             STATUS           NOT NULL DEFAULT 'FREE',
    picture            TEXT,
    description        TEXT             NOT NULL DEFAULT '', -- making create ts_vector easier
    age                AGE              NOT NULL DEFAULT 'BABY',
    weight             DOUBLE PRECISION NOT NULL,
    breed              TEXT             NOT NULL,
    fur_color          TEXT             NOT NULL,
    eye_color          TEXT             NOT NULL,
    text_search_vector tsvector         NOT NULL,            -- update by update_text_search_trigger()
    create_at          TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at          TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    shelter_id         uuid             NOT NULL REFERENCES shelter (id) ON DELETE CASCADE
);

-- Trigger to update text search vector
CREATE OR REPLACE FUNCTION update_text_search_trigger() RETURNS TRIGGER AS
$$
BEGIN
    NEW.text_search_vector := to_tsvector(
            NEW.status
                || ' '
                || NEW.age
                || ' '
                || NEW.breed
                || ' '
                || NEW.fur_color
                || ' '
                || NEW.eye_color
                || ' '
                || NEW.description
                              );
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER text_search_vector_update
    BEFORE INSERT OR UPDATE
    ON pet
    FOR EACH ROW
EXECUTE FUNCTION update_text_search_trigger();
