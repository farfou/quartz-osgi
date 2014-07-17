package com.wf.quartz.task.api;

import com.wf.quartz.task.context.TaskContext;

public abstract class Task {

	public Task() {
		
	}

	public abstract String getName();

	public String print() {
		return getName();
	}

	public void start() throws Exception {
		TaskContext context = null;
		try {
			context = TaskContext.newContext();
			execute(context);
			afterExecuted(context);
			afterTaskCompleted(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void execute(TaskContext context) throws Exception;

	public void afterExecuted(TaskContext context) {

	}

	public void afterTaskCompleted(TaskContext context) {

	}
}
