package com.wish.webserviceone.infrastructure.deliveries.controllers;

import com.wish.webserviceone.core.usecases.post.UpService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.PostUp;
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
@RequestMapping("/api/v1/post-ups")
@CrossOrigin(origins = "*")
public class PostUpController {
    UpService upService;

    @Autowired
    public PostUpController(@Qualifier("post-up") UpService upService) {
        this.upService = upService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "read", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @GetMapping("")
    public ResponseEntity<Result<List<PostUp>>> readAll(@RequestParam Map<String, String> filter) {
        Result<List<PostUp>> result = upService.readAll(filter);
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
    public ResponseEntity<Result<PostUp>> readOneById(@PathVariable("id") UUID id) {
        Result<PostUp> result = upService.readOneById(id);
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
    public ResponseEntity<Result<PostUp>> createOne(@RequestBody PostUp postUp) {
        Result<PostUp> result = upService.createOne(postUp);
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
    public ResponseEntity<Result<PostUp>> updateOneById(@PathVariable("id") UUID id, @RequestBody PostUp postUpToUpdate) {
        Result<PostUp> result = upService.updateOneById(id, postUpToUpdate);
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
    public ResponseEntity<Result<PostUp>> patchOneById(@PathVariable("id") UUID id, @RequestBody PostUp postUpToPatch) {
        Result<PostUp> result = upService.patchOneById(id, postUpToPatch);
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
    public ResponseEntity<Result<PostUp>> deleteOneById(@PathVariable("id") UUID id) {
        Result<PostUp> result = upService.deleteOneById(id);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "deleted" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
