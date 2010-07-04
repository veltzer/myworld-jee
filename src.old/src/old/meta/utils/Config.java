package meta.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.DatabaseConfiguration;

public class Config {
	private static Configuration config = null;
	private static final String table = "t_option";
	private static final String col_key = "f_name";
	private static final String col_val = "f_value";

	public static Configuration getConfig() {
		if (config != null) {
			return (config);
		} else {
			config = new DatabaseConfiguration(DataSource.getDataSource(),
					table, col_key, col_val);
			/*
			 * ConfigurationFactory factory = new
			 * ConfigurationFactory("config.xml"); try { config =
			 * factory.getConfiguration(); } catch (ConfigurationException e) {
			 * e.printStackTrace(); }
			 */
			return (config);
		}
	}

	public static void main(String[] args) {
		boolean b = Config.getConfig().getBoolean("property");
		System.out.println("b is " + b);
	}
}
