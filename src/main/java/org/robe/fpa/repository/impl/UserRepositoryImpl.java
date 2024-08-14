package org.robe.fpa.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.model.User;
import org.robe.fpa.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserMapper userMapper;
    
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.of(namedParameterJdbcTemplate.queryForObject(Queries.FIND_USER_BY_NAME, Map.of("username", username), userMapper));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.of(namedParameterJdbcTemplate.queryForObject(Queries.FIND_USER_BY_EMAIL, Map.of("email", email), userMapper));
    }

    @Override
    public long save(User user) {
        if(user.getUserId() == null) {
            SqlParameterSource parameterSource = prepareUserForInsert(user);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            namedParameterJdbcTemplate.update(Queries.CREATE_USER, parameterSource, keyHolder, new String[] { "user_id" });
            return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).orElse(null);
        } else {
            SqlParameterSource parameterSource = prepareUserForUpdate(user);
            namedParameterJdbcTemplate.update(Queries.UPDATE_USER, parameterSource);
            return user.getUserId();
        }
    }
    
    @Override
    public Optional<User> findById(long id) {
        return Optional.of(namedParameterJdbcTemplate.queryForObject(Queries.FIND_USER_BY_ID, Map.of("id", id), userMapper));
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query(Queries.FIND_ALL_USERS, userMapper);
    }

    @Override
    public void deleteUser(Long userId) {
        namedParameterJdbcTemplate.update(Queries.DELETE_USER_BY_ID, Map.of("id", userId));
    }

    private SqlParameterSource prepareUserForInsert(User user) {
        return new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password_hash", user.getPasswordHash())
                .addValue("email", user.getEmail());
    }
    
    private SqlParameterSource prepareUserForUpdate(User user) {
        return ((MapSqlParameterSource)prepareUserForInsert(user)).addValue("id", user.getUserId());
    }
}
