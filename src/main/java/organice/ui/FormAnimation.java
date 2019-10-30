package organice.ui;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import organice.model.person.FormField;
import organice.model.person.Type;

/**
 * Contains animations for form mode.
 */
public class FormAnimation {


    /**
     * Animation for fading in and out of the form mode.
     */
    public static void fadingAnimation(MainWindow mainWindow) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), mainWindow.getPersonListPanelPlaceholder());
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    /**
     * Animation for fading in and out of the form mode.
     */
    public static void showButtonAnimation(Button button) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), button);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    /**
     * Animation for increase percentage of progress bar.
     */
    public static void percentageChangeAnimation(Double newValue, String newPercentage,
                                                 Label percentage, ProgressBar bar) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(500));
            }

            protected void interpolate(double frac) {
                final int length = newPercentage.length();
                final int n = Math.round(length * (float) frac);
                percentage.setText(newPercentage.substring(0, n) + "%");
            }
        };
        animation.play();

        //Progress Bar animation
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(bar.progressProperty(), newValue);
        KeyFrame keyFrame = new KeyFrame(new Duration(500), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * Animation for typing the input into the form.
     */
    public static void typingAnimation(MainWindow mainWindow, String commandText, String formField) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(750));
            }

            protected void interpolate(double frac) {
                Type formType = mainWindow.getForm().getType();
                final int length = commandText.length();
                final int n = Math.round(length * (float) frac);
                switch (formField) {

                case FormField.NAME:
                    mainWindow.getForm().setName(commandText.substring(0, n));
                    break;

                case FormField.NRIC:
                    mainWindow.getForm().setNric(commandText.substring(0, n));
                    break;

                case FormField.PHONE:
                    mainWindow.getForm().setPhone(commandText.substring(0, n));
                    break;
                //CHECKSTYLE.OFF: SeparatorWrap - Type casting causes checkstyle to ask for incorrect wrapping
                case FormField.AGE:
                    if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setAge(commandText.substring(0, n));
                    } else if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setAge(commandText.substring(0, n));
                    }
                    break;

                case FormField.ORGAN:
                    if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setOrgan(commandText.substring(0, n));
                    } else if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setOrgan(commandText.substring(0, n));
                    }
                    break;

                case FormField.BLOOD_TYPE:
                    if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setBloodType(commandText.substring(0, n));
                    } else if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setBloodType(commandText.substring(0, n));
                    }
                    break;

                case FormField.TISSUE_TYPE:
                    if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setTissueType(commandText.substring(0, n));
                    } else if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setTissueType(commandText.substring(0, n));
                    }
                    break;

                case FormField.PRIORITY:
                    ((PatientForm) mainWindow.getForm()).setPriority(commandText.substring(0, n));
                    break;

                case FormField.DOCTOR_IC:
                    ((PatientForm) mainWindow.getForm()).setDoctorIc(commandText.substring(0, n));
                    break;

                case FormField.ORGAN_EXPIRY_DATE:
                    ((DonorForm) mainWindow.getForm()).setOrganExpiryDate(commandText.substring(0, n));
                    break;
                //CHECKSTYLE.ON: SeparatorWrap
                default:
                    break;
                }
            }
        };
        animation.play();
    }
}
