//@@author nattanyz
package dream.fcard.logic.stats;

/** A DeckSession represents a review session involving a particular deck. */
public class DeckSession extends Session {
    /** The Result of this particular session. */
    private String result; // call getScore() to get score

    /** Gets the Result of this particular session. */
    public String getResult() {
        return this.result;
    }

    /** Sets the Result of this particular session. */
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public void endSession() {
        super.endSession();
    }

    public void endSession(String result) {
        this.endSession();
        this.setResult(result);
    }
}
