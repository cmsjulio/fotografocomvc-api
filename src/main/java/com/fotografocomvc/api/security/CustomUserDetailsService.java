package com.fotografocomvc.api.security;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Role;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private BaseUserRepository baseUserRepository;

    @Autowired
    public CustomUserDetailsService(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser baseUser = baseUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return new User(baseUser.getUsername(), baseUser.getPassword(), mapRolesToAuthorities(baseUser.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
