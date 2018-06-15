CREATE TABLE public.users (
	id bigserial  PRIMARY KEY,
	login varchar(40) UNIQUE NOT NULL,
	password char(64) NOT NULL,
	name varchar(30),
	surname varchar(100)
);

CREATE TABLE public.notes (
	id bigserial PRIMARY KEY,
	title varchar(100) NOT NULL,
	content varchar(1000) NOT NULL,
	created_time timestamptz NOT NULL,
	modified_time timestamptz NOT NULL
);

CREATE TABLE public.user_notes (
	id bigserial PRIMARY KEY,
	user_id bigint REFERENCES users(id) NOT NULL,
	note_id bigint REFERENCES notes(id) NOT NULL,
	UNIQUE(user_id, note_id)
);

