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
}
