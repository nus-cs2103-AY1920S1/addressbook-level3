package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
@@author sakurablossom-reused
Reused from <a href="https://github.com/apache/commons-collections/blob/master/src/main/java/org/
apache/commons/collections4/list/TreeList.java"/> with several modifications.
*/

/**
 * A {@code java.util.List} implementation that is optimised for fast insertions and
 * removals of unique elements in the list.
 */
public class UniqueTreeList<E extends Identical> extends AbstractList<E> {

    /** The root node in the AVL tree */
    private AvlNode<E> root;

    /** The current size of the list */
    private int size;

    //-----------------------------------------------------------------------
    /**
     * Constructs a new empty list.
     */
    public UniqueTreeList() {
        super();
    }

    /**
     * Shallow-copies the specified treeList.
     */
    public void setRoot(UniqueTreeList<E> uniqueTreeList) {
        requireNonNull(uniqueTreeList);
        root = uniqueTreeList.root;
        size = uniqueTreeList.size;
    }

    @Override
    public E get(int index) {
        checkInterval(index, 0, size() - 1);
        return root.get(index).getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkInterval(index, 0, size());
        return new TreeListIterator<>(this, index);
    }

    @Override
    public int indexOf(Object o) {
        if (root == null || !(o instanceof Identical)) {
            return -1;
        }
        return root.indexOf((Identical) o, root.relativePosition);
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Object[] toArray() {
        final Object[] array = new Object[size()];
        if (root != null) {
            root.toArray(array, root.relativePosition);
        }
        return array;
    }

    @Override
    public boolean add(final E obj) {
        modCount++;
        if (root == null) {
            root = new AvlNode<>(0, obj, null, null);
        } else {
            AvlNode<E> rootNode = root.insert(obj);
            if (rootNode == null) {
                return false;
            }
            root = rootNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (E element : c) {
            if (!add(element)) {
                clear();
                return false;
            }
        }
        return true;
    }

    @Override
    public E set(int index, E element) {
        checkInterval(index, 0, size() - 1);
        final AvlNode<E> node = root.get(index);
        final E result = node.value;
        node.setValue(element);
        return result;
    }

    @Override
    public E remove(final int index) {
        modCount++;
        checkInterval(index, 0, size() - 1);
        E result = get(index);
        root = root.remove(index);
        size--;
        return result;
    }

    @Override
    public void clear() {
        modCount++;
        root = null;
        size = 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks whether the index is valid.
     *
     * @param index  the index to check
     * @param startIndex  the first allowed index
     * @param endIndex  the last allowed index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private void checkInterval(final int index, final int startIndex, final int endIndex) {
        if (index < startIndex || index > endIndex) {
            throw new IndexOutOfBoundsException("Invalid index:" + index + ", size=" + size());
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implements an AVLNode which keeps the offset updated.
     * <p>
     * This node contains the real work.
     * TreeList is just there to implement {@link java.util.List}.
     * The nodes don't know the index of the object they are holding.  They
     * do know however their position relative to their parent node.
     * This allows to calculate the index of a node while traversing the tree.
     * <p>
     * The Faedelung calculation stores a flag for both the left and right child
     * to indicate if they are a child (false) or a link as in linked list (true).
     */
    static class AvlNode<E extends Identical> {
        /** The left child node or the predecessor if {@link #leftIsPrevious}.*/
        private AvlNode<E> left;
        /** Flag indicating that left reference is not a subtree but the predecessor. */
        private boolean leftIsPrevious;
        /** The right child node or the successor if {@link #rightIsNext}. */
        private AvlNode<E> right;
        /** Flag indicating that right reference is not a subtree but the successor. */
        private boolean rightIsNext;
        /** How many levels of left/right are below this one. */
        private int height;
        /** The relative position, root holds absolute position. */
        private int relativePosition;
        /** The stored element. */
        private E value;

        /**
         * Constructs a new node with a relative position.
         *
         * @param relativePosition  the relative position of the node
         * @param obj  the value for the node
         * @param rightFollower the node with the value following this one
         * @param leftFollower the node with the value leading this one
         */
        private AvlNode(final int relativePosition, final E obj,
                        final AvlNode<E> rightFollower, final AvlNode<E> leftFollower) {
            this.relativePosition = relativePosition;
            value = obj;
            rightIsNext = true;
            leftIsPrevious = true;
            right = rightFollower;
            left = leftFollower;
        }

        /**
         * Gets the value.
         *
         * @return the value of this node
         */
        E getValue() {
            return value;
        }

        /**
         * Sets the value.
         *
         * @param obj  the value to store
         */
        void setValue(final E obj) {
            this.value = obj;
        }

        /**
         * Locate the element with the given index relative to the
         * offset of the parent of this node.
         */
        AvlNode<E> get(final int index) {
            final int indexRelativeToMe = index - relativePosition;

            if (indexRelativeToMe == 0) {
                return this;
            }

            final AvlNode<E> nextNode = indexRelativeToMe < 0 ? getLeftSubTree() : getRightSubTree();
            if (nextNode == null) {
                return null;
            }
            return nextNode.get(indexRelativeToMe);
        }

        /**
         * Locate the index that contains the specified object.
         */
        int indexOf(Identical<E> element, final int index) {
            if (value == null) {
                return -1;
            }

            int cmp = value.compareTo(element);
            if (cmp < 0) {
                if (getLeftSubTree() == null) {
                    return -1;
                }
                return left.indexOf(element, index + left.relativePosition);
            } else if (cmp > 0) {
                if (getRightSubTree() == null) {
                    return -1;
                }
                return right.indexOf(element, index + right.relativePosition);
            }

            return index;
        }

        /**
         * Stores the node and its children into the array specified.
         *
         * @param array the array to be filled
         * @param index the index of this node
         */
        void toArray(final Object[] array, final int index) {
            array[index] = value;
            if (getLeftSubTree() != null) {
                left.toArray(array, index + left.relativePosition);
            }
            if (getRightSubTree() != null) {
                right.toArray(array, index + right.relativePosition);
            }
        }

        /**
         * Gets the next node in the list after this one.
         *
         * @return the next node
         */
        AvlNode<E> next() {
            if (rightIsNext || right == null) {
                return right;
            }
            return right.min();
        }

        /**
         * Gets the node in the list before this one.
         *
         * @return the previous node
         */
        AvlNode<E> previous() {
            if (leftIsPrevious || left == null) {
                return left;
            }
            return left.max();
        }

        /**
         * Inserts a node at the position index.
         *
         * @param obj is the object to be stored in the position.
         */
        AvlNode<E> insert(final E obj) {
            int cmp = value.compareTo(obj);
            if (cmp < 0) {
                return insertOnLeft(obj);
            } else if (cmp > 0) {
                return insertOnRight(obj);
            } else {
                return null;
            }
        }

        /**
         * Inserts a node into the left sub-tree.
         *
         * @param obj is the object to be stored.
         */
        private AvlNode<E> insertOnLeft(final E obj) {
            if (getLeftSubTree() == null) {
                setLeft(new AvlNode<>(-1, obj, this, left), null);
            } else {
                setLeft(left.insert(obj), null);
            }

            if (relativePosition >= 0) {
                relativePosition++;
            }
            final AvlNode<E> ret = balance();
            recalcHeight();
            return ret;
        }

        /**
         * Inserts a node into the right sub-tree.
         *
         * @param obj is the object to be stored.
         */
        private AvlNode<E> insertOnRight(final E obj) {
            if (getRightSubTree() == null) {
                setRight(new AvlNode<>(+1, obj, right, this), null);
            } else {
                setRight(right.insert(obj), null);
            }
            if (relativePosition < 0) {
                relativePosition--;
            }
            final AvlNode<E> ret = balance();
            recalcHeight();
            return ret;
        }

        //-----------------------------------------------------------------------
        /**
         * Gets the left node, returning null if its a faedelung.
         */
        private AvlNode<E> getLeftSubTree() {
            return leftIsPrevious ? null : left;
        }

        /**
         * Gets the right node, returning null if its a faedelung.
         */
        private AvlNode<E> getRightSubTree() {
            return rightIsNext ? null : right;
        }

        /**
         * Gets the rightmost child of this node.
         */
        private AvlNode<E> max() {
            return getRightSubTree() == null ? this : right.max();
        }

        /**
         * Gets the leftmost child of this node.
         */
        private AvlNode<E> min() {
            return getLeftSubTree() == null ? this : left.min();
        }

        /**
         * Removes the node at a given position.
         *
         * @param index is the index of the element to be removed relative to the position of
         * the parent node of the current node.
         */
        AvlNode<E> remove(int index) {
            final int indexRelativeToMe = index - relativePosition;

            if (indexRelativeToMe == 0) {
                return removeSelf();
            }
            if (indexRelativeToMe > 0) {
                setRight(right.remove(indexRelativeToMe), right.right);
                if (relativePosition < 0) {
                    relativePosition++;
                }
            } else {
                setLeft(left.remove(indexRelativeToMe), left.left);
                if (relativePosition > 0) {
                    relativePosition--;
                }
            }
            recalcHeight();
            return balance();
        }

        /**
         * Removes the rightmost child.
         */
        private AvlNode<E> removeMax() {
            if (getRightSubTree() == null) {
                return removeSelf();
            }
            setRight(right.removeMax(), right.right);
            if (relativePosition < 0) {
                relativePosition++;
            }
            recalcHeight();
            return balance();
        }

        /**
         * Removes the rightmost child.
         */
        private AvlNode<E> removeMin() {
            if (getLeftSubTree() == null) {
                return removeSelf();
            }
            setLeft(left.removeMin(), left.left);
            if (relativePosition > 0) {
                relativePosition--;
            }
            recalcHeight();
            return balance();
        }

        /**
         * Removes this node from the tree.
         */
        private AvlNode<E> removeSelf() {
            if (getRightSubTree() == null && getLeftSubTree() == null) {
                return null;
            }
            if (getRightSubTree() == null) {
                if (relativePosition > 0) {
                    left.relativePosition += relativePosition;
                }
                left.max().setRight(null, right);
                return left;
            }
            if (getLeftSubTree() == null) {
                right.relativePosition += relativePosition - (relativePosition < 0 ? 0 : 1);
                right.min().setLeft(null, left);
                return right;
            }

            if (heightRightMinusLeft() > 0) {
                // more on the right, so delete from the right
                final AvlNode<E> rightMin = right.min();
                value = rightMin.value;
                if (leftIsPrevious) {
                    left = rightMin.left;
                }
                right = right.removeMin();
                if (relativePosition < 0) {
                    relativePosition++;
                }
            } else {
                // more on the left or equal, so delete from the left
                final AvlNode<E> leftMax = left.max();
                value = leftMax.value;
                if (rightIsNext) {
                    right = leftMax.right;
                }
                final AvlNode<E> leftPrevious = left.left;
                left = left.removeMax();
                if (left == null) {
                    // special case where left that was deleted was a double link
                    // only occurs when height difference is equal
                    left = leftPrevious;
                    leftIsPrevious = true;
                }
                if (relativePosition > 0) {
                    relativePosition--;
                }
            }
            recalcHeight();
            return this;
        }

        //-----------------------------------------------------------------------
        /**
         * Balances according to the AVL algorithm.
         */
        private AvlNode<E> balance() {
            switch (heightRightMinusLeft()) {
            case 1 :
            case 0 :
            case -1 :
                return this;
            case -2 :
                if (left.heightRightMinusLeft() > 0) {
                    setLeft(left.rotateLeft(), null);
                }
                return rotateRight();
            case 2 :
                if (right.heightRightMinusLeft() < 0) {
                    setRight(right.rotateRight(), null);
                }
                return rotateLeft();
            default :
                throw new IllegalStateException("tree inconsistent!");
            }
        }

        /**
         * Gets the relative position.
         */
        private int getOffset(final AvlNode<E> node) {
            if (node == null) {
                return 0;
            }
            return node.relativePosition;
        }

        /**
         * Sets the relative position.
         */
        private int setOffset(final AvlNode<E> node, final int newOffest) {
            if (node == null) {
                return 0;
            }
            final int oldOffset = getOffset(node);
            node.relativePosition = newOffest;
            return oldOffset;
        }

        /**
         * Sets the height by calculation.
         */
        private void recalcHeight() {
            height = Math.max(
                getLeftSubTree() == null ? -1 : getLeftSubTree().height,
                getRightSubTree() == null ? -1 : getRightSubTree().height) + 1;
        }

        /**
         * Returns the height of the node or -1 if the node is null.
         */
        private int getHeight(AvlNode<E> node) {
            return node == null ? -1 : node.height;
        }

        /**
         * Returns the height difference right - left
         */
        private int heightRightMinusLeft() {
            return getHeight(getRightSubTree()) - getHeight(getLeftSubTree());
        }

        /**
         * Rotates the tree to the left.
         */
        private AvlNode<E> rotateLeft() {
            final AvlNode<E> newTop = right;
            final AvlNode<E> movedNode = getRightSubTree().getLeftSubTree();

            final int newTopPosition = relativePosition + getOffset(newTop);
            final int myNewPosition = -newTop.relativePosition;
            final int movedPosition = getOffset(newTop) + getOffset(movedNode);

            setRight(movedNode, newTop);
            newTop.setLeft(this, null);

            setOffset(newTop, newTopPosition);
            setOffset(this, myNewPosition);
            setOffset(movedNode, movedPosition);
            return newTop;
        }

        /**
         * Rotates the tree to the right.
         */
        private AvlNode<E> rotateRight() {
            final AvlNode<E> newTop = left; // can't be faedelung
            final AvlNode<E> movedNode = getLeftSubTree().getRightSubTree();

            final int newTopPosition = relativePosition + getOffset(newTop);
            final int myNewPosition = -newTop.relativePosition;
            final int movedPosition = getOffset(newTop) + getOffset(movedNode);

            setLeft(movedNode, newTop);
            newTop.setRight(this, null);

            setOffset(newTop, newTopPosition);
            setOffset(this, myNewPosition);
            setOffset(movedNode, movedPosition);
            return newTop;
        }

        /**
         * Sets the left field to the node, or the previous node if that is null
         *
         * @param node  the new left subtree node
         * @param previous  the previous node in the linked list
         */
        private void setLeft(final AvlNode<E> node, final AvlNode<E> previous) {
            leftIsPrevious = (node == null);
            left = leftIsPrevious ? previous : node;
            recalcHeight();
        }

        /**
         * Sets the right field to the node, or the next node if that is null
         *
         * @param node  the new left subtree node
         * @param next  the next node in the linked list
         */
        private void setRight(final AvlNode<E> node, final AvlNode<E> next) {
            rightIsNext = (node == null);
            right = rightIsNext ? next : node;
            recalcHeight();
        }

        /**
         * Used for debugging.
         */
        @Override
        public String toString() {
            return new StringBuilder()
                .append("AVLNode(")
                .append(relativePosition)
                .append(',')
                .append(left != null)
                .append(',')
                .append(value)
                .append(',')
                .append(getRightSubTree() != null)
                .append(", faedelung ")
                .append(rightIsNext)
                .append(" )")
                .toString();
        }
    }

    /**
     * A list iterator over the linked list.
     */
    static class TreeListIterator<E extends Identical> implements ListIterator<E> {
        /** The parent list */
        private final UniqueTreeList<E> parent;
        /**
         * Cache of the next node that will be returned by {@link #next()}.
         */
        private AvlNode<E> next;
        /**
         * The index of the next node to be returned.
         */
        private int nextIndex;
        /**
         * Cache of the last node that was returned by {@link #next()}
         * or {@link #previous()}.
         */
        private AvlNode<E> current;
        /**
         * The index of the last node that was returned.
         */
        private int currentIndex;
        /**
         * The modification count that the list is expected to have. If the list
         * doesn't have this count, then a
         * {@link java.util.ConcurrentModificationException} may be thrown by
         * the operations.
         */
        private int expectedModCount;

        /**
         * Create a ListIterator for a list.
         *
         * @param parent  the parent list
         * @param fromIndex  the index to start at
         */
        protected TreeListIterator(final UniqueTreeList<E> parent, final int fromIndex)
            throws IndexOutOfBoundsException {
            super();
            this.parent = parent;
            this.expectedModCount = parent.modCount;
            this.next = parent.root == null ? null : parent.root.get(fromIndex);
            this.nextIndex = fromIndex;
            this.currentIndex = -1;
        }

        /**
         * Checks the modification count of the list is the value that this
         * object expects.
         *
         * @throws ConcurrentModificationException If the list's modification
         * count isn't the value that was expected.
         */
        protected void checkModCount() {
            if (parent.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex < parent.size();
        }

        @Override
        public E next() {
            checkModCount();
            if (!hasNext()) {
                throw new NoSuchElementException("No element at index " + nextIndex + ".");
            }
            if (next == null) {
                next = parent.root.get(nextIndex);
            }
            final E value = next.getValue();
            current = next;
            currentIndex = nextIndex++;
            next = next.next();
            return value;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            checkModCount();
            if (!hasPrevious()) {
                throw new NoSuchElementException("Already at start of list.");
            }
            if (next == null) {
                next = parent.root.get(nextIndex - 1);
            } else {
                next = next.previous();
            }
            final E value = next.getValue();
            current = next;
            currentIndex = --nextIndex;
            return value;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex() - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            if (currentIndex == -1) {
                throw new IllegalStateException();
            }
            parent.remove(currentIndex);
            if (nextIndex != currentIndex) {
                // remove() following next()
                nextIndex--;
            }
            // the AVL node referenced by next may have become stale after a remove
            // reset it now: will be retrieved by next call to next()/previous() via nextIndex
            next = null;
            current = null;
            currentIndex = -1;
            expectedModCount++;
        }

        @Override
        public void set(final E obj) {
            checkModCount();
            if (current == null) {
                throw new IllegalStateException();
            }
            current.setValue(obj);
        }

        @Override
        public void add(final E obj) {
            checkModCount();
            parent.add(obj);
            current = null;
            currentIndex = -1;
            nextIndex++;
            expectedModCount++;
        }
    }

}
