package com.webbdealer.dms.infrastructure.admin.persistence.services;

import com.webbdealer.dms.infrastructure.admin.persistence.DmsUserDetails;
import com.webbdealer.dms.infrastructure.admin.persistence.entities.DmsUser;
import com.webbdealer.dms.infrastructure.admin.persistence.repositories.DmsUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * This provides a UserDetail for the application
 *
 *
 */
@Transactional
public class DmsUserDetailsService implements UserDetailsService {

    private DmsUserRepository dmsUserRepository;

    public DmsUserDetailsService(DmsUserRepository dmsUserRepository) {
        this.dmsUserRepository = dmsUserRepository;
    }

    /**
     * <p>Loads the UserDetails from the username provided.
     * </p>
     * @param username the user's submitted username
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Running the loadByUsername (User Details Service)");

        try {
            final DmsUser user = this.dmsUserRepository.findByEmail(username);
            System.out.println("Loading the UserDetails");

            if(user != null) {
                System.out.println(user.getEmail());

                DmsUserDetails userDetails = new DmsUserDetails(
                        user.getEmail(),
                        user.getPassword(),
                        user.isActive(),
                        user.getAccount().isActive(),
                        true,
                        true,
                        getAuthorities(user)
                );

                userDetails.setFirstname(user.getFirstname());
                userDetails.setLastname(user.getLastname());

                Long accountId = user.getAccount().getId();
                userDetails.setAccountId(accountId);
                return userDetails;
            }
            System.out.println("User not found!");
        } catch(Exception e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(DmsUser dmsUser) {
        String[] userRoles = dmsUser.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//
//        System.out.println("Privileges");
//        System.out.println(roles);
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private List<String> getPrivileges(Collection<Role> roles) {
//
//        List<String> privileges = new ArrayList<>();
//        List<Privilege> collection = new ArrayList<>();
//        for (Role role : roles) {
//            collection.addAll(role.getPrivileges());
//        }
//        for (Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
}
