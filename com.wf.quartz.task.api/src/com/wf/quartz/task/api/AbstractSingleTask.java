package com.wf.quartz.task.api;

import com.wf.quartz.task.context.TaskContext;

public abstract class AbstractSingleTask implements SingleTask {

	public AbstractSingleTask() {
		
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String print() {
		return getName();
	}

	@Override
	public final void start() throws Exception {
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

	@Override
	public void afterExecuted(TaskContext context) {

	}

	@Override
	public void afterTaskCompleted(TaskContext context) {

	}
}
