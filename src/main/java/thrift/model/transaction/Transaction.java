package thrift.model.transaction;

import java.util.Set;

import thrift.model.tag.Tag;


/**
 * Represents a transaction containing the various types of transaction parameters such as Description,
 * Date, Value, Tags.
 */
public abstract class Transaction {

    public abstract boolean isSameTransaction(Transaction otherTransaction);

    /**
     * Get this Transaction object's Description.
     *
     * @return This Transaction object's Description.
     */
    public abstract Description getDescription();

    /**
     * Get this Transaction object's Date.
     *
     * @return This Transaction object's Date.
     */
    public abstract TransactionDate getDate();

    /**
     * Gets this Transaction object's Value in {@link Value#DEFAULT_CURRENCY}
     * denomination.
     *
     * @return This Transaction object's Value
     * in {@link Value#DEFAULT_CURRENCY} denomination.
     */
    public abstract Value getValue();

    /**
     * Gets this Transaction object's set of Tag.
     *
     * @return Set&lt;Tag&gt; belonging to this Transaction object.
     */
    public abstract Set<Tag> getTags();
}
