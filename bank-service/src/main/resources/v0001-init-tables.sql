CREATE TABLE IF NOT EXISTS credit_cards (
 id UUID PRIMARY KEY NOT NULL,
 balance FLOAT8,
 card_number VARCHAR(16) NOT NULL,
 credit_funds FLOAT8,
 credit_limit FLOAT8,
 debit_funds FLOAT8,
 is_blocked BOOLEAN
);