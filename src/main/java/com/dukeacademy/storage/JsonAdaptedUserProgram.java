package com.dukeacademy.storage;

import com.dukeacademy.model.program.UserProgram;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

class JsonAdaptedUserProgram {
    private final String className;
    private final String sourceCode;

    @JsonCreator
    public JsonAdaptedUserProgram(String className, String sourceCode) {
        this.className = className;
        this.sourceCode = sourceCode;
    }

    public JsonAdaptedUserProgram(UserProgram userProgram) {
        this.className = userProgram.getClassName();
        this.sourceCode = userProgram.getSourceCodeAsString();
    }

    @JsonValue
    public String getClassName() {
        return this.className;
    }

    @JsonValue
    public String getSourceCode() {
        return this.sourceCode;
    }

    public UserProgram toModel() {
        return new UserProgram(className, sourceCode);
    }
}

