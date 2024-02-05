package com.atahf.flightsearchapi.user.UserDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User Info Model", description = "User Info Model Documentation")
public class UserInfoDto {
    @ApiModelProperty(value = "username of user object")
    private String username;
    @ApiModelProperty(value = "password of user object")
    private String password;
}
