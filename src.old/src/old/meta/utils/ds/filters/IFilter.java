package meta.utils.ds.filters;

/**
 * This is a generic iterator filter which says "true|false" on each element
 *
 * @author Mark Veltzer
 */
public interface IFilter<E> {
	/**
	 * This is the actual and only method to be implemented.
	 * @param e The element in question
	 * @return whether or not to iterate this element
	 */
	boolean accept(E e);
}
