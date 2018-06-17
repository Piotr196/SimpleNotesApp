package com.simplenotesapp.simplenotesapp.service;

import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private UserRepository userRepository;

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    public String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    public User getLoggedUser() {
        String login = getLoggedUsername();
        if (login != null) {
            return userRepository.findOneByLogin(login).orElse(null);
        } else {
            return null;
        }
    }

    public Set<String> getLoggedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return ((Collection<SimpleGrantedAuthority>) authentication.getAuthorities())
                    .stream()
                    .map(SimpleGrantedAuthority::toString)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    public boolean isUserInRole(final String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(role));
    }

}
