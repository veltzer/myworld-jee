package meta.builderplug;

import java.io.File;
import java.io.IOException;

import meta.jdmt.Builder;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class Ftl implements Builder {

	public Ftl() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("."));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

    @Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
