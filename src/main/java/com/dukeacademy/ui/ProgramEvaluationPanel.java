package com.dukeacademy.ui;

import java.util.List;

import com.dukeacademy.model.program.TestCaseResult;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.observable.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class ProgramEvaluationPanel extends UiPart<Region> {
    private static final String FXML = "ProgramEvaluationPanel.fxml";

    @FXML
    private ListView<TestCaseResult> evaluationListView;

    /**
     * Instantiates a new Question list panel.
     *
     * @param
     */
    public ProgramEvaluationPanel(Observable<TestResult> testResultObservable) {
        super(FXML);
        testResultObservable.addListener(result -> {
            if (result != null) {
                List<TestCaseResult> testCaseResults = result.getResults();
                ObservableList<TestCaseResult> observableTestCaseResults =
                        FXCollections.observableArrayList(testCaseResults);

                evaluationListView.setItems(observableTestCaseResults);
                evaluationListView.setCellFactory(cell -> new EvaluationListViewCell());

            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Question} using a {@code QuestionCard}.
     */
    class EvaluationListViewCell extends ListCell<TestCaseResult> {
        @Override
        protected void updateItem(TestCaseResult testCaseResult, boolean empty) {
            super.updateItem(testCaseResult, empty);

            if (empty || testCaseResult == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProgramEvaluationCard(testCaseResult, getIndex() + 1).getRoot());
            }
        }
    }

}
