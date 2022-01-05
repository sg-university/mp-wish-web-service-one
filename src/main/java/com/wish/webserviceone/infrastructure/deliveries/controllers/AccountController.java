package com.wish.webserviceone.infrastructure.deliveries.controllers;

import com.wish.webserviceone.core.usecases.account.ManageService;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
    ManageService manageService;

    @Autowired
    public AccountController(@Qualifier("account-manage") ManageService manageService) {
        this.manageService = manageService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "read", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @GetMapping("")
    public ResponseEntity<Result<List<Account>>> readAll(@RequestParam Map<String, String> filter) {
        Result<List<Account>> result = manageService.readAll(filter);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "read" -> HttpStatus.OK;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "read", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Result<Account>> readOneById(@PathVariable("id") UUID id) {
        Result<Account> result = manageService.readOneById(id);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "read" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PostMapping("")
    public ResponseEntity<Result<Account>> createOne(@RequestBody Account account) {
        Result<Account> result = manageService.createOne(account);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "created" -> HttpStatus.CREATED;
            case "exists" -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "409", description = "exists", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<Account>> updateOneById(@PathVariable("id") UUID id, @RequestBody Account accountToUpdate) {
        Result<Account> result = manageService.updateOneById(id, accountToUpdate);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "updated" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            case "exists" -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "patched", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "409", description = "exists", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Result<Account>> patchOneById(@PathVariable("id") UUID id, @RequestBody Account accountToPatch) {
        Result<Account> result = manageService.patchOneById(id, accountToPatch);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "patched" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            case "exists" -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Account>> deleteOneById(@PathVariable("id") UUID id) {
        Result<Account> result = manageService.deleteOneById(id);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "deleted" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
