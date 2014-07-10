package com.wf.quartz.job.cron;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import com.wf.quartz.job.UniforceJobHandler;

public class CronJob implements UniforceJobHandler {

	@Override
	public String getName() {
		return "simpleCronJob";
	}

	@Override
	public String getGroupName() {
		return "simpleCronGroup";
	}

	@Override
	public Trigger getTrigger() {
		CronTrigger trigger = new CronTrigger();
		trigger.setName("cronTrigger");
		long endTimeAsLong = System.currentTimeMillis() + 6000l;
		trigger.setEndTime(new Date(endTimeAsLong));
		try {
			trigger.setCronExpression("0/3 * * * * ?");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return trigger;
	}

	@Override
	public JobDetail createJobDetail() {
		JobDetail job = new JobDetail();
		job.setName(getName());
		job.setGroup(getGroupName());
		job.setJobClass(this.getClass());
		return job;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("[" + this.getClass().getSimpleName() + "] I am the simplest job for quartz");
	}
}
