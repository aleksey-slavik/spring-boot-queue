-- liquibase formatted sql

-- changeset oleksii.slavik:1

CREATE TABLE IF NOT EXISTS messages (
  id SERIAL PRIMARY KEY,
  message JSONB NOT NULL
);

-- rollback DROP TABLE messages;