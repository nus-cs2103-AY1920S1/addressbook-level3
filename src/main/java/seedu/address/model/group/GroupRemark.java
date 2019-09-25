package seedu.address.model.group;

public class GroupRemark {
    public final String remark;

    public GroupRemark(String remark){
        this.remark = remark;
    }

    @Override
    public String toString() {
        return remark;
    }
}
