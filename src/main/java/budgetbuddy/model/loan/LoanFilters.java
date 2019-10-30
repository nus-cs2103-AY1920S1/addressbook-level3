package budgetbuddy.model.loan;

import java.util.function.Predicate;

import budgetbuddy.model.attributes.Direction;

/**
 * A container class to hold filters for the loan list.
 */
public class LoanFilters {

    public static final Predicate<Loan> FILTER_ALL = loan -> true;

    /**
     * Returns a predicate that checks if a loan's direction is equal to the given direction.
     */
    public static Predicate<Loan> getDirectionPredicate(Direction direction) {
        switch (direction) {
        case IN:
            return loan -> loan.getDirection() == Direction.IN;
        case OUT:
            return loan -> loan.getDirection() == Direction.OUT;
        default:
            throw new EnumConstantNotPresentException(
                    Direction.class, "Direction filter not found for given direction.");
        }
    }

    /**
     * Returns a predicate that checks if a loan's status is equal to the given status.
     */
    public static Predicate<Loan> getStatusPredicate(Status status) {
        switch (status) {
        case PAID:
            return Loan::isPaid;
        case UNPAID:
            return loan -> !loan.isPaid();
        default:
            throw new EnumConstantNotPresentException(
                    Status.class, "Status filter not found for given status.");
        }
    }
}
