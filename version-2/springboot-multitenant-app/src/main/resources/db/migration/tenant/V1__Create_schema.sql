
CREATE TABLE product(
    id      bigint NOT NULL,
    name    varchar(255),
    price   numeric(19,2),
    CONSTRAINT pk_product PRIMARY KEY (id)
) WITH (OIDS = FALSE)
TABLESPACE pg_default;

ALTER TABLE product OWNER TO postgres;

CREATE SEQUENCE hibernate_sequence START 1;
