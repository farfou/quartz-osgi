package com.wf.quartz.job.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.wf.quartz.job.QuartzJob;

public abstract class QuartzActivator implements BundleActivator {

	private QuartzJob job = null;
	private ServiceRegistration<QuartzJob> registerService;

	public void start(BundleContext bundleContext) throws Exception {
		job = getJob();
		registerService = bundleContext.registerService(QuartzJob.class, job, null);
	}

	public abstract QuartzJob getJob();

	public void stop(BundleContext bundleContext) throws Exception {
		if (job != null) {
			registerService.unregister();
			job = null;
			registerService = null;
		}
	}

}
