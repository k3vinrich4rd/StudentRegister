package br.com.api.studentregister.repository;

import br.com.api.studentregister.model.StudentRegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRegisterUserRepository extends JpaRepository<StudentRegisterUser, UUID> {
    StudentRegisterUser findByUsername(String username);
}
