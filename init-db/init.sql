CREATE DATABASE user_db;
CREATE DATABASE giftcard_db;

\c giftcard_db
CREATE TABLE IF NOT EXISTS pt_giftcards (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    amount NUMERIC(20,2) NOT NULL,
    creation_date TIMESTAMP,
    expiration_date TIMESTAMP,
    status VARCHAR(50),
    issue_date TIMESTAMP,
    redeem_date TIMESTAMP
);