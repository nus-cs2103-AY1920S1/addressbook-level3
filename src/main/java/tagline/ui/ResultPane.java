package tagline.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.contact.Contact;
import tagline.model.group.Group;
import tagline.model.note.Note;
import tagline.model.tag.Tag;
import tagline.ui.contact.ContactListResultView;
import tagline.ui.contact.ContactProfileResultView;
import tagline.ui.group.GroupListResultView;
import tagline.ui.group.GroupProfileResultView;
import tagline.ui.note.NoteListResultView;
import tagline.ui.tag.TagListResultView;

/**
 * The UI component that displays the command result.
 */
public class ResultPane extends UiPart<StackPane> {

    private static final String FXML = "ResultPane.fxml";
    private static final double RESULT_PANE_FADE_TRANSITION_DURATION = 0.5;
    private static final double RESULT_PANE_FADE_TRANSITION_OPACITY_FROM = 0.3;
    private static final double RESULT_PANE_FADE_TRANSITION_OPACITY_TO = 1.0;

    /** The current view displayed in the result pane. */
    private ViewType currentViewType;

    /** Stores all result pane views using the ViewType as the key. */
    private Map<ViewType, ResultView> resultViewMap;

    @FXML
    private StackPane resultViewPlaceholder;

    public ResultPane() {
        super(FXML);
    }

    /**
     * Changes the view in the result pane according to ViewType.
     *
     * @param viewType the ViewType to switch to
     */
    public void setCurrentViewType(ViewType viewType) {
        //no preferred view, don't switch
        if (viewType == ViewType.NONE) {
            return;
        }

        //already correct view, no need to switch
        if (currentViewType == viewType) {
            return;
        }

        setResultPaneView(resultViewMap.get(viewType));
        currentViewType = viewType;
    }

    /**
     * Adds a fade in transition to a result view.
     *
     * @param resultView the ResultView to animate
     */
    private void animateFadeIn(ResultView resultView) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(RESULT_PANE_FADE_TRANSITION_DURATION));
        fadeTransition.setFromValue(RESULT_PANE_FADE_TRANSITION_OPACITY_FROM);
        fadeTransition.setToValue(RESULT_PANE_FADE_TRANSITION_OPACITY_TO);

        fadeTransition.setNode(resultView.getRoot());
        fadeTransition.play();
    }

    /**
     * Changes the view in the result pane.
     *
     * @param resultView Next view to display
     */
    private void setResultPaneView(ResultView resultView) {
        resultViewPlaceholder.getChildren().clear();

        animateFadeIn(resultView);
        resultViewPlaceholder.getChildren().add(resultView.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts(ObservableList<Contact> filteredContactList,
                               ObservableList<Note> filteredNoteList,
                               ObservableList<Group> filteredGroupList,
                               ObservableList<Tag> filteredTagList) {
        resultViewMap = new HashMap<>();

        ContactProfileResultView contactProfileResultView = new ContactProfileResultView();
        contactProfileResultView.fillInnerParts(filteredContactList, filteredNoteList);
        resultViewMap.put(ViewType.CONTACT_PROFILE, contactProfileResultView);

        ContactListResultView contactListResultView = new ContactListResultView();
        contactListResultView.fillInnerParts(filteredContactList);
        resultViewMap.put(ViewType.CONTACT_LIST, contactListResultView);

        NoteListResultView noteListResultView = new NoteListResultView();
        noteListResultView.fillInnerParts(filteredNoteList);
        resultViewMap.put(ViewType.NOTE, noteListResultView);

        GroupProfileResultView groupProfileResultView = new GroupProfileResultView();
        groupProfileResultView.fillInnerParts(filteredGroupList, filteredContactList);
        resultViewMap.put(ViewType.GROUP_PROFILE, groupProfileResultView);

        GroupListResultView groupListResultView = new GroupListResultView();
        groupListResultView.fillInnerParts(filteredGroupList);
        resultViewMap.put(ViewType.GROUP_LIST, groupListResultView);

        TagListResultView tagListResultView = new TagListResultView();
        tagListResultView.fillInnerParts(filteredTagList);
        resultViewMap.put(ViewType.TAG_LIST, tagListResultView);

        //set to note result view by default
        setCurrentViewType(ViewType.NOTE);
    }
}
