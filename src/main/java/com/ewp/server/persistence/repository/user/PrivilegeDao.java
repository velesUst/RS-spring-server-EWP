package com.ewp.server.persistence.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ewp.server.persistence.entity.user.Privilege;
import com.ewp.server.persistence.entity.user.Role;

/**
 * Created by ...
 */
@Repository("privilegeDao")
public interface PrivilegeDao extends JpaRepository<Privilege, Integer> {
    
    @Query("FROM Privilege privilege WHERE privilege.name = :name")
    Privilege findByName(@Param("name") String name);

}
