package com.tswproject.tswproj;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebListener
public class AppContext implements ServletContextListener {
    private static final int POOL_SIZE = 5;
    private static final Logger logger = Logger.getLogger(AppContext.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.init(POOL_SIZE);
        logger.info("Attenzione mondo");
        logger.warning("Sto usando il logger");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.releaseResources();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
