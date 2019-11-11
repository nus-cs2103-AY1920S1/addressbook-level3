package seedu.deliverymans.model.deliveryman.deliverymanstatus;

import java.util.Objects;

/**
 * A list that contains 3 status tags.
 */
public class UniqueStatusList {

    public static final String AVAILABLE_STATUS = "AVAILABLE";
    public static final String UNAVAILABLE_STATUS = "UNAVAILABLE";
    public static final String DELIVERING_STATUS = "DELIVERING";

    private static final StatusTag availableTag = new StatusTag(AVAILABLE_STATUS);
    private static final StatusTag unavailableTag = new StatusTag(UNAVAILABLE_STATUS);
    private static final StatusTag deliveringTag = new StatusTag(DELIVERING_STATUS);

    /**
     * Lists all the deliverymen with their respective statuses.
     */
    public static StatusTag getAvailableTag() {
        return availableTag;
    }

    public static StatusTag getUnavailableTag() {
        return unavailableTag;
    }

    public static StatusTag getDeliveringTag() {
        return deliveringTag;
    }

    public static StatusTag getCorrespondingTag(String tagDescription) {

        switch (tagDescription) {
        case AVAILABLE_STATUS:
            return getAvailableTag();
        case UNAVAILABLE_STATUS:
            return getUnavailableTag();
        case DELIVERING_STATUS:
            return getDeliveringTag();
        default:
            return getAvailableTag();
        }
    }

    /**
     * Checks if a tag description is valid ie. matches one of the 3 tags in UniqueStatusList.
     */
    public static boolean isValidStatus(String tagDescription) {
        return tagDescription.equals(AVAILABLE_STATUS) || tagDescription.equals(UNAVAILABLE_STATUS)
                || tagDescription.equals(DELIVERING_STATUS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStatusList // instanceof handles nulls
                && availableTag.equals(((StatusTag) other).description)
                && unavailableTag.equals(((StatusTag) other).description))
                && deliveringTag.equals(((StatusTag) other).description); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableTag, unavailableTag, deliveringTag);
    }
}
