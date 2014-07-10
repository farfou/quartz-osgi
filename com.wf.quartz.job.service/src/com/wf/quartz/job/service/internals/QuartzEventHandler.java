package com.wf.quartz.job.service.internals;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.wf.quartz.job.properties.QuartzEventAttributes;

public class QuartzEventHandler implements EventHandler {

	private static final String EMPTY_STRING = "";
	private static String FORMAT = "[%s] - %s - %s - %s";

	public void handleEvent(Event event) {
		String job = event.getProperty(QuartzEventAttributes.JOB).toString();
		String jobString = (job == null ? EMPTY_STRING : job);
		Object property = event.getProperty(QuartzEventAttributes.TRIGGER);
		String trigger = (property == null ? "" : property.toString());
		String triggerString = (trigger == null ? EMPTY_STRING : trigger);
		String time = event.getProperty(QuartzEventAttributes.TIME).toString();
		String timeString = (time == null ? EMPTY_STRING : time);

		String format2 = String.format(FORMAT, event.getTopic(), jobString, triggerString, timeString);
		System.out.println(format2);
	}

}
