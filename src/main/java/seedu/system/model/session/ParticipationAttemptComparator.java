package seedu.system.model.session;

import java.util.Comparator;

/**
 * Compares two PartcipationAttempt objects based on their attempt index,
 * and order them in increasing weight attempted for each respective lift and attempt.
 */
public class ParticipationAttemptComparator implements Comparator<ParticipationAttempt> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * First, we compare the ParticipationAttempt objects by their index,
     * to group all the attempts with the same lift and attempt number together.
     * Then, we will sort the attempts with the same attempt index by their weights attempted,
     * in increasing order.
     *
     * @param pa1 the first object to be compared.
     * @param pa2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(ParticipationAttempt pa1, ParticipationAttempt pa2) {
        if (pa1.getAttemptIndex() == pa2.getAttemptIndex()) {
            return pa1.getWeight() - pa2.getWeight();
        } else {
            return pa1.getAttemptIndex() - pa2.getAttemptIndex();
        }
    }
}
