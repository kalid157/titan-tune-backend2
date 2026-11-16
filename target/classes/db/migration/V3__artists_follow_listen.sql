ALTER TABLE users ADD COLUMN IF NOT EXISTS alias VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS description VARCHAR(1000);

CREATE TABLE IF NOT EXISTS suivres (
    tracking_id            VARCHAR(255) PRIMARY KEY,
    tracking_id_follower   VARCHAR(255) NOT NULL,
    tracking_id_following  VARCHAR(255) NOT NULL,
    created_at             TIMESTAMP,
    CONSTRAINT uq_suivre UNIQUE (tracking_id_follower, tracking_id_following)
);

CREATE TABLE IF NOT EXISTS song_ecouter (
    tracking_id       VARCHAR(255) PRIMARY KEY,
    user_tracking_id  VARCHAR(255) NOT NULL,
    song_tracking_id  VARCHAR(255) NOT NULL,
    listened_at       TIMESTAMP
);