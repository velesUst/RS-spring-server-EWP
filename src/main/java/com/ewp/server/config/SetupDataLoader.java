package com.ewp.server.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ewp.server.persistence.repository.user.PrivilegeDao;
import com.ewp.server.persistence.repository.user.RoleDao;
import com.ewp.server.persistence.repository.user.UserDao;
import com.ewp.server.persistence.entity.user.Privilege;
import com.ewp.server.persistence.entity.user.Role;
import com.ewp.server.persistence.entity.user.User;
import com.ewp.server.persistence.entity.analysis.EWave;
import com.ewp.server.persistence.entity.analysis.PType;
import com.ewp.server.persistence.repository.analysis.EWaveDao;
import com.ewp.server.persistence.repository.analysis.CandlestickDao;
import com.ewp.server.persistence.entity.analysis.Candlestick;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ewp.server.dto.*;

@Component
public class SetupDataLoader  implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    @Autowired
    UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PrivilegeDao privilegeDao;

    @Autowired
    EWaveDao ewaveDao;    
    @Autowired
    CandlestickDao candlestickDao;
    

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege);
        final List<Privilege> userPrivileges = Arrays.asList(readPrivilege, passwordPrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        final Role adminRole = roleDao.findByName("ROLE_ADMIN");  
        User userAdmin = userDao.findByLogin("admin");
        if (userAdmin != null) 
            alreadySetup = true; 
        else {    
            userAdmin = new User();
            userAdmin.setFirstName("Admin");
            userAdmin.setLastName("Admin");
            userAdmin.setPassword(passwordEncoder.encode("admin"));
            userAdmin.setLogin("admin");
            userAdmin.setEnabled(true);
            userAdmin.setRoles(Arrays.asList(adminRole));
            userDao.save(userAdmin);
        }

        /*Candlestick last_stick = candlestickDao.findLastBy_SeriesScale();  
        EWave pattern = new EWave();
        pattern.setPType(PType.Impulse);
        pattern.setEwaveSticks(Arrays.asList(last_stick));
        ewaveDao.save(pattern);

        EWave pattern1 = new EWave();
        pattern1.setPType(PType.LeadingDiagonal);
        pattern1.setEwaveSticks(Arrays.asList(last_stick));
        pattern1.setDecompositeEWave(Arrays.asList(pattern));
        ewaveDao.save(pattern1);*/
        alreadySetup = true;
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeDao.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeDao.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleDao.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleDao.save(role);
        }
        return role;
    }

}
