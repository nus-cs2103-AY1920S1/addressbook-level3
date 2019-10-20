package com.dukeacademy.storage;

import com.dukeacademy.model.program.UserProgram;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class JsonAdaptedUserProgram {
    private final String className;
    private final String sourceCode;

    @JsonCreator
    public JsonAdaptedUserProgram(@JsonProperty("className") String className, @JsonProperty("sourceCode") String sourceCode) {
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

