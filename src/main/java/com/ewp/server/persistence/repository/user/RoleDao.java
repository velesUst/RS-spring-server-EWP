package com.ewp.server.persistence.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ewp.server.persistence.entity.user.Role;

/**
 * Created by ...
 */
@Repository("roleDao")
public interface RoleDao extends JpaRepository<Role, Integer>  {
    
    @Query("FROM Role role WHERE role.name = :name")
    Role findByName(@Param("name") String name);

}
