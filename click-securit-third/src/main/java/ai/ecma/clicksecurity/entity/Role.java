package ai.ecma.clicksecurity.entity;

import ai.ecma.clicksecurity.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    // GrantedAuthority is about what you are allowed to do in the context of an application
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;


    @Override
    public String getAuthority() {
        return roleName.name();
    }


}
