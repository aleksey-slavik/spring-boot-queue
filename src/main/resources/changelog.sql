-- liquibase formatted sql

-- changeset oleksii.slavik:create_messages_table

CREATE TABLE IF NOT EXISTS messages (
  id SERIAL PRIMARY KEY,
  message JSONB NOT NULL
);

-- rollback

DROP TABLE messages;