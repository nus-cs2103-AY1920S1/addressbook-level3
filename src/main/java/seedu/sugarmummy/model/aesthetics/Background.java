package seedu.sugarmummy.model.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's background in the user's biography data. Guarantees: immutable; is valid as declared in {@link
 * #isValidBackgroundPicPath(String)}
 */
public class Background {

    public static final String MESSAGE_CONSTRAINTS =
            "Background should either be a path or colour. "
                    + "Colour should be a valid background or a hexadecimal representation of a colour.";

    public static final String VALIDATION_REGEX = "^$|[^\\s].*";
    private final boolean isBackgroundColour;

    private String backgroundColour;
    private String backgroundPicPath;

    private String bgSize;
    private String bgRepeat;
    private boolean showDefaultBackground = false;

    /**
     * Constructs a {@code Background}.
     *
     * @param background A valid background.
     */
    public Background(String background) {
        requireNonNull(background);
        if (Colour.isValidColour(background)) {
            this.backgroundColour = (new Colour(background)).toString();
            this.backgroundPicPath = null;
            isBackgroundColour = true;
        } else {
            checkArgument(isValidBackgroundPicPath(background), MESSAGE_CONSTRAINTS);
            this.backgroundPicPath = background;
            this.backgroundColour = null;
            isBackgroundColour = false;
        }
    }

    /**
     * Returns true if a given string is a valid background pic path.
     */
    public static boolean isValidBackgroundPicPath(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if given string is a valid background size argument.
     */
    public static boolean isValidBackgroundSize(String test) {
        return test != null && BackgroundImageArgs.BACKGROUND_SIZE_VALUES.contains(test.toLowerCase());
    }

    /**
     * Returns true if a given string is a valid background repeat argument.
     */
    public static boolean isValidBackgroundRepeat(String test) {
        return test != null && BackgroundImageArgs.BACKGROUND_REPEAT_VALUES.contains(test.toLowerCase());
    }

    /**
     * Returns whether or not this object represents a background colour. If false, this object would represent a
     * background image.
     */
    public boolean isBackgroundColour() {
        return isBackgroundColour;
    }

    /**
     * Merges this background's settings with the previous background's settings. i.e. Any blank fields in the new
     * background will be filled with the contents of the previous background, unless explicitly intended to be
     * removed.
     *
     * @param oldBackground Background to compare to this background instance.
     */
    public void merge(Background oldBackground) {
        if (!this.isBackgroundColour && !oldBackground.isBackgroundColour) {
            if ((this.backgroundPicPath == null || this.backgroundPicPath.equals(""))
                    && oldBackground.backgroundPicPath != null) {
                this.backgroundPicPath = oldBackground.backgroundPicPath;
            }
            if ((this.bgSize == null || this.bgSize.equals(""))
                    && oldBackground.bgSize != null) {
                this.bgSize = oldBackground.bgSize;
            }
            if ((this.bgRepeat == null || this.bgRepeat.equals(""))
                    && oldBackground.bgRepeat != null) {
                this.bgRepeat = oldBackground.bgRepeat;
            }
        } else if (!this.isBackgroundColour) {
            this.bgSize = this.bgSize == null || this.bgSize.equals("") ? "auto" : this.bgSize;
            this.bgRepeat = this.bgRepeat == null || this.bgRepeat.equals("") ? "repeat" : this.bgRepeat;
        }
    }

    public String getBgSize() {
        return bgSize;
    }

    public void setBgSize(String bgSize) {
        this.bgSize = bgSize;
    }

    public String getBgRepeat() {
        return bgRepeat;
    }

    public void setBgRepeat(String bgRepeat) {
        this.bgRepeat = bgRepeat;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public String getBackgroundPicPath() {
        return backgroundPicPath;
    }

    public boolean isEmpty() {
        return (backgroundColour == null || backgroundColour.isEmpty())
                && (backgroundPicPath == null || backgroundPicPath.isEmpty());
    }

    /**
     * Returns whether or not to show the default background if this object is used to set the background.
     */
    public boolean showDefaultBackground() {
        return showDefaultBackground;
    }

    /**
     * Sets whether or not to show the default background if this object is used to set the background.
     *
     * @param showDefaultBackground Boolena used to set whether the default background is to be shown.
     */
    public void setShowDefaultBackground(boolean showDefaultBackground) {
        this.showDefaultBackground = showDefaultBackground;
    }

    @Override
    public String toString() {
        return backgroundColour == null ? backgroundPicPath : backgroundColour;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Background) {

            Background otherBackground = (Background) other;

            boolean sameBackgroundColour = this.backgroundColour == null
                    ? otherBackground.backgroundColour == null
                    : otherBackground.backgroundColour != null
                            && this.backgroundColour.equals(otherBackground.backgroundColour);

            boolean sameBackgroundPicPath = this.backgroundPicPath == null
                    ? otherBackground.backgroundPicPath == null
                    : otherBackground.backgroundPicPath != null
                            && this.backgroundPicPath.equals(otherBackground.backgroundPicPath);

            if (sameBackgroundPicPath && sameBackgroundColour) {
                boolean sameBackgroundSize = this.bgSize == null
                        ? otherBackground.bgSize == null
                        : otherBackground.bgSize != null
                                && this.bgSize.equals(otherBackground.bgSize);
                boolean sameBackgroundRepeat = this.bgRepeat == null
                        ? otherBackground.bgRepeat == null
                        : otherBackground.bgRepeat != null
                                && this.bgRepeat.equals(otherBackground.bgRepeat);
                return sameBackgroundSize && sameBackgroundRepeat;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return backgroundColour == null ? backgroundPicPath.hashCode() : backgroundColour.hashCode();
    }

}
