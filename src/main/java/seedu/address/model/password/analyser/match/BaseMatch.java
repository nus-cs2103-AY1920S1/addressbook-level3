package seedu.address.model.password.analyser.match;

/**
 * Represents a match found by an analyser which contains the start and end index as well as the match token.
 */
public abstract class BaseMatch implements Match {

    private String token;
    private int startIndex;
    private int endIndex;
    //private int rank;

    public BaseMatch(int startIndex, int endIndex, String token) {
        this.token = token;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        //this.rank = rank; TODO : transfer
    }

    public String getToken() {
        return token;
    }

    public int getStart_index() {
        return startIndex;
    }

    public int getEnd_index() {
        return endIndex;
    }

    @Override
    public String toString() {
        return MESSAGE_INIT
                + "Token: " + this.token + "\n"
                + "Start Index: " + this.startIndex + "\n"
                + "End Index: " + this.endIndex + "\n";
    }

    //    @Override
    //    public int compareTo(Match o) {
    //        return this.rank - o.rank;
    //    }
}
