package com.wf.quartz.task.api;

import com.wf.quartz.task.context.TaskContext;

public abstract class AbstractMultipleTask implements MultipleTask {

	private MultipleTask nextTask;
	private MultipleTask previousTask;

	public AbstractMultipleTask() {
		setNextTask(null);
		setPreviousTask(null);
	}

	public AbstractMultipleTask(MultipleTask nextTask) {
		setNextTask(nextTask);
	}

	@Override
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
				((AbstractMultipleTask) nextTask).execute0(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			afterTaskCompleted(context);
		}
	}

	@Override
	public MultipleTask getNextTask() {
		return this.nextTask;
	}

	@Override
	public void setNextTask(MultipleTask nextTask) {
		this.nextTask = nextTask;
		if (this.nextTask != null)
			this.nextTask.setPreviousTask(this);
	}

	@Override
	public MultipleTask getPreviousTask() {
		return this.previousTask;
	}

	@Override
	public void setPreviousTask(MultipleTask prevTask) {
		this.previousTask = prevTask;
	}

	@Override
	public MultipleTask getParent() {
		if (this.previousTask == null)
			return this;
		return this.previousTask.getParent();
	}

}
