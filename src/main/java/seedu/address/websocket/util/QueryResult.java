package seedu.address.websocket.util;

import java.util.logging.Logger;

/**
 * Result of the Query
 */
public class QueryResult {
    private Integer responseCode;
    private String responseResult;

    public QueryResult(Integer responseCode, String responseResult) {
        this.responseCode = responseCode;
        this.responseResult = responseResult;
    }

    public Integer getResponseCode() {
        return this.responseCode;
    }

    public String getResponseResult() {
        return this.responseResult;
    }

    /**
     * Processes any errors and returns true if not errors are found.
     *
     * @return
     */

    public boolean process(Logger logger) {
        if (this.responseCode == null) {
            logger.warning(this.responseResult);
            return false;
        } else if (this.responseCode != 200) {
            logger.warning(this.responseResult + " response code: " + this.responseCode);
            return false;
        } else {
            logger.fine(this.responseResult);
            return true;
        }
    }
}
