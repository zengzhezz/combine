package com.ibeacon.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ibeacon.service.person.UserService;
import com.ibeacon.thread.LineLocateThread;
import com.ibeacon.utils.SpringContextHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerStartListener implements ServletContextListener {




    @Override
    public void contextDestroyed(ServletContextEvent arg0) {


    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new LineLocateThread());
    }

}
