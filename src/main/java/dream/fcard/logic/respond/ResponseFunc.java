package dream.fcard.logic.respond;

/**
 * Interface for lambdas or commands classes for Responses enum.
 */
public interface ResponseFunc {

    /**
     * Lambda's signature interface.
     *
     * @param i input string
     */
    boolean funcCall(String i);
}
