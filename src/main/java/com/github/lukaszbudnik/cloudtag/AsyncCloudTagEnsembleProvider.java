package com.github.lukaszbudnik.cloudtag;

import org.apache.curator.ensemble.EnsembleProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class AsyncCloudTagEnsembleProvider implements EnsembleProvider {

    private final ConnectionStringBuilder connectionStringBuilder;
    private final ScheduledExecutorService scheduledExecutorService;

    private String connectionString;

    @Inject
    public AsyncCloudTagEnsembleProvider(ConnectionStringBuilder connectionStringBuilder) {
        this.connectionStringBuilder = connectionStringBuilder;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void start() throws Exception {
        connectionStringBuilder.start();
        // every 10 seconds the scheduled executor service will call this runnable
        // initial delay is 0
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                String connectionString = connectionStringBuilder.getConnectionString();
                AsyncCloudTagEnsembleProvider.this.setConnectionString(connectionString);
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    @Override
    public synchronized String getConnectionString() {
        return connectionString;
    }

    @Override
    public void close() throws IOException {
        scheduledExecutorService.shutdown();
    }

    private synchronized void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

}
