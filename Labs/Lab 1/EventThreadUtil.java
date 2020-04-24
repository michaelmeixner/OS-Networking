/**
 * Code written by Nicolaas tenBroek.
 * JavaDoc documentation written by Michael Meixner.
 */

import javax.swing.*;

public class EventThreadUtil {
	protected static Class EVENT_DISPATCH_THREAD_CLASS;

	/**
	 * Attempts to find and return the class object of the class "java.awt.EventDispatchThread".
	 * If the class object is not found, the system error is thrown.
	 * @param java.awt.EventDispatchThread the class for which a class object is being found.
	 * @param e a throwable of type Exception that can be caught if the class objeect cannot be found.
	 * @return the class object of the given parameter.
	 */
	static {
		try {
			EVENT_DISPATCH_THREAD_CLASS = Class.forName("java.awt.EventDispatchThread");
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Checks that the currently executing thread object is assignment-compatible with the object EVENT_DISPATCH_THREAD_CLASS.
	 * @return whether or not the EVENT_DISPATCH_THREAD_CLASS object is an instance of the same class as the currently executing thread object.
	 */
	public static final boolean isCurrentThreadEventThread() {
		return EVENT_DISPATCH_THREAD_CLASS.isInstance(Thread.currentThread());
	}

	/**
	 * Checks that the current Runnable interface is runnable, and run it if it is. If it is null, quit the method.
	 * If it is not the current method, put it at the front of the run queue.
	 * @param runnable an instance of the Runnable interface
	 */
	public static final void runNowInEventThread(Runnable runnable) {
		if(runnable == null) {
			return;
		}
		if(isCurrentThreadEventThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch(InterruptedException ie) {
			} catch(java.lang.reflect.InvocationTargetException ite) {}
		}
	}

	/**
	 * Checks that the current Runnable interface is runnable, and run it if it is. If it is null, quit the method.
	 * If it is not the current method, put it at later in the run queue.
	 * @param runnable an instance of the Runnable interface
	 */
	public static final void runLaterInEventThread(Runnable runnable) {
		if(runnable == null) {
			return;
		}
		if(isCurrentThreadEventThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}
}
