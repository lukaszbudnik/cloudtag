/**
 * Copyright (C) 2015 Łukasz Budnik <lukasz.budnik@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
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
    private final Configuration configuration;

    private String connectionString;

    @Inject
    public AsyncCloudTagEnsembleProvider(Configuration configuration, ConnectionStringBuilder connectionStringBuilder) {
        this.connectionStringBuilder = connectionStringBuilder;
        this.configuration = configuration;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void start() throws Exception {
        connectionStringBuilder.start();
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                String connectionString = connectionStringBuilder.getConnectionString();
                AsyncCloudTagEnsembleProvider.this.setConnectionString(connectionString);
            }
        }, 0, configuration.getAsyncIntervalSeconds(), TimeUnit.SECONDS);
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
