package seedu.address.diaryfeature.model.diaryEntry;

public class Memory {

    public static final String MESSAGE_CONSTRAINTS = "" +
            "Memory, while optional, if input, " +
            "can't be the empty string, can't only be spaces ";

    public static final int MEMORY_MAX_LENGTH = 100;


    private final String memory;
    private boolean isPrivate = false;
    public static final String HIDDEN_MESSAGE = "*****";
    private String showMemory;

    /**
     * Constructs an {@code Address}.
     *
     * @param input is a valid title.
     */
    public Memory(String input) {
        memory = input;
        showMemory = input;
    }


    public boolean getPrivacy() {
        return isPrivate;
    }

    public void setPrivate() {
        isPrivate = true;
        showMemory = HIDDEN_MESSAGE;
    }

    public void unPrivate() {
        isPrivate = false;
        showMemory = memory;
    }


    @Override
    public String toString() {
        return showMemory;
    }

    public String toSave() {
        return memory;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Memory // instanceof handles nulls
                && memory.equalsIgnoreCase(((Memory) other).memory)); // state check
    }

    @Override
    public int hashCode() {
        return memory.hashCode();
    }

}
