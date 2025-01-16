package com.openclassrooms.starterjwt.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  UserRepository userRepository;

  UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

    UserDetailsImpl userDetails = new UserDetailsImpl();

    userDetails.setUsername(user.getEmail());
    userDetails.setFirstName(user.getFirstName());
    userDetails.setLastName(user.getLastName());
    userDetails.setId(user.getId());
    userDetails.setPassword(user.getPassword());
    userDetails.setAdmin(user.isAdmin());
    
    return userDetails;
  }

}
