package ai.ecma.clicksecurity.entity;


import ai.ecma.clicksecurity.entity.templete.AbsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String firsName, lastName, password;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;


    public User(String firsName, String lastName, String password, String email, String phoneNumber, String userName, List<Role> roles) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.roles = roles;
    }

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
