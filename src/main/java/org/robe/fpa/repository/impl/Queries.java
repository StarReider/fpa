package org.robe.fpa.repository.impl;

public class Queries {
    private Queries() {}
    
    public static final String CREATE_USER = 
        "INSERT INTO Users(username, password_hash, email) " +
        "VALUES(:username, :password_hash, :email)";
    
    public static final String UPDATE_USER = 
        "UPDATE Users " +
        "SET username = :username, password_hash = :password_hash, email = :email, updated_at = now() " +
        "WHERE user_id = :id";
    
    public static final String FIND_USER_BY_ID = 
        "SELECT user_id, username, password_hash, email, created_at, updated_at " +
        "FROM Users " + 
        "WHERE user_id = :id";
    
    public static final String FIND_USER_BY_NAME = 
        "SELECT user_id, username, password_hash, email, created_at, updated_at " +
        "FROM Users " + 
        "WHERE username = :username";
    
    public static final String FIND_USER_BY_EMAIL = 
        "SELECT user_id, username, password_hash, email, created_at, updated_at " +
        "FROM Users " + 
        "WHERE email = :email";
    
    public static final String FIND_ALL_USERS = 
        "SELECT user_id, username, password_hash, email, created_at, updated_at " +
        "FROM Users";
    
    public static final String DELETE_USER_BY_ID = 
        "DELETE FROM Users " +
        "WHERE user_id = :id";
    
    public static final String FIND_ALL_ACCOUNTS = 
        "SELECT account_id, user_id, account_name, type, balance, currency, interest_rate, created_at, updated_at " +
        "FROM Accounts";
    
    public static final String CREATE_ACCOUNT = 
        "INSERT INTO Accounts(user_id, account_name, type, balance, currency, interest_rate) " +
        "VALUES(:user_id, :account_name, :type, :balance, :currency, :interest_rate)";
    
    public static final String UPDATE_ACCOUNT = 
        "UPDATE Accounts " +
        "SET user_id = :user_id, account_name = :account_name, type = :type, balance = :balance, currency = :currency, updated_at = now() " +
        "WHERE account_id = :id";
    
    public static final String DELETE_ACCOUNT_BY_ID = 
        "DELETE FROM Accounts " +
        "WHERE account_id = :id";
    
    public static final String FIND_ACCOUNT_BY_ID = 
        "SELECT account_id, user_id, account_name, type, balance, currency, interest_rate, created_at, updated_at " +
        "FROM Accounts " + 
        "WHERE account_id = :id";
    
    public static final String FIND_ALL_CURRENCIES = 
        "SELECT currency_code, currency_name, exchange_rate, base_currency, created_at " +
        "FROM Currencies";
    
    public static final String FIND_CURRENCY_BY_CODE = 
        "SELECT currency_code, currency_name, exchange_rate, base_currency, created_at " +
        "FROM Currencies " + 
        "WHERE currency_code = :code";
    
    public static final String CREATE_CURRENCY = 
        "INSERT INTO Currencies(currency_code, currency_name, exchange_rate, base_currency) " +
        "VALUES(:currency_code, :currency_name, :exchange_rate, :base_currency)";
    
    public static final String DELETE_CURRENCY_BY_CODE = 
        "DELETE FROM Currencies " +
        "WHERE currency_code = :code";
    
    public static final String DELETE_ALL_CURRENCIES = 
        "TRUNCATE TABLE Currencies CASCADE";
    
    public static final String FIND_TRANSACTION_BY_ID = 
        "SELECT transaction_id, status, source_account_id, target_account_id, amount, type, description, is_scheduled, scheduled_date, created_at, updated_at " +
        "FROM Transactions " + 
        "WHERE transaction_id = :id";
    
    public static final String FIND_TRANSACTION_BY_ACCOUNT_ID = 
        "SELECT transaction_id, status, source_account_id, target_account_id, amount, type, description, is_scheduled, scheduled_date, created_at, updated_at " +
        "FROM Transactions " + 
        "WHERE source_account_id = :id OR target_account_id = :id";
    
    public static final String CREATE_TRANSACTION = 
        "INSERT INTO Transactions(source_account_id, target_account_id, amount, type, description, is_scheduled, scheduled_date) " +
        "VALUES(:source_account_id, :target_account_id, :amount, :type, :description, :is_scheduled, :scheduled_date)";
    
    public static final String UPDATE_TRANSACTION = 
        "UPDATE Transactions " +
        "SET status = :status, source_account_id = :source_account_id, target_account_id = :target_account_id, amount = :amount, type = :type, description = :description, is_scheduled =:is_scheduled, scheduled_date = :scheduled_date, updated_at = now() " +
        "WHERE transaction_id = :id";
    
    public static final String FIND_ALL_TRANSACTIONS = 
        "SELECT transaction_id, status, source_account_id, target_account_id, amount, type, description, is_scheduled, scheduled_date, created_at, updated_at " +
        "FROM Transactions";
    
    public static final String FIND_SCHEDULED_TRANSACTIONS = 
        "SELECT transaction_id, status, source_account_id, target_account_id, amount, type, description, is_scheduled, scheduled_date, created_at, updated_at " +
        "FROM Transactions " + 
        "WHERE is_scheduled IS TRUE AND scheduled_date::date <= CURRENT_DATE AND status = 'PENDING'";
    
    public static final String DELETE_TRANSACTION_BY_ID = 
        "DELETE FROM Transactions " +
        "WHERE transaction_id = :id";
}
