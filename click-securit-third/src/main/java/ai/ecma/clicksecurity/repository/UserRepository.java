package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);

    UserDetails findByPhoneNumberOrEmail(String phoneNumberOrEmail, String phoneNumberOrEmail1);


}
