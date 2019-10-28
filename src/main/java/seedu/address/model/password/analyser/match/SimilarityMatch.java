package seedu.address.model.password.analyser.match;

import seedu.address.model.password.Password;

/**
 * Represents a {@code match} which was found by {@code SimilarityAnalyser}.
 */
public class SimilarityMatch extends BaseMatch implements Comparable<SimilarityMatch> {
    private double similarity;
    private Password password;

    /**
     * Constructs a {@code SimilarityMatch}
     *
     * @param startIndex the start index in the {@code PasswordValue} which the match was found.
     * @param endIndex the end index in the {@code PasswordValue} which the match was found.
     * @param token the string in the {@code PasswordValue} which the match was found.
     * @param password the password found that was similar.
     * @param similarity the similarity score for both passwords.
     */
    public SimilarityMatch(int startIndex, int endIndex, String token, Password password, double similarity) {
        super(startIndex, endIndex, token);
        this.password = password;
        this.similarity = similarity;
    }

    @Override
    public int compareTo(SimilarityMatch o) {
        return Double.compare(o.similarity, this.similarity);
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Similarity Match\n"
                + "Account : " + this.password
                + "Similarity : " + this.similarity + "\n";
    }
}
