package com.example.springsecurity_exam.entity;

import com.example.springsecurity_exam.entity.enums.PermissionEnum;
import com.example.springsecurity_exam.entity.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "users")
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    @Column(unique = true)
    private String username;
    @ManyToMany
   private Set<Role> roles;

    private boolean active = true;
    private boolean enabled = true;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (Role role : this.roles) {
            Set<PermissionEnum> permissionEnumSet = role.getPermissionEnumSet();
            for (PermissionEnum permissionEnum : permissionEnumSet) {
                authoritySet.add(new SimpleGrantedAuthority(permissionEnum.name()));
            }
        }
        return authoritySet;
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
    public boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {

        return enabled;
    }
}
