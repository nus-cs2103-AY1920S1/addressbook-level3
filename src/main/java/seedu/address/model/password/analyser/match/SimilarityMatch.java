package seedu.address.model.password.analyser.match;

import java.util.Objects;

import seedu.address.model.password.Password;

/**
 * Represents a {@code Match} which was found by {@code SimilarityAnalyser}.
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
                + "Similarity : " + convertToPercentage(this.similarity) + "\n";
    }

    /**
     * Returns the similarity decimal into a percentage format.
     * @param similarity the similarity decimal
     */
    private String convertToPercentage(double similarity) {
        return String.format("%.3f", similarity * 100) + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimilarityMatch that = (SimilarityMatch) o;
        return Double.compare(that.similarity, similarity) == 0
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(similarity, password);
    }
}
