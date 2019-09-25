package seedu.address.model.group;

/**
 * GroupRemark of a Group.
 */
public class GroupRemark {
    public final String remark;

    public GroupRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return remark;
    }
}
