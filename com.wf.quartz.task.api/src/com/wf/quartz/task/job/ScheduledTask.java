package com.wf.quartz.task.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wf.quartz.job.QuartzJob;
import com.wf.quartz.task.api.Task;

public abstract class ScheduledTask implements QuartzJob {

	private Task task;

	public ScheduledTask() {
		this(null);
	}
	
	public ScheduledTask(Task task) {
		setTask(task);
	}

	@Override
	public String getName() {
		return getTask().print();
	}
	
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			getTask().start();
		} catch (Exception e) {
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
