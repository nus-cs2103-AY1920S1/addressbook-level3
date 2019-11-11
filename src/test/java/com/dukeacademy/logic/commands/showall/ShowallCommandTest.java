package com.dukeacademy.logic.commands.showall;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.testutil.MockQuestionsLogic;
import com.dukeacademy.testutil.TypicalQuestions;

import javafx.collections.ObservableList;

class ShowallCommandTest {
    @TempDir public Path tempFolder;

    private MockQuestionsLogic questionsLogic;
    private ApplicationState applicationState;

    @BeforeEach void initializeTest() throws LogicCreationException {
        this.questionsLogic = MockQuestionsLogic.getMockQuestionsLogicWithTypicalQuestions();
        this.applicationState = new ApplicationState();
    }

    @Test void execute() {
        ShowallCommand
            command = new ShowallCommand(questionsLogic, applicationState);

        command.execute();
        List<Question> expectedQuestions = TypicalQuestions.getTypicalQuestions();
        this.matchListData(questionsLogic.getFilteredQuestionsList(), expectedQuestions);
    }


    /**
     * Helper method to compare an observable list to a list for equality.
     * @param observableList the observable list to be compared.
     * @param questionList the question list to be compared.
     */
    private void matchListData(ObservableList<Question> observableList, List<Question> questionList) {
        if (observableList.size() != questionList.size()) {
            return;
        }

        if (observableList.size() == 0) {
            return;
        }

        IntStream.range(0, observableList.size()).mapToObj(
            i -> observableList.get(i).checkContentsEqual(questionList.get(i)))
                 .reduce((x, y) -> x && y).get();
    }
}
