CREATE SEQUENCE IF NOT EXISTS offers_id_seq;
CREATE TABLE IF NOT EXISTS offers
(
    id integer NOT NULL DEFAULT nextval('offers_id_seq'::regclass),
    date timestamp without time zone,
    name text,
    author text,
    text text,
    CONSTRAINT offers_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS candidates_id_seq;
CREATE TABLE IF NOT EXISTS candidates
(
    id integer NOT NULL DEFAULT nextval('candidates_id_seq'::regclass),
    date timestamp without time zone,
    name text,
    description text,
    CONSTRAINT candidates_pkey PRIMARY KEY (id)
);