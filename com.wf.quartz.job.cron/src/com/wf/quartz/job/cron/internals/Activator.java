package com.wf.quartz.job.cron.internals;

import org.osgi.framework.BundleContext;

import com.wf.quartz.job.QuartzJob;
import com.wf.quartz.job.activator.QuartzActivator;
import com.wf.quartz.job.cron.CronJob;

public class Activator extends QuartzActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		Activator.context = null;
	}

	@Override
	public QuartzJob getJob() {
		return new CronJob();
	}

}
