package ai.ecma.clicksecurity.repository;

import ai.ecma.clicksecurity.entity.Role;
import ai.ecma.clicksecurity.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleName(RoleName roleName);
}
