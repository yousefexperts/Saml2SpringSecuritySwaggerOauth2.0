package co.qyef.starter.firebase.repository;


import co.qyef.starter.firebase.domain.User;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gismat.test.web.rest.vm.ManagedUserVM;



import java.util.List;

import javax.validation.Valid;


public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("select u from User u where u.activationKey = ?1")
    User getUserByActivationKey(String activationKey);
    
    @Query("select u from User u where u.activated = false and u.createdDate > ?1")
    List<User> findNotActivatedUsersByCreationDateBefore(DateTime dateTime);

    User findByLogin(String login);
    
    User findByEmailIgnoreCase(String email);
    
    User save(@Valid ManagedUserVM managedUserVM);


    
    
} 
