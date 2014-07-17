package com.wf.quartz.job.task;

import com.wf.quartz.task.api.Task;
import com.wf.quartz.task.context.TaskContext;

public class DummyTask extends Task {

	public DummyTask() {
		super();
	}
	
	@Override
	public void execute(TaskContext context) throws Exception {
		System.out.println("[" + this.getClass().getSimpleName() + "] I am the simplest task based  cron job for quartz");
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
