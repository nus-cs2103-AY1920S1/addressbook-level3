package com.dukeacademy.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;


/**
 * The type Profile panel.
 */
class ProfilePanel extends UiPart<Region> {
    private static final String FXML = "ProfilePanel.fxml";

    @FXML
    private TextArea profileDisplay;

    /**
     * Instantiates a new Profile panel.
     *
     * @param profile the profile
     */
    public ProfilePanel(ArrayList<String> profile) {
        super(FXML);
        profileDisplay.setText(profile.get(0));
    }

    /**
     * Sets profile display.
     *
     * @param s the s
     */
    public void setProfileDisplay(String s) {
        profileDisplay.setText(s);
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfilePanel)) {
            return false;
        }

        // state check
        ProfilePanel card = (ProfilePanel) other;
        return profileDisplay.getText().equals(card.profileDisplay.getText());
    }


}
