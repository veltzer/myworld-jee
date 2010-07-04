package meta.utils;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSource {
	static private final String driverClassName = "com.mysql.jdbc.Driver";
	static private final String url = "jdbc:mysql://localhost:3306/d_all";
	static private final String username = "mark";
	static private final String password = "";
	static BasicDataSource bsd = null;

	public static BasicDataSource getDataSource() {
		if (bsd == null) {
			bsd = new BasicDataSource();
			bsd.setDriverClassName(driverClassName);
			bsd.setUrl(url);
			bsd.setUsername(username);
			bsd.setPassword(password);
		}
		return bsd;
	}
}
