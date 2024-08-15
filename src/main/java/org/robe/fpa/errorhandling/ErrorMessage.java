package org.robe.fpa.errorhandling;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 5587413684719834681L;

    private String message;
    private ErrorMessage details;
}
