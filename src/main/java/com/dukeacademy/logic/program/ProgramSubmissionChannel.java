package com.dukeacademy.logic.program;

import com.dukeacademy.model.program.UserProgram;

/**
 * Functional interface used by ProgramSubmissionLogicManagers to allow external components to submit user programs.
 */
public interface ProgramSubmissionChannel {
    public void submitProgram(UserProgram program);
}
