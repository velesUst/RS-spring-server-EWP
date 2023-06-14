package com.ewp.server.persistence.repository.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ewp.server.persistence.entity.user.Role;
import com.ewp.server.persistence.entity.user.User;

/**
 * Created by ...
 */
@Repository("userDao")
public interface UserDao extends JpaRepository<User, Integer>  {

    @Query("FROM User user WHERE user.login = :login")
    User findByLogin(@Param("login") String login);
    
}
