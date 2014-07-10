package com.wf.quartz.job.service.internals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;

import com.wf.quartz.job.properties.QuartzEventAttributes;
import com.wf.quartz.job.properties.QuartzTopics;

public class CustomSchedulerListener implements SchedulerListener {

	private EventAdmin eventAdmin = null;
	private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
	
	public CustomSchedulerListener(EventAdmin anEventAdmin) {
		setEventAdmin(anEventAdmin);
	}
	
	public void sendEvent(String topic, Trigger trigger) {
		if (getEventAdmin() != null) {
			getEventAdmin().sendEvent(createEvent(topic, createProperties(trigger)));
		} else {
			System.out.println("EVENT ADMIN YOKKKK");
		}
	}
	
	private Event createEvent(String topic, Dictionary<String, Object> properties) {
		return new Event(topic, properties);
	}
	
	private Dictionary<String, Object> createProperties(Trigger trigger) {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(QuartzEventAttributes.JOB, trigger.getFullJobName());
		properties.put(QuartzEventAttributes.TIME, dateToString(new Date()));
		return properties;
	}
	
	private String dateToString(Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}
	
	public void jobScheduled(Trigger trigger) {
		sendEvent(QuartzTopics.SCHEDULED, trigger);
		
	}

	public void jobUnscheduled(String arg0, String arg1) {
		System.out.println(arg0 + "." + arg1 + " jobUnscheduled");
	}

	public void jobsPaused(String arg0, String arg1) {
		System.out.println(arg0 + "." + arg1 + " jobsPaused");
	}

	public void jobsResumed(String arg0, String arg1) {
		System.out.println(arg0 + "." + arg1 + " jobsResumed");
	}

	public void schedulerError(String arg0, SchedulerException arg1) {
		// TODO Auto-generated method stub
		
	}

	public void schedulerShutdown() {
		// TODO Auto-generated method stub
		
	}

	
	public void triggerFinalized(Trigger trigger) {
		sendEvent(QuartzTopics.EXECUTED, trigger);
	}

	
	public void triggersPaused(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void triggersResumed(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}
