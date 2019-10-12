package com.webbdealer.dms.presentation.controllers;

import com.webbdealer.dms.infrastructure.admin.config.SecurityConfig;
import com.webbdealer.dms.infrastructure.admin.persistence.entities.Account;
import com.webbdealer.dms.infrastructure.admin.persistence.entities.DmsUser;
import com.webbdealer.dms.infrastructure.admin.persistence.repositories.AccountRepository;
import com.webbdealer.dms.infrastructure.admin.persistence.repositories.DmsUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

//    @Autowired
//    private UserDetails userDetails;
    private DmsUserRepository userRepository;
    private AccountRepository accountRepository;

    public UserController(DmsUserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = "/user")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DmsUser> createUser(@Valid @RequestBody DmsUser dmsUser, Errors errors) {
//        if(errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(VehicleValidationErrorBuilder.fromBindingErrors(errors));
//        }

        DmsUser result = userRepository.findByEmail(dmsUser.getEmail());

        if(result != null) {
            System.out.println("Email already exists");
            ResponseEntity.badRequest();
        }

        PasswordEncoder encoder = SecurityConfig.passwordEncoder();
        String password = encoder.encode(dmsUser.getPassword());

        Optional<Account> optionalAccount = accountRepository.findById(40L);
        Account account = optionalAccount.get();

        DmsUser newUser = new DmsUser(
                dmsUser.getEmail(),
                password,
                dmsUser.getFirstname(),
                dmsUser.getLastname(),
                dmsUser.getPhone(),
                dmsUser.getPosition(),
                dmsUser.isAdmin());
        //newUser.setAccount(account);
        userRepository.save(newUser);

        //        Set<User> userSet = new HashSet<>();
//        userSet.add(newUser);
//
//        account.setUsers(userSet);
//
//        accountRepository.save(account);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/user")
                .buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Authentication> showAuthenticatedUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Collection authorities = auth.getAuthorities();
        System.out.println("Name: " + name);
        System.out.println(authorities);

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            String displayData = "Cast to UserDetails: " + username + "\n";


        }
        return ResponseEntity.ok(auth);
//        else {
//            String username = principal.toString();
//            return ResponseEntity.ok("String: " + username);
//        }
    }

    @GetMapping(value = "/admin/users")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Account> listUsers() {
        Optional<Account> result = accountRepository.findById(40L);
        if(result.isPresent()) {
            Account account = result.get();
            //List<User> users = account.getUsers();
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<DmsUser> showUserById(@PathVariable Long id) {
        Optional<DmsUser> result = userRepository.findById(id);
        System.out.println(result.get());

        if(result.isPresent()) {
            DmsUser dmsUser = result.get();
            return ResponseEntity.ok(dmsUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<DmsUser> updateUser(@PathVariable Long id, @RequestBody DmsUser userForm ) {
        Optional<DmsUser> result = userRepository.findById(id);


        if(result.isPresent()) {
            PasswordEncoder encoder = SecurityConfig.passwordEncoder();
            String password = encoder.encode(userForm.getPassword());
            System.out.println("New Password: " + userForm.getPassword() + "\nEncoded Password: " + password);

            DmsUser dmsUser = result.get();
            dmsUser.setPassword(password);
            userRepository.save(dmsUser);
            return ResponseEntity.ok(dmsUser);
        }
        return ResponseEntity.badRequest().build();
    }
}
