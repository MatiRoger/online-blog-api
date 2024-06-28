package com.rogerdev.blog_service.domain.usersec;

import com.rogerdev.blog_service.domain.usersec.dto.AuthReqDTO;
import com.rogerdev.blog_service.domain.usersec.dto.AuthResDTO;
import com.rogerdev.blog_service.infra.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSec user = userRepo.getUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new  User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNotLocked(),
                user.isAccountNotExpired(),
                user.isCredentialNotExpired(),
                authorities
        );
    }
    public Authentication authByUsernameAndPassword (String username, String plainPass) {

        UserDetails user = this.loadUserByUsername(username);
        if (user == null || !passwordEncoder.matches(plainPass, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );

    }

    public AuthResDTO login(AuthReqDTO authRequest) {

        String username = authRequest.username();

        Authentication auth = this.authByUsernameAndPassword(username, authRequest.password());

        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtUtils.generateToken(auth);

        return new AuthResDTO("Login successfully", username, token);
    }
}
