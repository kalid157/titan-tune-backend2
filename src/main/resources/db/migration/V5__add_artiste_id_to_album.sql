-- Ajout de la colonne pour lier l'album à l'artiste
ALTER TABLE albums ADD COLUMN IF NOT EXISTS artiste_tracking_id VARCHAR(36);