CREATE TABLE IF NOT EXISTS credit_cards
(
    id           UUID PRIMARY KEY   NOT NULL,
    card_number  VARCHAR(16) UNIQUE NOT NULL,
    credit_funds FLOAT8,
    credit_limit FLOAT8,
    debit_funds  FLOAT8,
    blocked   BOOLEAN
);

CREATE TABLE IF NOT EXISTS debit_cards
(
    id          UUID PRIMARY KEY   NOT NULL,
    card_number VARCHAR(16) UNIQUE NOT NULL,
    balance     FLOAT8,
    blocked  BOOLEAN
);