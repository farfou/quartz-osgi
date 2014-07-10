package com.wf.quartz.job.service.internals;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.wf.quartz.job.UniforceJobHandler;
import com.wf.quartz.job.WFSchedulerAdmin;

public class WFSchedulerImpl extends ServiceTracker<UniforceJobHandler, UniforceJobHandlerWrapper> implements WFSchedulerAdmin {

	private Scheduler scheduler = null;
	private EventAdmin eventAdmin = null;
	private SchedulerListener schedulerListener = null;
	private List<UniforceJobHandlerWrapper> jobs = null;

	public WFSchedulerImpl(BundleContext context) {
		super(context, UniforceJobHandler.class.getName(), null);
		jobs = new ArrayList<UniforceJobHandlerWrapper>();
	}

	public void start() {
		try {
			setEventAdminInstance(context);
			setSchedulerInstance();
			this.scheduler.start();
			this.open();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			this.scheduler.shutdown();
			this.close();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	private void setEventAdminInstance(BundleContext context) {
		ServiceReference<EventAdmin> ref = context.getServiceReference(EventAdmin.class);
		EventAdmin service = null;
		if (ref != null) {
			service = context.getService(ref);
			setEventAdmin(service);
			context.ungetService(ref);
		} else {
			service = null;
		}
	}
	
	private void setSchedulerInstance() throws SchedulerException {
		scheduler = new StdSchedulerFactory().getScheduler();
		schedulerListener = new CustomSchedulerListener(getEventAdmin());
		scheduler.addSchedulerListener(schedulerListener);
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public UniforceJobHandlerWrapper addingService(ServiceReference<UniforceJobHandler> reference) {
		System.out.println("servis ekleniyor");
		UniforceJobHandlerWrapper wrapper = new UniforceJobHandlerWrapper(reference, context);
		synchronized (this) {
			bucket(wrapper);
		}
		return wrapper;
	}

	@Override
	public void modifiedService(ServiceReference<UniforceJobHandler> reference, UniforceJobHandlerWrapper service) {
		synchronized (this) {
			unbucket(service);
			bucket(service);
		}

		service.flush(); // needs to be called outside sync region
	}

	public void removedService(ServiceReference<UniforceJobHandler> reference, UniforceJobHandlerWrapper service) {
		synchronized (this) {
			unbucket(service);
		}
		service.flush(); // needs to be called outside sync region
	}

	private void bucket(UniforceJobHandlerWrapper wrapper) {
		UniforceJobHandler handler = wrapper.getHandler();
		if (handler != null) {
			if (!jobExists(handler)) {
				try {
					scheduler.scheduleJob(handler.createJobDetail(), handler.getTrigger());
					jobs.add(wrapper);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void unbucket(UniforceJobHandlerWrapper wrapper) {
		UniforceJobHandler handler = wrapper.getHandler();
		if (handler != null) {
			if (jobExists(handler)) {
				try {
					scheduler.deleteJob(handler.getName(), handler.getGroupName());
					jobs.remove(wrapper);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean jobExists(UniforceJobHandler handler) {
		boolean result = false;
		String theJobName = handler.getName();
		String theJobGroupName = handler.getGroupName();
		try {
			String[] jobGroupNames = scheduler.getJobGroupNames();
			for (String jobGroup : jobGroupNames) {
				String[] jobNames = scheduler.getJobNames(jobGroup);
				for (String jobName : jobNames) {
					if (jobName.equalsIgnoreCase(theJobName) && jobGroup.equalsIgnoreCase(theJobGroupName))
						return true;
				}
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}