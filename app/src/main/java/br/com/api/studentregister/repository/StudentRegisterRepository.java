package br.com.api.studentregister.repository;


import br.com.api.studentregister.model.StudentRegisterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRegisterRepository extends JpaRepository<StudentRegisterModel, UUID> {

    @Query(value = "select * from student_register where status_registration_enum in ('ACTIVE') ", nativeQuery = true)
    List<StudentRegisterModel> searchStudentsWithRegistrationStatusActive();

    @Query(value = "select * from student_register where status_registration_enum in ('INACTIVE')", nativeQuery = true)
    List<StudentRegisterModel> searchStudentsWithRegistrationStatusInactive();

    Optional<StudentRegisterModel> findByCpf(String cpf);

    @Query(value = "select * from student_register where status_registration_enum in ('ACTIVE') ", nativeQuery = true)
    Page<StudentRegisterModel> searchStudentsWithRegistrationStatusActivePageable(Pageable pageable);
}
