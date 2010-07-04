package meta.utils.ds.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an "or" filter. You can give it many filters and it will accept a value
 * if any of the filters says "yes" on the value.
 * @author Mark Veltzer
 */
public class FilterOr<E> implements IFilter<E> {

	private List<IFilter<E>> filters;
	public FilterOr() {
		filters=new ArrayList<IFilter<E>>();
	}
	public void addFilter(IFilter<E> f) {
		filters.add(f);
	}
	/**
	 * This method applies all filter
	 * @param e
	 * @return
	 */

	@Override
	public boolean accept(E e) {
		for(IFilter<E> x:filters) {
			if(x.accept(e)) {
				return true;
			}
		}
		return false;
	}
}