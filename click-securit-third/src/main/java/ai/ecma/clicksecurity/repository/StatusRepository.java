package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findById(UUID id);
  Optional<Status> findByName(String name);

    boolean existsByName(String name);
//  boolean existsByName(StatusName name);
//  boolean existsByName(StatusName name);

}
