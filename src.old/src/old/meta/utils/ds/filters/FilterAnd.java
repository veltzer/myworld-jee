package meta.utils.ds.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a multiple filter. It can apply many filters in stead of one
 * @author Mark Veltzer
 */
public class FilterAnd<E> implements IFilter<E> {

	private List<IFilter<E>> filters;
	public FilterAnd() {
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
		boolean accept=true;
		for(IFilter<E> x:filters) {
			accept=accept && x.accept(e);
			// short cut the calculation
			if(accept==false) {
				continue;
			}
		}
		return accept;
	}
}