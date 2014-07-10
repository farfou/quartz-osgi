package com.wf.quartz.job.properties;

public interface QuartzTopics {
	
	public static final String ALL = "uniforce/quartz/job/*";
	
	public static final String SCHEDULED = "uniforce/quartz/job/scheduled";
	public static final String UNSCHEDULED = "uniforce/quartz/job/unscheduled";
	public static final String HEARTBEATH= "uniforce/quartz/job/heartBeath";
	public static final String EXECUTED = "uniforce/quartz/job/executed";
	
	

}
