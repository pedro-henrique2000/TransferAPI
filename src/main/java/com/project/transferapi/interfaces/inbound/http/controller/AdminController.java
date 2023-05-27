package com.project.transferapi.interfaces.inbound.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Management", description = "Management Endpoints")
public class AdminController {

   @GetMapping
   @PreAuthorize("hasAuthority('admin:read')")
   @Operation(
         description = "Admin GET Endpoint",
         summary = "Admin GET Endpoint",
         responses = {
               @ApiResponse(
                     description = "Success Response",
                     responseCode = "200"
               ),
               @ApiResponse(
                     description = "Unauthorized / Invalid Token",
                     responseCode = "403"
               )
         })
   public String get() {
      return "GET:: admin controller";
   }

   @PostMapping
   @PreAuthorize("hasAuthority('admin:create')")
   @Operation(
         description = "Admin POST Endpoint",
         summary = "Admin POST Endpoint",
         responses = {
               @ApiResponse(
                     description = "Success Response",
                     responseCode = "200"
               ),
               @ApiResponse(
                     description = "Unauthorized / Invalid Token",
                     responseCode = "403"
               )
         })
   public String post() {
      return "POST:: admin controller";
   }

   @PutMapping
   @PreAuthorize("hasAuthority('admin:update')")
   @Operation(
         description = "Admin PUT Endpoint",
         summary = "Admin PUT Endpoint",
         responses = {
               @ApiResponse(
                     description = "Success Response",
                     responseCode = "200"
               ),
               @ApiResponse(
                     description = "Unauthorized / Invalid Token",
                     responseCode = "403"
               )
         })
   public String put() {
      return "PUT:: admin controller";
   }

   @DeleteMapping
   @PreAuthorize("hasAuthority('admin:delete')")
   @Operation(
         description = "Admin DELETE Endpoint",
         summary = "Admin DELETE Endpoint",
         responses = {
               @ApiResponse(
                     description = "Success Response",
                     responseCode = "200"
               ),
               @ApiResponse(
                     description = "Unauthorized / Invalid Token",
                     responseCode = "403"
               )
         })
   public String delete() {
      return "DELETE:: admin controller";
   }

}
