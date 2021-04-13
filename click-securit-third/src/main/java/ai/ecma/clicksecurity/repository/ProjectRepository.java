package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    boolean existsByName(String name);

    Optional<Project> findById(UUID uuid);


    Page<Project> findByUsersId(UUID users_id, Pageable pageable);

    @Query(nativeQuery = true, value = "select pr.* from project pr join user_project up on pr.id = up.project_id where user_id=:userId limit :size offset (:page*:size) ")
    List<Project> progectList(@Param("userId") UUID userId,@Param("page") Integer page, @Param("size") Integer size);






}

