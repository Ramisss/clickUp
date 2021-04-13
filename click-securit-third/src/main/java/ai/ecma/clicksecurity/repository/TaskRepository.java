package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByProjectId(UUID project_id);

    Page<Task> findByProjectId(UUID project_id, Pageable pageable);

    Optional<Task> findById(UUID uuid);

}
