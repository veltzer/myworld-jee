package meta.pdata.ops;

import org.hibernate.Session;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.Date;
import meta.pdata.util.HibernateUtils;
import meta.pdata.data.Done;
import java.util.List;
import org.hibernate.Transaction;

public class AddDone {

	public static void main(String[] args) {
		AddDone mgr = new AddDone();

		if (args[0].equals("store")) {
			mgr.createAndStoreEvent(args[1], new Date());
		}
		if (args[0].equals("list")) {
			List<Done> done = mgr.listEvents();
			XStream xStream = new XStream(new DomDriver());
			System.out.println(xStream.toXML(done));
		}

		HibernateUtils.getSessionFactory().close();
	}

	private void createAndStoreEvent(String title, Date theDate) {

		Session session = HibernateUtils.getSessionFactory()
				.getCurrentSession();

		Transaction t=session.beginTransaction();

		Done theEvent = new Done();
		theEvent.setTitle(title);
		theEvent.setDateDone(theDate);

		session.save(theEvent);
		
		t.commit();
	}

	@SuppressWarnings("unchecked")
	protected List<Done> listEvents() {

		Session session = HibernateUtils.getSessionFactory()
				.getCurrentSession();

		Transaction t=session.beginTransaction();

		List<Done> result = session.createQuery("from Done").list();

		t.commit();

		return result;
	}

}