package dream.fcard.util.datastructures;

/**
 * Product type of two types.
 * @param <U>   first object type
 * @param <T>   second object type
 */
public class Pair<U, T> {

    private U first;
    private T second;

    public Pair(U a, T b) {
        first = a;
        second = b;
    }

    public U fst() {
        return first;
    }

    public T snd() {
        return second;
    }
}
