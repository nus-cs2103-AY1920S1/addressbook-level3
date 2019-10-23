package com.dukeacademy.logic.program;

import com.dukeacademy.model.question.UserProgram;

/**
 * Functional interface used by ProgramSubmissionLogicManagers to allow external components to submit user programs.
 */
public interface UserProgramChannel {
    public UserProgram getProgram();
}
