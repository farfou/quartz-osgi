package com.wf.quartz.task.api;

import com.wf.quartz.task.context.TaskContext;

public abstract class MultipleTask  extends Task {

	private static final String TASK_SEPERATOR = "->";
	private MultipleTask nextTask;
	private MultipleTask previousTask;

	public MultipleTask() {
		setNextTask(null);
		setPreviousTask(null);
	}
	
	public MultipleTask(MultipleTask task) {
		addTask(task);
	}

	public String print() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getName());
		if (nextTask != null) {
			buffer.append(MultipleTask.TASK_SEPERATOR);
			buffer.append(nextTask.print());
		}
		return buffer.toString();
	}

	@Override
	public void start() throws Exception {
		if (this.previousTask == null) {
			TaskContext context = TaskContext.newContext();
			try {
				execute0(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new Exception("Zincir sadece en basindan baslatilabilir");
		}
	}

	protected void execute0(TaskContext context) throws Exception {
		execute(context);
		afterExecuted(context);
		continueTask(context);
	}

	@Override
	public void afterExecuted(TaskContext context) {

	}

	@Override
	public void afterTaskCompleted(TaskContext context) {

	}

	protected void continueTask(TaskContext context) {
		if (nextTask != null) {
			try {
				((MultipleTask) nextTask).execute0(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			afterTaskCompleted(context);
		}
	}

	public MultipleTask getNextTask() {
		return this.nextTask;
	}

	public void addTask(MultipleTask task) {
		setNextTask((MultipleTask) task);
	}

	public void setNextTask(MultipleTask nextTask) {
		this.nextTask = nextTask;
		if (this.nextTask != null)
			this.nextTask.setPreviousTask(this);
	}

	public MultipleTask getPreviousTask() {
		return this.previousTask;
	}

	public void setPreviousTask(MultipleTask prevTask) {
		this.previousTask = prevTask;
	}

	public MultipleTask getParent() {
		if (this.previousTask == null)
			return this;
		return this.previousTask.getParent();
	}

}
