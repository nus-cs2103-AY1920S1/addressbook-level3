//@@author nattanyz
package dream.fcard.logic.stats;

import dream.fcard.logic.exam.Result;

public class DeckSession extends Session {
    /** The Result of this particular session. */
    private Result result; // call getScore() to get score

    /** Gets the Result of this particular session. */
    public Result getResult() {
        return this.result;
    }

    /** Sets the Result of this particular session. */
    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public void endSession() {
        super.endSession();
        // todo: get Result from ExamRunner
        //this.setResult(new Result());
    }
}
