package com.dukeacademy.solution.compiler;

import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.exceptions.CompilerFileContentException;
import com.dukeacademy.solution.models.ClassFile;
import com.dukeacademy.solution.models.JavaFile;

public interface Compiler {
    public ClassFile compileJavaFile(JavaFile file) throws CompilerException, CompilerFileContentException;
}
