package com.wf.quartz.task.api;


public interface MultipleTask extends SingleTask {

	public static final String TASK_SEPERATOR = " -> ";
	
	public MultipleTask getNextTask();
	
	public void setNextTask(MultipleTask nextTask);
	
	public MultipleTask getPreviousTask();
	
	public void setPreviousTask(MultipleTask prevTask);
	
	public MultipleTask getParent();
	

}
