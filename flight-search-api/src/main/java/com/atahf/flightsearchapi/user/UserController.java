package com.atahf.flightsearchapi.user;

import com.atahf.flightsearchapi.user.UserDto.*;

import com.atahf.flightsearchapi.utils.GeneralResponse;
import com.atahf.flightsearchapi.utils.NotFoundException;
import com.atahf.flightsearchapi.utils.UserExistsException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@CrossOrigin
@RestController
@RequestMapping("/")
@Api(value = "User Endpoints documentation")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "New User adding Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 400, message = "user already exists", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @PostMapping("register")
    public ResponseEntity<GeneralResponse<String>> registerUser(
            @ApiParam(value = "New User Object", required = true) @RequestBody UserInfoDto userInfoDto
    ) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, "");
        try {
            userService.addUser(userInfoDto);
            response.setResult("User successfully Registered!");
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (UserExistsException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "New Admin adding Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 400, message = "user already exists", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @PostMapping("register-admin")
    public ResponseEntity<GeneralResponse<String>> registerAdmin(
            @ApiParam(value = "New Admin Object", required = true) @RequestBody UserInfoDto userInfoDto
    ) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, "");
        try {
            userService.addAdmin(userInfoDto);
            response.setResult("Admin successfully Registered!");
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (UserExistsException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "New User adding Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 404, message = "user not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @DeleteMapping ("delete-account")
    public ResponseEntity<GeneralResponse<String>> deleteUser(
            @ApiParam(hidden = true) Authentication authentication
    ) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, "");
        try {
            userService.deleteUser(authentication.getName());
            response.setResult("User successfully Deleted!");
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}