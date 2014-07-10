package com.wf.quartz.job.simple.internals;

import org.osgi.framework.BundleContext;

import com.wf.quartz.job.UniforceJobHandler;
import com.wf.quartz.job.activator.QuartzActivator;
import com.wf.quartz.job.simple.SimpleJob;

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
	public UniforceJobHandler getJob() {
		return new SimpleJob();
	}

}
