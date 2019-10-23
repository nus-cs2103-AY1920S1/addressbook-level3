package com.dukeacademy.storage.question;

import com.dukeacademy.model.question.UserProgram;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json friendly representation of the user program model for read and write by the Jackson library.
 */
public class JsonAdaptedUserProgram {
    private final String className;
    private final String sourceCode;

    @JsonCreator
    public JsonAdaptedUserProgram(@JsonProperty("className") String className,
                                  @JsonProperty("sourceCode") String sourceCode) {
        this.className = className;
        this.sourceCode = sourceCode;
    }

    public JsonAdaptedUserProgram(UserProgram userProgram) {
        this.className = userProgram.getClassName();
        this.sourceCode = userProgram.getSourceCodeAsString();
    }

    public UserProgram toModel() {
        return new UserProgram(className, sourceCode);
    }
}

