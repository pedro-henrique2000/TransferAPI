package com.project.transferapi.interfaces.inbound.http.handler;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {

   private Map<String, String> violations;

}
