package com.wf.quartz.job.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.wf.quartz.job.UniforceJobHandler;

public abstract class QuartzActivator implements BundleActivator {

	private UniforceJobHandler job = null;
	private ServiceRegistration<UniforceJobHandler> registerService;

	public void start(BundleContext bundleContext) throws Exception {
		job = getJob();
		registerService = bundleContext.registerService(UniforceJobHandler.class, job, null);
	}

	public abstract UniforceJobHandler getJob();

	public void stop(BundleContext bundleContext) throws Exception {
		if (job != null) {
			registerService.unregister();
			job = null;
			registerService = null;
		}
	}

}
