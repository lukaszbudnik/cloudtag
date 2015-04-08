package com.github.lukaszbudnik.cloudtag;

import org.apache.curator.ensemble.EnsembleProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class CloudTagEnsembleProvider implements EnsembleProvider {

    private final ConnectionStringBuilder connectionStringBuilder;

    @Inject
    public CloudTagEnsembleProvider(ConnectionStringBuilder connectionStringBuilder) {
        this.connectionStringBuilder = connectionStringBuilder;
    }

    @Override
    public void start() throws Exception {
        connectionStringBuilder.start();
    }

    @Override
    public synchronized String getConnectionString() {
        return connectionStringBuilder.getConnectionString();
    }

    @Override
    public void close() throws IOException {
    }
}
