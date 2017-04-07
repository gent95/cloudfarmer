package com.jctl.cloud.manager.tets;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class Job1 implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//
//		System.out.println("Hello, Quartz! - executing its JOB at "+
//	            new Date() + " by " + context.getTrigger().getName());
	}
	
}
