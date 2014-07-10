package com.wf.quartz.job.service.internals;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.wf.quartz.job.UniforceJobHandler;

public class UniforceJobHandlerWrapper {

	private ServiceReference<UniforceJobHandler> reference = null;
	private UniforceJobHandler handler = null;
	private BundleContext context = null;

	public UniforceJobHandlerWrapper(ServiceReference<UniforceJobHandler> reference, BundleContext context) {
		this.reference = reference;
		this.context = context;
	}

	public void flush() {
		synchronized (this) {
			if (handler == null) {
				return;
			}
			handler = null;
		}
		try {
			context.ungetService(reference);
		} catch (IllegalStateException e) {
			// ignore event admin must have stopped
		}
	}

	public UniforceJobHandler getHandler() {
		synchronized (this) {
			// if we already have a handler, return it
			if (handler != null) {
				return handler;
			}
		}

		// we don't have the handler, so lets get it outside the sync region
		UniforceJobHandler tempHandler = null;
		try {
			tempHandler = context.getService(reference);
		} catch (IllegalStateException e) {
			// ignore; event admin may have stopped
		}

		synchronized (this) {
			// do we still need the handler we just got?
			if (handler == null) {
				handler = tempHandler;
				return handler;
			}
			// get the current handler
			tempHandler = handler;
		}

		// unget the handler we just got since we don't need it
		try {
			context.ungetService(reference);
		} catch (IllegalStateException e) {
			// ignore; event admin may have stopped
		}

		// return the current handler (copied into the local var)
		return tempHandler;
	}
}
