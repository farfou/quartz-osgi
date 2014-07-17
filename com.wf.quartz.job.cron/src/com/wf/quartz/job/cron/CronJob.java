package com.wf.quartz.job.cron;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import com.wf.quartz.job.task.DummyMultipleTask;
import com.wf.quartz.task.api.MultipleTask;
import com.wf.quartz.task.api.Task;
import com.wf.quartz.task.context.TaskContext;
import com.wf.quartz.task.job.ScheduledTask;

public class CronJob extends ScheduledTask {

	private static MultipleTask task = new DummyMultipleTask(new MultipleTask() {

		@Override
		public String getName() {
			return "InnerTask";
		}

		@Override
		public void execute(TaskContext context) throws Exception {
			System.out.println(getName() + " executing");
		}
	});

	public CronJob() {
		super(task);
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
	public Task getTask() {

		return task;
	}

}
