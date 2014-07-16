package com.wf.quartz.task.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TaskContext {

	private Map<String, Object> values = null;

	public TaskContext() {
		values = new HashMap<String, Object>();
		add("startTime", System.currentTimeMillis());
	}

	public Set<String> getContextKeys() {
		return values.keySet();
	}

	public Object getContextObject(String key) {
		if (values != null && values.size() > 0 && values.containsKey(key))
			return values.get(key);
		return null;
	}

	public void add(String key, Object object) {
		this.values.put(key, object);
	}

	public static TaskContext newContext() {
		return new TaskContext();
	}

}
