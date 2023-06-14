CREATE TABLE customerrxw (
    iwd SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE SEQUENCE candlestick_EURUSD_id_seq;

CREATE TABLE candlestick_EURUSD (
 id integer NOT NULL DEFAULT nextval('candlestick_EURUSD_id_seq'),
 scale character varying(255),
 ticktime TIMESTAMP NOT NULL,
 open DECIMAL(12, 6) NOT NULL,
 high DECIMAL(12, 6) NOT NULL,
 low DECIMAL(12, 6) NOT NULL,
 close DECIMAL(12, 6) NOT NULL,
 volume DECIMAL(12, 6) NOT NULL,
 PRIMARY KEY (scale, ticktime)
);

ALTER SEQUENCE candlestick_EURUSD_id_seq
OWNED BY candlestick_EURUSD.id;

CREATE INDEX candlestick_EURUSD_index ON candlestick_EURUSD (scale, ticktime);
CREATE UNIQUE INDEX candlestick_EURUSD_id ON candlestick_EURUSD (id);


CREATE SEQUENCE partitioned_EURUSD_id_seq;

CREATE TABLE partitioned_EURUSD (
 id integer NOT NULL DEFAULT nextval('partitioned_EURUSD_id_seq'),
 scale character varying(255),
 candlestick_EURUSD_id integer NOT NULL,

 PRIMARY KEY (scale, candlestick_EURUSD_id),
 FOREIGN KEY (candlestick_EURUSD_id) REFERENCES candlestick_EURUSD (id) ON DELETE SET NULL
);

ALTER SEQUENCE partitioned_EURUSD_id_seq
OWNED BY partitioned_EURUSD.id;

CREATE INDEX partitioned_EURUSD_index ON partitioned_EURUSD (scale, candlestick_EURUSD_id);
