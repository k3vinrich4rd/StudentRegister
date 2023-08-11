package br.com.api.studentregister.service;


import br.com.api.studentregister.repository.StudentRegisterUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentRegisterUserDetailsService implements UserDetailsService {

    private final StudentRegisterUserRepository studentRegisterUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(studentRegisterUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("StudentRegister User not found"));
    }
}
