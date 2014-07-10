package com.wf.quartz.job.service.internals;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.wf.quartz.job.WFSchedulerAdmin;
import com.wf.quartz.job.properties.QuartzTopics;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private WFSchedulerImpl component = null;
	private ServiceRegistration<WFSchedulerAdmin> registerService;
	private EventHandler quartzEventHandler;
	private ServiceRegistration<EventHandler> registerServiceEventHandler;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		registerQuartzEventHandler();
		registerWFSchedulerAdmin();
	}

	private void registerWFSchedulerAdmin() {
		component = new WFSchedulerImpl(context);
		component.start();
		registerService = context.registerService(WFSchedulerAdmin.class, component, null);
	}

	private void registerQuartzEventHandler() {
		quartzEventHandler = new QuartzEventHandler();
		String[] topics = new String[] { QuartzTopics.ALL };

		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put(EventConstants.EVENT_TOPIC, topics);
		registerServiceEventHandler = context.registerService(EventHandler.class, quartzEventHandler, props);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;

		if (quartzEventHandler != null) {
			registerServiceEventHandler.unregister();
			quartzEventHandler = null;
			registerServiceEventHandler = null;
		}

		if (component != null) {
			registerService.unregister();
			component.stop();
			component = null;
			registerService = null;
		}
	}

}
