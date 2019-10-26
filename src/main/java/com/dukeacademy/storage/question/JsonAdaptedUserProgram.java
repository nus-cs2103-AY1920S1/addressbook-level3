package com.dukeacademy.storage.question;

import com.dukeacademy.model.question.UserProgram;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json friendly representation of the user program model for read and write by the Jackson library.
 */
public class JsonAdaptedUserProgram {
    private final String canonicalName;
    private final String sourceCode;

    @JsonCreator
    public JsonAdaptedUserProgram(@JsonProperty("canonicalName") String canonicalName,
                                  @JsonProperty("sourceCode") String sourceCode) {
        this.canonicalName = canonicalName;
        this.sourceCode = sourceCode;
    }

    public JsonAdaptedUserProgram(UserProgram userProgram) {
        this.canonicalName = userProgram.getCanonicalName();
        this.sourceCode = userProgram.getSourceCode();
    }

    public UserProgram toModel() {
        return new UserProgram(canonicalName, sourceCode);
    }
}

