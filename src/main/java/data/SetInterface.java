package data;

public interface SetInterface<E> {

    /*
     * PRE -
     * POST - Size of the set is returned
     */
    public int getSize();

    /*
     * PRE - Set is not full
     * POST - TRUE: Element is added to the Set
     *        FALSE: Element could not be added
     */
    public boolean addElement(E element);

    /*
     * PRE - Set is not empty && Element exists in set
     * POST - TRUE: Element has been removed
     *        FALSE: Element could not be found
     */
    public boolean removeElement(E element);

    /*
     * PRE - Set is not empty
     * POST - TRUE: Set is not empty
     *        FALSE: Set is empty
     */
    public boolean containsElement(E element);

    /*
     * PRE -
     * POST - A copy of the set is returned
     */
    public SetInterface<E> copyContents();

    /*
     * PRE -
     * POST - Result of union operation is returned
     */
    public SetInterface<E> union(SetInterface<E> input);

    /*
     * PRE -
     * POST - Result of intersection is returned
     */
    public SetInterface<E> intersect(SetInterface<E> input);

    /*
     * PRE -
     * POST - Result of difference is returned
     */
    public SetInterface<E> complement(SetInterface<E> input);

    /*
     * PRE -
     * POST - Result of symmetric difference is returned
     */
    public SetInterface<E> symmetricDifference(SetInterface<E> input);

    /*
     * PRE -
     * POST - TRUE: Set is empty
     *        FALSE: Set is not empty
     */
    public boolean isEmpty();

}