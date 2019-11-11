package seedu.address.diaryfeature.model.diaryEntry;

public class Memory {

    public static final String MESSAGE_CONSTRAINTS = "" +
            "Memory, while optional, if input, " +
            "can't be the empty string, can't only be spaces ";

    public static final int MEMORY_MAX_LENGTH = 100;


    private final String memory;
    private boolean isPrivate = false;
    private static final String HIDDEN_MESSAGE = "*****";
    private String showMemory;

    /**
     * Constructs an {@code Memory}.
     *
     * @param input is a valid memory.
     */
    public Memory(String input) {
        memory = input;
        showMemory = input;
    }

    /**
     * Get the privacy status of this memory
     * @return
     */
    public boolean getPrivacy() {
        return isPrivate;
    }

    /**
     * Sets Privacy to true and changes the visible memory to *****
     */
    public void setPrivate() {
        isPrivate = true;
        showMemory = HIDDEN_MESSAGE;
    }

    /**
     * Sets Privacy to false and changes the visible memory to the actual memory
     */
    public void unPrivate() {
        isPrivate = false;
        showMemory = memory;
    }

    /**
     * Return the "visible" memory
     * @return showMemory for presentation to user
     */
    @Override
    public String toString() {
        return showMemory;
    }

    /**
     * Return the actual memory to save
     * @return memory to save
     */
    public String toSave() {
        return memory;
    }

    /**
     * To defend against any malfunctions
     * @return a copy of this memory
     */
    public Memory copy () {
        Memory copyMemory =  new Memory(memory);
        if(this.isPrivate) {
            copyMemory.setPrivate();
        }
        return copyMemory;
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
