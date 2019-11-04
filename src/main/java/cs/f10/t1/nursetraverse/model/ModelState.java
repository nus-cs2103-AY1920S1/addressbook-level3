package cs.f10.t1.nursetraverse.model;

/**
 * Enum that indicates the current state of the model. This state is used to determine whether certain commands
 * are available for use.
 */
public enum ModelState {
    NORMAL, VISIT_ONGOING;

    public static final String MESSAGE_REQUIRE_STATE_NORMAL =
            "This command cannot be used when there is an ongoing visit.";
    public static final String MESSAGE_REQUIRE_STATE_VISIT_ONGOING =
            "This command cannot be used when there is no ongoing visit.";
}
