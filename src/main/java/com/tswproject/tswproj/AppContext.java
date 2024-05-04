package com.tswproject.tswproj;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContext implements ServletContextListener {
    private static final int POOL_SIZE = 5;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.init(POOL_SIZE);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.releaseResources();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
