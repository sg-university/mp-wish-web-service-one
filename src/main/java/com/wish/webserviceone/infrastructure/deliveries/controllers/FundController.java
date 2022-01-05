package com.wish.webserviceone.infrastructure.deliveries.controllers;

import com.wish.webserviceone.core.usecases.fund.ManageService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Fund;
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
@RequestMapping("/api/v1/funds")
@CrossOrigin(origins = "*")
public class FundController {
    ManageService manageService;

    @Autowired
    public FundController(@Qualifier("fund-manage") ManageService manageService) {
        this.manageService = manageService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "read", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @GetMapping("")
    public ResponseEntity<Result<List<Fund>>> readAll(@RequestParam Map<String, String> filter) {
        Result<List<Fund>> result = manageService.readAll(filter);
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
    public ResponseEntity<Result<Fund>> readOneById(@PathVariable("id") UUID id) {
        Result<Fund> result = manageService.readOneById(id);
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
    public ResponseEntity<Result<Fund>> createOne(@RequestBody Fund fund) {
        Result<Fund> result = manageService.createOne(fund);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "created" -> HttpStatus.CREATED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Result<Fund>> updateOneById(@PathVariable("id") UUID id, @RequestBody Fund fundToUpdate) {
        Result<Fund> result = manageService.updateOneById(id, fundToUpdate);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "updated" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "patched", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "not_found", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Result<Fund>> patchOneById(@PathVariable("id") UUID id, @RequestBody Fund fundToPatch) {
        Result<Fund> result = manageService.patchOneById(id, fundToPatch);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "patched" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
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
    public ResponseEntity<Result<Fund>> deleteOneById(@PathVariable("id") UUID id) {
        Result<Fund> result = manageService.deleteOneById(id);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "deleted" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
