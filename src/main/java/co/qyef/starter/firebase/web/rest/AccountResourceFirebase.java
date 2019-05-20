package co.qyef.starter.firebase.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.qyef.starter.firebase.domain.Authority;
import co.qyef.starter.firebase.domain.PersistentToken;
import co.qyef.starter.firebase.domain.User;
import co.qyef.starter.firebase.repository.PersistentTokenRepository;
import co.qyef.starter.firebase.repository.UserRepository;
import co.qyef.starter.firebase.security.SecurityUtils;
import co.qyef.starter.firebase.service.MailService;
import co.qyef.starter.firebase.service.UserService;
import co.qyef.starter.firebase.web.rest.dto.UserDTO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;


import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


@RestController
@RequestMapping("/app")
public class AccountResourceFirebase {

    private final Logger log = LoggerFactory.getLogger(AccountResourceFirebase.class);

    @Inject
    private ServletContext servletContext;

    @Inject
    private ApplicationContext applicationContext;



    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private MailService mailService;


    @RequestMapping(value = "/rest/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> registerAccount(@RequestBody UserDTO userDTO, HttpServletRequest request,
                                             HttpServletResponse response) {
        User user = userRepository.getOne(userDTO.getLogin());
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(), userDTO.getFirstName(),
                    userDTO.getLastName(), userDTO.getEmail().toLowerCase(), userDTO.getLangKey());
            final Locale locale = Locale.forLanguageTag(user.getLangKey());

           // mailService.sendActivationEmail(user.getEmail(), user, locale);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/rest/activate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        User user = userService.activateRegistration(key);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }


    @RequestMapping(value = "/rest/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        User user = userService.getUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<String> roles = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            roles.add(authority.getName());
        }
        return new ResponseEntity<>(
            new UserDTO(
                user.getLogin(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLangKey(),
                roles),
            HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/account",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void saveAccount(@RequestBody UserDTO userDTO) {
        userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
    }


    @RequestMapping(value = "/rest/account/change_password",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (StringUtils.isEmpty(password)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/account/sessions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        User user = userRepository.getOne(SecurityUtils.getCurrentLogin());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
            persistentTokenRepository.findByUser(user),
            HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/account/sessions/{series}",
            method = RequestMethod.DELETE)
    @Timed
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        User user = userRepository.getOne(SecurityUtils.getCurrentLogin());
        List<PersistentToken> persistentTokens = persistentTokenRepository.findByUser(user);
        for (PersistentToken persistentToken : persistentTokens) {
            if (StringUtils.equals(persistentToken.getSeries(), decodedSeries)) {
                persistentTokenRepository.delete(persistentToken);
            }
        }
    }


}
