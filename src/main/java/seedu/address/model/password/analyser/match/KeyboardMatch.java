package seedu.address.model.password.analyser.match;

import java.util.Objects;

/**
 * Represents a {@code match} which was found  by {@code KeyboardAnalyser}.
 */
public class KeyboardMatch extends BaseMatch implements Comparable<KeyboardMatch> {

    private int turns;

    /**
     * Constructs a {@code KeyboardMatch}
     *
     * @param startIndex the start index in the {@code PasswordValue} which the match was found.
     * @param endIndex the end index in the {@code PasswordValue} which the match was found.
     * @param token the string in the {@code PasswordValue} which the match was found.
     * @param turns the complexity level of the keyboard pattern.
     */
    public KeyboardMatch(int startIndex, int endIndex, String token, int turns) {
        super(startIndex, endIndex, token);
        this.turns = turns;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.turns == ((KeyboardMatch) obj).turns;
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Keyboard Match\n" + "Keyboard Pattern Complexity: " + this.turns + "\n";
    }

    @Override
    public int compareTo(KeyboardMatch o) {
        if ((this.turns - o.turns) != 0) {
            return this.turns - o.turns;
        }
        return o.getToken().length() - super.getToken().length();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), turns);
    }
}
