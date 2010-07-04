package meta.utils.ds.iterators;

import java.util.Iterator;

/**
 * A recursive iterator based on the MultipleIterator class
 * The trick is that if multiple iterator returns an element
 * which is also an iterator than that iterator is added.
 *
 * @author Mark Veltzer
 */
public class IteratorRecursive<E> extends IteratorMultiple<E> {
	/**
	 * Method to return the next element
	 * @return The next
	 */
	@Override
	public E next() {
		E e=super.next();
		if(e instanceof Iterator) {
			Iterator<E> ei=(Iterator<E>)e;
			addFirst(ei);
		}
		return e;
	}
}