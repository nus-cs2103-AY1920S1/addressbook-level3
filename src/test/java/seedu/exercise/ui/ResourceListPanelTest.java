package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.CommonTestData.EXERCISE_LABEL_ID;
import static seedu.exercise.testutil.CommonTestData.EXERCISE_LIST_PANEL_LIST_VIEW_ID;
import static seedu.exercise.testutil.CommonTestData.GUI_TITLE_TEXT;
import static seedu.exercise.testutil.CommonTestData.SUGGESTION_LIST_PANEL_LIST_VIEW_ID;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExercises;
import static seedu.exercise.ui.testutil.GuiAssert.assertItemFocused;
import static seedu.exercise.ui.testutil.GuiAssert.assertItemSelected;
import static seedu.exercise.ui.testutil.GuiAssert.assertListSelectionReset;
import static seedu.exercise.ui.testutil.GuiAssert.assertResourceAddedToListView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import seedu.exercise.guihandlers.LabelHandle;
import seedu.exercise.guihandlers.ResourceListViewHandle;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Resource;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.typicalutil.TypicalExercises;
import seedu.exercise.testutil.typicalutil.TypicalRegime;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;
import seedu.exercise.ui.testutil.GuiUnitTest;

public class ResourceListPanelTest extends GuiUnitTest {

    private ScheduleListPanel scheduleListPanel;
    private RegimeListPanel regimeListPanel;
    private ExerciseListPanel exerciseListPanel;
    private SuggestionListPanel suggestionListPanel;

    private ResourceListViewHandle<Schedule> scheduleListViewHandle;
    private ResourceListViewHandle<Regime> regimeListViewHandle;
    private ResourceListViewHandle<Exercise> exerciseListViewHandle;
    private ResourceListViewHandle<Exercise> suggestListViewHandle;
    private LabelHandle exerciseListPanelLabelHandle;

    @BeforeEach
    private void setUp() {
        scheduleListPanel = new ScheduleListPanel(
                FXCollections.observableList(TypicalSchedule.getValidScheduleList()));
        scheduleListViewHandle = new ResourceListViewHandle<>(
                getChildNode(scheduleListPanel.getRoot(), CommonTestData.SCHEDULE_LIST_PANEL_LIST_VIEW_ID));

        regimeListPanel = new RegimeListPanel(FXCollections.observableList(TypicalRegime.getValidRegimeList()));
        regimeListViewHandle = new ResourceListViewHandle<>(
                getChildNode(regimeListPanel.getRoot(), CommonTestData.REGIME_LIST_PANEL_LIST_VIEW_ID));

        exerciseListPanel = new ExerciseListPanel(FXCollections.observableList(getTypicalExercises()));
        exerciseListViewHandle = new ResourceListViewHandle<>(
                getChildNode(exerciseListPanel.getRoot(), EXERCISE_LIST_PANEL_LIST_VIEW_ID));
        exerciseListPanelLabelHandle = new LabelHandle(
                getChildNode(exerciseListPanel.getRoot(), EXERCISE_LABEL_ID));

        suggestionListPanel = new SuggestionListPanel(
                FXCollections.observableList(TypicalExercises.getTypicalExercises()));
        suggestListViewHandle = new ResourceListViewHandle<>(
                getChildNode(suggestionListPanel.getRoot(), SUGGESTION_LIST_PANEL_LIST_VIEW_ID));

    }

    @Test
    public void resetListSelect_selectionReset() {
        //Testing schedule list panel
        initUi(scheduleListPanel);
        assertListSelectionReset(scheduleListViewHandle, scheduleListPanel);

        //Testing regime list panel
        initUi(regimeListPanel);
        assertListSelectionReset(regimeListViewHandle, regimeListPanel);

        //Testing exercise list panel
        initUi(exerciseListPanel);
        assertListSelectionReset(exerciseListViewHandle, exerciseListPanel);

        //Testing suggest list panel
        initUi(suggestionListPanel);
        assertListSelectionReset(suggestListViewHandle, suggestionListPanel);
    }

    @Test
    public void setNewResourceCard_newResource_added() {
        //Testing schedule list panel
        initUi(scheduleListPanel);
        assertResourceAddedToListView(scheduleListViewHandle,
                TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE, scheduleListPanel);

        //Testing regime list panel
        initUi(regimeListPanel);
        assertResourceAddedToListView(regimeListViewHandle,
                TypicalRegime.VALID_REGIME_CARDIO, regimeListPanel);

        //Testing exercise list panel
        initUi(exerciseListPanel);
        assertResourceAddedToListView(exerciseListViewHandle,
                TypicalExercises.AEROBICS, exerciseListPanel);

        //Testing suggestion list panel
        initUi(suggestionListPanel);
        assertResourceAddedToListView(suggestListViewHandle,
                TypicalExercises.BENCH_PRESS, suggestionListPanel);
    }

    @Test
    public void getResourceListView_testResources_sameView() {
        assertEquals(scheduleListViewHandle.getListView(), scheduleListPanel.getResourceListView());
        assertEquals(regimeListViewHandle.getListView(), regimeListPanel.getResourceListView());
        assertEquals(exerciseListViewHandle.getListView(), exerciseListPanel.getResourceListView());
        assertEquals(suggestListViewHandle.getListView(), suggestionListPanel.getResourceListView());
    }

    @Test
    public void setExerciseListPanelTitleText_correctString_textShown() {
        initUi(exerciseListPanel);
        guiRobot.interact(() -> exerciseListPanel.setPanelTitleText(CommonTestData.GUI_TITLE_TEXT));
        assertEquals(GUI_TITLE_TEXT, exerciseListPanelLabelHandle.getLabelText());
    }

    @Test
    public void selectGivenIndex_selectFirstIndex_selected() {
        assertResourceItemSelectedAndFocused(scheduleListPanel, scheduleListViewHandle, 0);
        assertResourceItemSelectedAndFocused(regimeListPanel, regimeListViewHandle, 0);
        assertResourceItemSelectedAndFocused(exerciseListPanel, exerciseListViewHandle, 0);
        assertResourceItemSelectedAndFocused(suggestionListPanel, suggestListViewHandle, 0);
    }

    @Test
    public void onListViewChanged_itemSelected() {
        scheduleListViewHandle.addResource(TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE);
        assertResourceItemSelectedAndFocused(scheduleListPanel, scheduleListViewHandle, 0);
    }

    private void initUi(UiPart<? extends Parent> uiPart) {
        uiPartExtension.setUiPart(uiPart);
    }

    /**
     * Asserts that the resource at {@code index} is selected and focused.
     */
    private void assertResourceItemSelectedAndFocused(ResourceListPanel actualPanel,
                                                      ResourceListViewHandle<? extends Resource> handle, int index) {
        initUi(actualPanel);
        guiRobot.interact(() -> actualPanel.selectGivenIndex(0));
        guiRobot.pauseForHuman();
        assertItemSelected(handle.getListView(), 0);
        assertItemFocused(handle.getListView(), 0);
    }
}
