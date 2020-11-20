CREATE SEQUENCE IF NOT EXISTS offers_id_seq;
CREATE TABLE IF NOT EXISTS offers
(
    id integer NOT NULL DEFAULT nextval('offers_id_seq'::regclass),
    date timestamp without time zone,
    name text,
    author text,
    text text,
    CONSTRAINT offers_id_pk PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS photo_id_seq;
CREATE TABLE IF NOT EXISTS photo
(
    id integer NOT NULL DEFAULT nextval('photo_id_seq'::regclass),
    CONSTRAINT photo_id_pk PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS cities_id_seq;
CREATE TABLE public.cities
(
    id integer NOT NULL DEFAULT nextval('cities_id_seq'::regclass),
    name text
);

CREATE SEQUENCE IF NOT EXISTS candidates_id_seq;
CREATE TABLE public.candidates
(
    id integer NOT NULL DEFAULT nextval('candidates_id_seq'::regclass),
    date timestamp without time zone,
    name text,
    description text,
    photo integer,
    city integer,
    CONSTRAINT candidates_id_pk PRIMARY KEY (id),
    CONSTRAINT candidates_photo_fk FOREIGN KEY (photo)
        REFERENCES public.photo (id)
);