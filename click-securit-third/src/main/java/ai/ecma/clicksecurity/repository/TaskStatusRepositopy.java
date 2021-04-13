package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;
import java.util.UUID;

public interface  TaskStatusRepositopy extends JpaRepository<TaskStatus, UUID> {

    Optional<TaskStatus> deleteByTaskIdAndStatusId(UUID taskId,UUID statusId);


}
