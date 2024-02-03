package com.atahf.flightsearchapi.user;

import com.atahf.flightsearchapi.security.UserRole;

import com.atahf.flightsearchapi.user.UserDto.UserInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User Model", description = "User Model Documentation")
public class User implements UserDetails {
    @Id
    @ApiModelProperty(value = "Unique username of user object")
    private String username;
    @ApiModelProperty(value = "password of user object")
    private String password;
    @ApiModelProperty(value = "role of user object")
    private String role;
    @ApiModelProperty(value = "isAccountNonExpired field of user object")
    private Boolean isAccountNonExpired;
    @ApiModelProperty(value = "isAccountNonLocked field of user object")
    private Boolean isAccountNonLocked;
    @ApiModelProperty(value = "isCredentialsNonExpired field of user object")
    private Boolean isCredentialsNonExpired;
    @ApiModelProperty(value = "isEnabled field of user object")
    private Boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRole.valueOf(role).getGrantedAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(UserInfoDto userInfoDto) {
        this.username = userInfoDto.getUsername();
        this.password = "";
        this.role = UserRole.USER.name();
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }
}
