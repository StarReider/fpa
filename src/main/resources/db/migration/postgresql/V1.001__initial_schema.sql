CREATE TYPE transaction_type AS ENUM('debit', 'credit', 'interest');

CREATE TABLE IF NOT EXISTS Users (
    user_id SMALLSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP --ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Currencies (
    currency_code VARCHAR(10) PRIMARY KEY,
    currency_name VARCHAR(50) NOT NULL,
    exchange_rate DECIMAL(10, 6) NOT NULL, -- Exchange rate compared to base currency
    base_currency VARCHAR(10) NOT NULL -- Base currency code
);

CREATE TABLE IF NOT EXISTS Accounts (
    account_id SMALLSERIAL PRIMARY KEY,
    user_id INT,
    account_name VARCHAR(50) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.00,
    currency VARCHAR(10) NOT NULL, -- Foreign key to Currencies
    interest_rate DECIMAL(5, 2) DEFAULT 0.00, -- Interest rate as a percentage
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP, -- ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (currency) REFERENCES Currencies(currency_code)
);

CREATE TABLE IF NOT EXISTS Transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    type transaction_type NOT NULL,
    description VARCHAR(255),
    is_scheduled BOOLEAN DEFAULT FALSE,
    scheduled_date TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP, --ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);