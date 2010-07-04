package meta.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;

public class Jndi {

	static private final String type = "javax.sql.DataSource";
	static private final String factory = "org.apache.commons.dbcp.BasicDataSourceFactory";
	static private final String driverClassName = "com.mysql.jdbc.Driver";
	static private final String url = "jdbc:mysql://localhost:3306/d_all";
	static private final String username = "mark";
	static private final String password = "";
	static private final String resource = "jdbc/basic";

	static private InitialContext ic = null;
	static private DataSource ds = null;

	public static void setup() {
		try {
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.fscontext.RefFSContextFactory");
			System.setProperty(Context.PROVIDER_URL, "file:///tmp");
			ic = new InitialContext();

			Reference ref = new Reference(type, factory, null);
			ref.add(new StringRefAddr("driverClassName", driverClassName));
			ref.add(new StringRefAddr("url", url));
			ref.add(new StringRefAddr("username", username));
			ref.add(new StringRefAddr("password", password));
			ic.rebind(resource, ref);
			ds = (DataSource) ic.lookup(resource);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DataSource getDataSource() {
		if (ds == null) {
			setup();
		}
		return (ds);
	}

	public static void main(String[] args) {
		ClassLoader prevcl = Thread.currentThread().getContextClassLoader();
		System.out.println("prevcl is " + prevcl);
		// DataSource ds=getDataSource();
		// System.out.println("ds is "+ds);
	}
}
