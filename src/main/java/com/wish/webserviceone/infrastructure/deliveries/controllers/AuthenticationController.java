package com.wish.webserviceone.infrastructure.deliveries.controllers;

import com.wish.webserviceone.core.usecases.authentication.LoginService;
import com.wish.webserviceone.core.usecases.authentication.RegisterService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.CredentialsByEmail;
import com.wish.webserviceone.infrastructure.deliveries.contracts.CredentialsByHuawei;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Account;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private LoginService loginService;
    private RegisterService registerService;

    @Autowired
    public AuthenticationController(@Qualifier("authentication-login") LoginService loginService, @Qualifier("authentication-register") RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "logged_in", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "403", description = "invalid_credentials", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PostMapping("/login/email")
    public ResponseEntity<Result<Account>> loginByEmail(@RequestBody CredentialsByEmail credentials) {
        Result<Account> result = loginService.loginByEmail(credentials);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "logged_in" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            case "invalid_credentials" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "registered", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "409", description = "exists", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PostMapping("/register/email")
    public ResponseEntity<Result<Account>> registerByEmail(@RequestBody CredentialsByEmail credentials) {
        Result<Account> result = registerService.registerByEmail(credentials);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "registered" -> HttpStatus.OK;
            case "exists" -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "logged_in", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "403", description = "invalid_credentials", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PostMapping("/login/huawei/open-id")
    public ResponseEntity<Result<Account>> loginByHuaweiOpenId(@RequestBody CredentialsByHuawei credentials) {
        Result<Account> result = loginService.loginByHuaweiOpenId(credentials);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "logged_in" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            case "invalid_credentials" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "registered", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "409", description = "exists", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PostMapping("/register/huawei/open-id")
    public ResponseEntity<Result<Account>> registerByHuaweiOpenId(@RequestBody CredentialsByHuawei credentials) {
        Result<Account> result = registerService.registerByHuaweiOpenId(credentials);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "registered" -> HttpStatus.OK;
            case "exists" -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}