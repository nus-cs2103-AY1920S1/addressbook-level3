package seedu.sugarmummy.model.aesthetics;

import seedu.sugarmummy.model.ModelStub;
import seedu.sugarmummy.model.UserPrefs;

/**
 * Class containing stubs for Model specifically for Aesthetics.
 */
public class AestheticsModelStub {

    public static final Colour VALID_FONT_COLOUR = new Colour("yellow");
    public static final Background VALID_BACKGROUND = new Background("black");

    /**
     * Model stub for FontColour sharing the same set of user preferences as model stub for Background.
     */
    public static class ModelStubForFontColour extends ModelStub {

        private UserPrefs userPrefs = new UserPrefs();

        @Override
        public Colour getFontColour() {
            return userPrefs.getFontColour();
        }

        @Override
        public void setFontColour(Colour fontColour) {
            userPrefs.setFontColour(fontColour);
        }

        @Override
        public Background getBackground() {
            return userPrefs.getBackground();
        }

        @Override
        public void setBackground(Background background) {
            userPrefs.setBackground(background);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof ModelStubForFontColour)) {
                return false;
            } else {
                return getFontColour().equals(((ModelStubForFontColour) obj)
                        .getFontColour());
            }
        }
    }

    /**
     * Model stub for Background sharing the same set of user preferences as model stub for FontColour.
     */
    public static class ModelStubForBackground extends ModelStub {

        private UserPrefs userPrefs = new UserPrefs();

        @Override
        public Background getBackground() {
            return userPrefs.getBackground();
        }

        @Override
        public void setBackground(Background background) {
            userPrefs.setBackground(background);
        }

        @Override
        public Colour getFontColour() {
            return userPrefs.getFontColour();
        }

        @Override
        public void setFontColour(Colour fontColour) {
            userPrefs.setFontColour(fontColour);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof ModelStubForBackground)) {
                return false;
            } else {
                return getFontColour().equals(((ModelStubForBackground) obj)
                        .getFontColour());
            }
        }

        @Override
        public String toString() {
            return "" + getBackground() + getFontColour();
        }
    }

}
