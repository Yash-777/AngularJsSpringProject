package com.github.controllers;

import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

public class SpringStopWatch {
	public static void main(String[] args) throws InterruptedException {
		StopWatch sw = new org.springframework.util.StopWatch();
		sw.start("Method-1"); // Start a named task
			Thread.sleep(500);
		sw.stop();
		
		sw.start("Method-2");
			Thread.sleep(300);
		sw.stop();
		
		sw.start("Method-3");
			Thread.sleep(200);
		sw.stop();
		
		System.out.println("Total time in milliseconds for all tasks :\n"+sw.getTotalTimeMillis());
		System.out.println("Table describing all tasks performed :\n"+sw.prettyPrint());
		
		System.out.format("Time taken by the last task : [%s]:[%d]", 
				sw.getLastTaskName(),sw.getLastTaskTimeMillis());
		
		System.out.println("\n Array of the data for tasks performed « Task Name: Time Taken");
		TaskInfo[] listofTasks = sw.getTaskInfo();
		for (TaskInfo task : listofTasks) {
			System.out.format("[%s]:[%d]\n", 
					task.getTaskName(), task.getTimeMillis());
		}
	}
}
