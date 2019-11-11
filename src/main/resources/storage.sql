PRAGMA encoding = "UTF-8";

CREATE TABLE IF NOT EXISTS settings (
	id         INTEGER PRIMARY KEY,
	password         BLOB NOT NULL,
	updated_at       TEXT NOT NULL,
	last_login_at    TEXT NOT NULL,
	autolock_minutes INTEGER DEFAULT 0,
	hide_passwords   INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS entries (
	id         INTEGER PRIMARY KEY,
	updated_at TEXT NOT NULL,
	title      TEXT NOT NULL,
	username   TEXT,
	password   BLOB NOT NULL,
	url        TEXT,
	favicon    BLOB,
	note       TEXT
);
