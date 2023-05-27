package com.project.transferapi.interfaces.inbound.http.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ExceptionDetails {

   protected String title;
   protected int status;
   protected String details;
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
   protected LocalDateTime timestamp;
   protected String developerMessage;

}
