package com.alacriti.p2p.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartUpListener implements javax.servlet.ServletContextListener {
	public static void main(String[] args) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new TimeForDaemonProcess(), 0, 1,
				TimeUnit.HOURS);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}
}
