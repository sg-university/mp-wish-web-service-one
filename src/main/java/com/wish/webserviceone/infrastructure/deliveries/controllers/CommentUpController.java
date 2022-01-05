package com.wish.webserviceone.infrastructure.deliveries.controllers;

import com.wish.webserviceone.core.usecases.comment.UpService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.CommentUp;
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
@RequestMapping("/api/v1/comment-ups")
@CrossOrigin(origins = "*")
public class CommentUpController {
    UpService upService;

    @Autowired
    public CommentUpController(@Qualifier("comment-up") UpService upService) {
        this.upService = upService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "read", content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "error", content = @Content(schema = @Schema(implementation = Result.class)))
    })
    @GetMapping("")
    public ResponseEntity<Result<List<CommentUp>>> readAll(@RequestParam Map<String, String> filter) {
        Result<List<CommentUp>> result = upService.readAll(filter);
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
    public ResponseEntity<Result<CommentUp>> readOneById(@PathVariable("id") UUID id) {
        Result<CommentUp> result = upService.readOneById(id);
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
    public ResponseEntity<Result<CommentUp>> createOne(@RequestBody CommentUp commentUp) {
        Result<CommentUp> result = upService.createOne(commentUp);
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
    public ResponseEntity<Result<CommentUp>> updateOneById(@PathVariable("id") UUID id, @RequestBody CommentUp commentUpToUpdate) {
        Result<CommentUp> result = upService.updateOneById(id, commentUpToUpdate);
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
    public ResponseEntity<Result<CommentUp>> patchOneById(@PathVariable("id") UUID id, @RequestBody CommentUp commentUpToPatch) {
        Result<CommentUp> result = upService.patchOneById(id, commentUpToPatch);
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
    public ResponseEntity<Result<CommentUp>> deleteOneById(@PathVariable("id") UUID id) {
        Result<CommentUp> result = upService.deleteOneById(id);
        HttpStatus httpStatus = switch (result.getStatus()) {
            case "deleted" -> HttpStatus.OK;
            case "not_found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        result.setCode(httpStatus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
