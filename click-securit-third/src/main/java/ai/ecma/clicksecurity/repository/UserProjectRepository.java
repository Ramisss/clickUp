package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.Project;
import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.entity.UserProject;
import ai.ecma.clicksecurity.entity.enums.ProjectPermissionEnum;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProjectRepository extends JpaRepository<UserProject, UUID> {


    Optional<Project> findByProjectId(UUID projectId);


    Page<Project> findUserProjectsByUserId(UUID userId, Pageable pageable);

    @Query(nativeQuery = true, value = "Select * from user_project where user_id=:userId and project_id=:projectId ORDER BY update_at LIMIT 1")
    Optional<UserProject> findByUserIdAndProjectId(UUID userId, UUID projectId);

//    Page<UserProject> findAllByUserId(UUID id, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select project_permission_enums from user_project_project_permission_enums where user_project_id=:Id")
    List<String> findByUserProjectId(UUID Id);






}
