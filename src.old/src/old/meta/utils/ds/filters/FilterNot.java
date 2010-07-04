package meta.utils.ds.filters;

/**
 * This is a reverse filter. It accepts another filter and does exactly the opposite
 * of it
 * @author Mark Veltzer
 */
public class FilterNot<E> implements IFilter<E> {

	private IFilter<E> filter;
	public FilterNot(IFilter<E> f) {
		filter=f;
	}
	/**
	 * This method only accepts directories
	 * @param e
	 * @return
	 */

	@Override
	public boolean accept(E e) {
		return !filter.accept(e);
	}

}