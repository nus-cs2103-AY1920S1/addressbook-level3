package com.dukeacademy.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * A profile page which has two tabs, a progress tab that shows user's completion of questions,
 * and a bookmarks tab which shows questions which user chose to bookmark.
 */
public class ProfilePage extends UiPart<Region> {
    private static final String FXML = "ProfilePage.fxml";

    @FXML
    private TabPane profilePane;

    @FXML
    private ProgressIndicator indicator;

    @FXML
    private Text numDone;

    @FXML
    private Text numTotal;

    @FXML
    private Text skillLevel;

    public ProfilePage() {
        super(FXML);
        indicator.setMinWidth(130);
        indicator.setMinHeight(130);
        double progress = 71.0 / 80.0;
        indicator.setProgress(progress);
        numDone.setText("71");
        numTotal.setText("80");
        fillSkillLevel(progress);
    }

    /**
     * Closes the profile page on click of the button.
     * @param event the ActionEvent
     */
    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        profilePane.setVisible(false);
    }

    /**
     * Fills the skill level of the user.
     * @param progress the degree of completion of questions achieved by the user
     */
    private void fillSkillLevel(double progress) {
        if (progress < 0.3) {
            skillLevel.setText("Novice");
        }

        if (progress > 0.3 && progress < 0.6) {
            skillLevel.setText("Apprentice");
        }

        if (progress > 0.6 && progress < 0.8) {
            skillLevel.setText("Master");
        }

        if (progress > 0.8) {
            skillLevel.setText("Grandmaster");
        }


    }
}
