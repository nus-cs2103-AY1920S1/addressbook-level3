package seedu.address.model;

/**
 * StudyBuddyCounterHelper to help maintain the counts of each item
 *
 */

public class StudyBuddyCounter {

    private int flashcardCount = 0;
    private int notesCount = 0;
    private int cheatSheetCount = 0;

    public StudyBuddyCounter() {
        this.resetCounts();
    }

    public void increaseFlashcardCount() {
        this.flashcardCount++;
    }

    public void increaseNotesCount() {
        this.notesCount++;
    }

    public void increaseCheatSheetCount() {
        this.cheatSheetCount++;
    }

    /**
     * Resets the counter
     */
    public void resetCounts() {
        this.flashcardCount = 0;
        this.notesCount = 0;
        this.cheatSheetCount = 0;
    }

    // getter methods

    public int getFlashcardCount() {
        return flashcardCount;
    }

    public int getNotesCount() {
        return notesCount;
    }

    public int getCheatSheetCount() {
        return cheatSheetCount;
    }

    /**
     * Check if the tag still exists
     * @return true or false depending
     */
    public boolean isTagExisting() {
        int sum = this.flashcardCount + this.cheatSheetCount + this.notesCount;
        boolean exists;
        if (sum == 0) {
            exists = false;
        } else {
            exists = true;
        }
        return exists;
    }

    @Override
    public String toString() {
        return " flashcards : " + this.flashcardCount
                + " notes : " + this.notesCount
                + " cheatsheets : " + this.cheatSheetCount;
    }
}
