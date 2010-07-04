package meta.utils.ds.iterators;

import meta.utils.ds.filters.*;
import java.util.Iterator;

/**
 * This is an iterator with a filter. You supply both the iterator and the
 * filter on construction and the iterator will not return anything the
 * filter is against.
 *
 * If you want more that one filter just just a "multiplefilters" construct
 * to build a filter which represents a whole set of filters.
 *
 * @author Mark Veltzer
 */
public class IteratorWithFilter<E> implements Iterator<E>, Iterable<E> {

	/**
	 * The iterator this iterator masks
	 */
	private Iterator<E> iter;
	/*
	 * The filter that will be used
	 */
	private IFilter<E> filter;
	/*
	 * Private element stored from call to hasNext
	 */
	E element;
	/**
	 * construct this iterator
	 * @param i iterator to use
	 * @param f filter to use
	 */
	public IteratorWithFilter(Iterator<E> i,IFilter<E> f) {
		iter=i;
		filter=f;
		element=null;
	}
	@Override
	public boolean hasNext() {
		if(element!=null)  {
			return true;
		}
		else {
			if(iter.hasNext()) {
				E e=iter.next();
				while(true) {
					if(filter.accept(e)) {
						element=e;
						return true;
					} else {
						if(iter.hasNext()) {
							e=iter.next();
						} else {
							return false;
						}
					}
				}
			} else {
				return false;
			}
		}
	}

	@Override
	public E next() {
		if(element!=null) {
			E e=element;
			element=null;
			return e;
		} else {
			if(hasNext()) {
				return element;
			} else {
				throw new RuntimeException("what ?!?");
			}
		}
	}

	@Override
	public void remove() {
		iter.remove();
	}

	@Override
	public Iterator<E> iterator() {
		return this;
	}
}