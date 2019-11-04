package com.dukeacademy.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.entities.Status;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller class for home page. The home page not only provides greetings and introductory messages to the users,
 * but also functions as a personal dashboard. This personal dashboard displays useful information pertaining to the
 * user's personal progress he/she made so far in Duke Academy.
 */
public class HomePage extends UiPart<Region> {
    private static final String FXML = "HomePage.fxml";
    private static final String[] skillTiers = {"Novice", "Apprentice", "Master", "Grandmaster", "Duke"};
    private static final int[] skillTierCeilings = {29, 49, 69, 89, 100};

    @FXML
    private ProgressIndicator indicator;

    @FXML
    private Text numDone;

    @FXML
    private Text numTotal;

    @FXML
    private Text currentTier;

    @FXML
    private Text numToNextTier;

    @FXML
    private Text nextTier;

    @FXML
    private Text progressDescription;

    @FXML
    private VBox attempting;

    @FXML
    private VBox bookmarked;

    @FXML
    private Button userGuideButton;

    @FXML
    private Button devGuideButton;

    @FXML
    private Button aboutUsButton;

    @FXML
    private Button contactUsButton;

    @FXML
    private AnchorPane userGuidePane;

    /**
     * Constructor for Home Page controller class.
     *
     * @param questions observable list of questions
     */
    public HomePage(ObservableList<Question> questions) {
        super(FXML);
        this.populateHomePage(questions);
        questions.addListener((ListChangeListener<Question>) c -> populateHomePage(new ArrayList<>(c.getList())));
    }

    /**
     * Helper method to populate the homepage with a new list of questions
     * @param questions questions to populate the homepage with
     */
    private void populateHomePage(List<Question> questions) {
        int done = computeNumDone(questions);
        int total = computeNumTotal(questions);
        updateNumDone(done);
        updateNumTotal(total);
        double progress = computeProgress(done, total);
        updateIndicator(progress);

        String current = computeCurrentTier(progress);
        updateCurrentTier(current);

        if ("Duke".equals(current)) {
            removeProgressDescription();
        } else {
            String next = computeNextTier(progress);
            updateNextTier(next);
            int toNextTier = computeNumToNextTier(progress, current, total);
            updateNumToNextTier(toNextTier);
        }

        updateAttempting(questions);
        updateBookmarked(questions);
    }

    /**
     * Computes the number of questions successfully completed by the user.
     * @param questions observable list of questions
     * @return number of questions successfully completed by the user
     */
    private int computeNumDone(List<Question> questions) {
        int numDone = 0;
        for (Question q : questions) {
            if (q.getStatus() == Status.PASSED) {
                numDone++;
            }
        }
        return numDone;
    }

    /**
     * Computes the total number of questions in the question bank.
     * @param questions observable list of questions
     * @return total number of questions in the question bank
     */
    private int computeNumTotal(List<Question> questions) {
        return questions.size();
    }

    /**
     * Computes the progress made by the user, in decimal form, by dividing number of questions successfully
     * completed by the user over total number of questions in the question bank.
     * @param numDone number of questions successfully completed by the user
     * @param numTotal total number of questions in the question bank
     * @return the progress made by the user, in decimal form
     */
    private double computeProgress(int numDone, int numTotal) {
        return (double) numDone / numTotal;
    }

    /**
     * Computes the current skill tier of the user.
     * @param progress the progress made by the user, in decimal form
     * @return current skill tier of the user
     */
    private String computeCurrentTier(double progress) {
        double progressInPercent = progress * 100;
        int roundedProgress = (int) Math.round(progressInPercent);

        int currentTierIndex = -1;

        // Trivial case - Progress within the first skill tier value range, so current tier is the lowest tier
        if (roundedProgress <= skillTierCeilings[0]) {
            currentTierIndex = 0;
        }
        // Trivial case - Progress is 100%, so current tier is the highest tier
        if (roundedProgress == 100) {
            currentTierIndex = skillTierCeilings.length - 1;
        }

        // Else, when it comes to non-trivial cases
        for (int i = 0; i < skillTierCeilings.length; i++) {
            if (roundedProgress <= skillTierCeilings[i]) {
                currentTierIndex = i;
                break;
            }
        }

        String currentTier = skillTiers[currentTierIndex];
        return currentTier;
    }

    /**
     * Computes the next skill tier of the user. This method will only be called if current tier is not already
     * the highest tier.
     * @param progress the progress made by the user, in decimal form
     * @return next skill tier of the user
     */
    private String computeNextTier(double progress) {
        double progressInPercent = progress * 100;
        int roundedProgress = (int) Math.round(progressInPercent);

        int nextTierIndex = -1;

        // Trivial case - Progress within the first skill tier value range, so current tier is the lowest tier
        // Its next tier will be the second lowest tier
        if (roundedProgress <= skillTierCeilings[0]) {
            nextTierIndex = 1;
        }

        // Else when it comes to non-trivial cases,
        for (int i = 0; i < skillTierCeilings.length; i++) {
            if (roundedProgress <= skillTierCeilings[i]) {
                nextTierIndex = i + 1;
                break;
            }
        }

        String nextTier = skillTiers[nextTierIndex];
        return nextTier;
    }

    /**
     * Computes the number of completed questions required in order to reach the next tier.
     * @param progress the progress made by the user, in decimal form
     * @param currentTier current skill tier of the user
     * @param numTotal total number of questions in the question bank
     * @return the number of completed questions required in order to reach the next tier
     */
    private int computeNumToNextTier(double progress, String currentTier, int numTotal) {
        List<String> skillTiersList = Arrays.asList(skillTiers);
        int currentTierIndex = skillTiersList.indexOf(currentTier);

        int ceilingOfCurrentTier = skillTierCeilings[currentTierIndex];
        int floorOfNextTier = ceilingOfCurrentTier + 1;

        double progressInPercent = progress * 100;
        int roundedProgress = (int) Math.round(progressInPercent);

        int gapInPercent = floorOfNextTier - roundedProgress;
        double gapInDouble = (double) gapInPercent / 100 * numTotal;
        int numToNextTier = (int) Math.round(gapInDouble);

        return numToNextTier;
    }

    /**
     * Updates the progress indicator on Home Page UI
     * @param progress the progress made by the user, in decimal form
     */
    private void updateIndicator(double progress) {
        indicator.setProgress(progress);
    }

    /**
     * Updates the number of questions successfully completed by the user, on the Home Page UI
     * @param inputNumDone number of questions successfully completed by the user
     */
    private void updateNumDone(int inputNumDone) {
        String numDoneString = inputNumDone + "";
        numDone.setText(numDoneString);
    }

    /**
     * Updates total number of questions in the question bank, on the Home Page UI
     * @param inputNumTotal total number of questions in the question bank
     */
    private void updateNumTotal(int inputNumTotal) {
        String numTotalString = inputNumTotal + "";
        numTotal.setText(numTotalString);
    }

    /**
     * Updates the current skill tier of the user on the Home Page UI
     * @param tier current skill tier of the user
     */
    private void updateCurrentTier(String tier) {
        currentTier.setText(tier);
    }

    /**
     * Updates the next skill tier of the user on the Home Page UI
     * @param tier next skill tier of the user
     */
    private void updateNextTier(String tier) {
        nextTier.setText(tier);
    }

    /**
     * Updates the number of completed questions required in order to reach the next tier
     * @param num number of completed questions required in order to reach the next tier
     */
    private void updateNumToNextTier(int num) {
        String numToNextTierString = num + "";
        numToNextTier.setText(numToNextTierString);
    }

    /**
     * Updates the list of questions the user is still attempting, on the Home Page UI
     * @param questions observable list of questions
     */
    private void updateAttempting(List<Question> questions) {
        ListView<Label> attemptingListView = new ListView<>();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getStatus() == Status.ATTEMPTED) {
                int displayedIndex = questions.get(i).getId();
                String displayedIndexString = displayedIndex + ". ";
                Label questionDisplay = new Label(displayedIndexString + questions.get(i).getTitle());
                attemptingListView.getItems().add(questionDisplay);
            }
        }
        attemptingListView.setPrefWidth(300);
        VBox.setVgrow(attemptingListView, Priority.ALWAYS);
        attempting.getChildren().clear();
        attempting.getChildren().add(attemptingListView);
    }

    /**
     * Updates the list of questions the user bookmarked for personal reference, on the Home Page UI
     * @param questions observable list of questions
     */
    private void updateBookmarked(List<Question> questions) {
        ListView<Label> bookmarkedListView = new ListView<>();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).isBookmarked()) {
                int displayedIndex = questions.get(i).getId();
                String displayedIndexString = displayedIndex + ". ";
                Label questionDisplay = new Label(displayedIndexString + questions.get(i).getTitle());
                bookmarkedListView.getItems().add(questionDisplay);
            }
        }
        bookmarkedListView.setPrefWidth(300);
        VBox.setVgrow(bookmarkedListView, Priority.ALWAYS);
        bookmarked.getChildren().clear();
        bookmarked.getChildren().add(bookmarkedListView);
    }

    /**
     * Removes the progress description on the Home Page UI
     */
    private void removeProgressDescription() {
        numToNextTier.setText("");
        progressDescription.setText("");
        nextTier.setText("");
    }

    /**
     * On click handler for user guide button
     */
    public void handleUserGuideButtonClick() {
        userGuidePane.setVisible(true);
    }

    /**
     * On click handler for close button on user guide popup pane
     */
    public void handleCloseUserGuideButtonClick() {
        userGuidePane.setVisible(false);
    }
}
