DROP TABLE IF EXISTS city;
CREATE TABLE city
(
    id bigserial,
    name character varying(30) NOT NULL,
    info character varying(30) NOT NULL
);