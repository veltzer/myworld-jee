package meta.utils.ds.iterators;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a collection of iterators that acts as a single iterator. You can
 * also add new iterators at runtime. This could be the basis of a recursive
 * iterator.
 *
 * @author Mark Veltzer
 */
public class IteratorMultiple<E> implements Iterator<E>, Iterable<E> {

	/**
	 * This stack stores all the iterators found so far. The head of the stack is
	 * the iterator which we are currently progressing through
	 */
	private final Deque<Iterator<E>> iterators = new LinkedList<Iterator<E>>();

	public IteratorMultiple() {
	}

	/**
	 * Add an iterator to be iterated
	 * @param e the iterator to add
	 */
	public void add(Iterator<E> e) {
		iterators.addLast(e);
	}

	/**
	 * Add an iterable to be iterated
	 * @param e
	 */
	public void add(Iterable<E> e) {
		iterators.addLast(e.iterator());
	}
	/**
	 * Add an iterator to be iterated
	 * @param e the iterator to add
	 */
	public void addFirst(Iterator<E> e) {
		iterators.addFirst(e);
	}

	/**
	 * Add an iterable to be iterated
	 * @param e
	 */
	public void addFirst(Iterable<E> e) {
		iterators.addFirst(e.iterator());
	}

	/**
	 * Add an iterator to be iterated
	 * @param e the iterator to add
	 */
	public void addLast(Iterator<E> e) {
		iterators.addLast(e);
	}

	/**
	 * Add an iterable to be iterated
	 * @param e
	 */
	public void addLast(Iterable<E> e) {
		iterators.addLast(e.iterator());
	}
	/**
	 * This delegates to the underlying iterator
	 */
	@Override
	public void remove() {
		while(!iterators.isEmpty() && !iterators.getFirst().hasNext()) {
			iterators.removeFirst();
		}
		iterators.getFirst().remove();
	}

	/**
	 * Returns the next element in our iteration, throwing a NoSuchElementException
	 * if none is found.
	 */
	@Override
	public E next() {
		while(!iterators.isEmpty() && !iterators.getFirst().hasNext()) {
			iterators.removeFirst();
		}
		return iterators.getFirst().next();
	}

	/**
	 * Returns if there are any objects left to iterate over. This method
	 * can change the internal state of the object when it is called, but repeated
	 * calls to it will not have any additional side effects.
	 */
	@Override
	public boolean hasNext() {
		while(!iterators.isEmpty() && !iterators.getFirst().hasNext()) {
			iterators.removeFirst();
		}
		return !iterators.isEmpty();
	}

	/**
	 * Method to comply with the iterable interface
	 * @return The iterator of this iterable
	 */
	@Override
	public Iterator<E> iterator() {
		return this;
	}

	public static void main(String[] args) {
		List<Integer> l1=new ArrayList<Integer>();
		l1.add(2);
		l1.add(4);

		List<Integer> l2=new ArrayList<Integer>();
		l2.add(1);
		l2.add(3);

		IteratorMultiple<Integer> mi=new IteratorMultiple<Integer>();
		mi.add(l1);
		mi.add(l2);

		for(Integer x: mi) {
			System.out.println(x);
		}
	}
}