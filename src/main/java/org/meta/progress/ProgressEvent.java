package org.meta.progress;

/**
 * This is a progress event
 * @author mark
 */
public class ProgressEvent {

	protected Progress progress;


	public Progress getProgress() {
		return progress;
	}

	/**
	 *
	 */
	protected String message;
	/**
	 *
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 *
	 * @param str
	 */
	public void setMessage(String imessage) {
		message = imessage;
	}

	/**
	 *
	 */
	protected int units;
	/**
	 *
	 * @return
	 */
	public int getUnits() {
		return units;
	}

	/**
	 *
	 * @param units
	 */
	public void setUnits(int units) {
		this.units = units;
	}

	/**
	 *
	 */
	public enum Type {
		/**
		 *
		 */
		INIT,
		/**
		 *
		 */
		FINI,
		/**
		 *
		 */
		WORK,
		/**
		 *
		 */
		REPORT,
		/**
		 *
		 */
		STARTL,
		/**
		 *
		 */
		ENDL,
		/**
		 *
		 */
		PRINT
	, 	PRINTLN}

	/**
	 * The type of the event
	 */
	protected Type type;

	/**
	 *
	 * @return
	 */
	public Type getType() {
		return type;
	}

	/**
	 *
	 * @param type the type of the event
	 */
	public ProgressEvent(Type itype,Progress iprogress) {
		type=itype;
		progress=iprogress;
	}
}