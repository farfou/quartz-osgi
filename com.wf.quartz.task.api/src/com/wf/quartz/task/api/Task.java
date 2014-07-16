package com.wf.quartz.task.api;

import com.wf.quartz.task.context.TaskContext;

public interface Task {

	public String getName();

	public String print();

	public void start() throws Exception;

	public void execute(TaskContext context) throws Exception;

	public void afterExecuted(TaskContext context);

	public void afterTaskCompleted(TaskContext context);

}
