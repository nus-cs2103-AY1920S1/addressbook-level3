package budgetbuddy.model.loan;

import java.util.function.Predicate;

import budgetbuddy.model.attributes.Direction;

public class LoanFilters {

    public static final Predicate<Loan> FILTER_ALL = loan -> true;

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