package meta.examples;

// this example monitors the current directory

import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;
import net.contentobjects.jnotify.JNotify;

public class JnotifyListenerImpl implements JNotifyListener {

	@Override
	public void fileCreated(int arg0, String arg1, String arg2) {
		System.out.println("fileCreated " + arg0 + arg1 + arg2);

	}

	@Override
	public void fileDeleted(int arg0, String arg1, String arg2) {
		System.out.println("fileDeleted " + arg0 + arg1 + arg2);

	}

	@Override
	public void fileModified(int arg0, String arg1, String arg2) {
		System.out.println("fileModified " + arg0 + arg1 + arg2);

	}

	@Override
	public void fileRenamed(int arg0, String arg1, String arg2, String arg3) {
		System.out.println("fileRenamed " + arg0 + arg1 + arg2 + arg3);

	}

	public static void main(String[] args) throws JNotifyException {
		// to add a watch :
		String path = System.getProperty("user.home");

		// watch mask
		int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED
				| JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;

		// watch subtree?
		boolean watchSubtree = false;

		// add actual watch
		int watchID = JNotify.addWatch(path, mask, watchSubtree,
				new JnotifyListenerImpl());

		// sleep a little, the application will exit if you
		// don't (watching is asynchronous), depending on your
		// application, this may not be required
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e1) {
		}

		// to remove watch the watch
		boolean res = JNotify.removeWatch(watchID);
		if (!res) {
			// invalid watch ID specified.
		}
	}
}
