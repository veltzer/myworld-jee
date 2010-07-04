package org.meta.progress;

import org.meta.wrappers.java.util.concurrent.Semaphore;

/**
 * The class here represents a Progress class that can be used to report on work
 * progress from a task.
 *
 * The task can be a thead in which case other threads could use this class to know
 * what that thread is currently doing.
 *
 * The task can also just be a piece of code with no concurrenct considerations
 * what so ever and just use this class to report visually on its progress
 * (curses, standard java io, swing, SWT, web, database or whatever).
 * TODO: use a pool (I'm creating a multitue of small Event objects here...)
 * <c>
 * Here is how a client would look like.
 *
 * Progress p = New Progress();
 * // this means that this task can be paused and can be cancelled
 * p.init("this tasks name",true,true,true);
 *
 * p.report("doing setup work");
 * // setup code
 *
 * p.startl("this is a loop",100);
 *
 * for(int i=0;i<100;i++) {
 *	p.workl("this is work "+i);
 *	// do some work
 *	// check if cancelled and if so stop doing work
 *	if(p.isCancelled()) {
 *		break;
 *	}
 *	// this will block until allowed to continue
 *	p.waitForCont();
 *	}
 * }
 *
 * p.fini();
 * </c>
 *
 * @author mark
 */
public class Progress {

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	private int units;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;

	public boolean isUnitsKnown() {
		return unitsKnown;
	}

	public void setUnitsKnown(boolean unitsKnown) {
		this.unitsKnown = unitsKnown;
	}

	private int work;

	public int getWork() {
		return work;
	}

	public void setWork(int work) {
		this.work = work;
	}

	private boolean unitsKnown;
	/**
	 *
	 */
	protected String name;
	/**
	 *
	 */
	protected boolean canPause;
	/**
	 *
	 */
	protected boolean canCancel;
	/**
	 *
	 */
	protected boolean canRerpot;
	/**
	 *
	 */
	protected boolean isCancelled;
	/**
	 *
	 */
	protected boolean isPaused;
	/**
	 *
	 */
	protected Semaphore s;
	/**
	 *
	 */
	protected IProgressEventProcessor pep;

	/**
	 *
	 * @param c
	 */
	public Progress(IProgressEventProcessor ipep) {
		super();
		pep=ipep;
	}

	public void init(String iname, boolean icanPause, boolean icanCancel, boolean icanReport) {
		name=iname;
		canPause=icanPause;
		canCancel=icanCancel;
		canRerpot=icanReport;
		this.s=new Semaphore(1);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.INIT,this);
		pe.setMessage(name);
		pep.processProgressEvent(pe);
	}

	public void fini() {
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.FINI,this);
		pe.setMessage(name);
		pep.processProgressEvent(pe);
	}

	public void report(String imessage) {
		setMessage(imessage);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.REPORT,this);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}

	public void startl(String imessage) {
		setWork(0);
		setUnitsKnown(false);
		setMessage(imessage);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.STARTL,this);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}

	public void startl(String imessage, int units) {
		setWork(0);
		setUnitsKnown(true);
		setUnits(units);
		setMessage(imessage);

		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.STARTL,this);
		pe.setMessage(message);
		pe.setUnits(units);
		pep.processProgressEvent(pe);
	}

	public void workl(String imessage, int units) {
		work+=units;
		setMessage(imessage);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.WORK,this);
		pe.setUnits(units);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}
	public void workl() {
		workl("",1);
	}
	public void workl(String onwhat) {
		workl(onwhat,1);
	}
	public void workl(int units) {
		workl("",units);
	}


	public void endl() {
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.ENDL,this);
		pep.processProgressEvent(pe);
	}

	public void endl(String imessage) {
		setMessage(imessage);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.ENDL,this);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}

	public void cancell() {
		isCancelled=true;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void pause() {
		// it's ok if we cannot acquire - maybe some else already paused
		// the task?
		if(s.tryAcquire()) {
			isPaused=true;
		}
	}

	public void cont() {
		if(isPaused) {
			s.release();
		} else {
			throw new RuntimeException("what 2341 ?!?");
		}
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void waitForCont() {
		if(isPaused) {
			s.acquire();
			s.release();
		}
	}

	public void print(String imessage) {
		setMessage(imessage);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.PRINT,this);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}

	/**
	 *
	 * @param s
	 */
	public void println(String imessage) {
		setMessage(imessage);
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.PRINTLN,this);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}

	/**
	 *
	 * @param o
	 */
	public void print(Object o) {
		print(o.toString());
	}

	/**
	 *
	 * @param o
	 */
	public void println(Object o) {
		println(o.toString());
	}

	/**
	 *
	 */
	public void println() {
		setMessage("");
		ProgressEvent pe=new ProgressEvent(ProgressEvent.Type.PRINTLN,this);
		pe.setMessage(message);
		pep.processProgressEvent(pe);
	}
}