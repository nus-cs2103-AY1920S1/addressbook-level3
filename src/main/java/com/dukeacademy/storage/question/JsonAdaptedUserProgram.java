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

    /**
     * Instantiates a new Json adapted user program.
     *
     * @param canonicalName the canonical name
     * @param sourceCode    the source code
     */
    @JsonCreator
    public JsonAdaptedUserProgram(@JsonProperty("canonicalName") String canonicalName,
                                  @JsonProperty("sourceCode") String sourceCode) {
        this.canonicalName = canonicalName;
        this.sourceCode = sourceCode;
    }

    /**
     * Instantiates a new Json adapted user program.
     *
     * @param userProgram the user program
     */
    public JsonAdaptedUserProgram(UserProgram userProgram) {
        this.canonicalName = userProgram.getCanonicalName();
        this.sourceCode = userProgram.getSourceCode();
    }

    /**
     * To model user program.
     *
     * @return the user program
     */
    public UserProgram toModel() {
        return new UserProgram(canonicalName, sourceCode);
    }
}

