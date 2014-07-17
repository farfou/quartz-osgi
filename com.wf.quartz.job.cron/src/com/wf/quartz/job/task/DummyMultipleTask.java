package com.wf.quartz.job.task;

import com.wf.quartz.task.api.MultipleTask;
import com.wf.quartz.task.context.TaskContext;

public class DummyMultipleTask extends MultipleTask {

	public DummyMultipleTask() {
		
	}
	
	public DummyMultipleTask(MultipleTask task) {
		super(task);
	}
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void execute(TaskContext context) throws Exception {
		System.out.println(getName() + " executing");
	}

	
}
