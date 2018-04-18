package com.gmail.buckartz.roomreservation.authorization;

import com.gmail.buckartz.roomreservation.dao.authorization.AuthorityRepository;
import com.gmail.buckartz.roomreservation.domain.employee.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AppUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authority authority = authorityRepository.findFirstByLogin(username);
        UserDetails userDetails = User.withUsername(authority.getLogin())
                .password(authority.getPassword())
                .roles("default")
                .build();
        return userDetails;
    }
}
