CREATE TABLE url_mappings (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL UNIQUE,
    short_code VARCHAR(36) NOT NULL UNIQUE
);