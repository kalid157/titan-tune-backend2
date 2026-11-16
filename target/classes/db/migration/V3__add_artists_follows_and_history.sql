-- 1. Ajout des colonnes pour les artistes dans la table users
ALTER TABLE users ADD COLUMN IF NOT EXISTS alias VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS description VARCHAR(1000);

-- 2. Création de la table des abonnements (Suivre)
CREATE TABLE IF NOT EXISTS suivres (
    tracking_id VARCHAR(36) PRIMARY KEY,
    tracking_id_follower VARCHAR(36) NOT NULL,
    tracking_id_following VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_suivre UNIQUE (tracking_id_follower, tracking_id_following),
    FOREIGN KEY (tracking_id_follower) REFERENCES users(tracking_id) ON DELETE CASCADE,
    FOREIGN KEY (tracking_id_following) REFERENCES users(tracking_id) ON DELETE CASCADE
);

-- 3. Création de la table d'historique d'écoute (SongEcouter)
CREATE TABLE IF NOT EXISTS song_ecouter (
    tracking_id VARCHAR(36) PRIMARY KEY,
    user_tracking_id VARCHAR(36) NOT NULL,
    song_tracking_id VARCHAR(36) NOT NULL,
    listened_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_tracking_id) REFERENCES users(tracking_id) ON DELETE CASCADE,
    FOREIGN KEY (song_tracking_id) REFERENCES songs(tracking_id) ON DELETE CASCADE
);

-- Index pour accélérer les recherches
CREATE INDEX IF NOT EXISTS idx_suivre_follower ON suivres(tracking_id_follower);
CREATE INDEX IF NOT EXISTS idx_suivre_following ON suivres(tracking_id_following);
CREATE INDEX IF NOT EXISTS idx_ecouter_user ON song_ecouter(user_tracking_id);