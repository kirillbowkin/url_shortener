CREATE TABLE urls (
    short_url TEXT PRIMARY KEY,
    url TEXT NOT NULL,
    count BIGSERIAL,
    unique(url)
);