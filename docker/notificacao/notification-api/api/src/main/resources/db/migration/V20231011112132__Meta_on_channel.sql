ALTER TABLE channel
    ADD COLUMN meta JSONB NOT NULL DEFAULT '{}'::JSONB;
