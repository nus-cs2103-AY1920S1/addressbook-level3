package com.dukeacademy.logic.problemstatement;

import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;

/**
 * The type Problem statement logic manager.
 */
public class ProblemStatementLogicManager implements ProblemStatementLogic {
    private final StandardObservable<String> problemStatement;

    /**
     * Constructor.
     */
    public ProblemStatementLogicManager() {
        this.problemStatement = new StandardObservable<>();
    }
    @Override public Observable<String> getProblemStatementObservable() {
        return this.problemStatement;
    }

    @Override public void setProblemStatementObservable(String problemStatement) {
        this.problemStatement.setValue(problemStatement);
    }
}
