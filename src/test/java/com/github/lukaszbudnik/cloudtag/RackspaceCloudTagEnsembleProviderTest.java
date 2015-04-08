package com.github.lukaszbudnik.cloudtag;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RackspaceCloudTagEnsembleProviderTest {

    private static Injector injector;

    @BeforeClass
    public static void setupClass() {
        injector = Guice.createInjector(new CloudTagPropertiesModule("/cloudtag_rackspace.properties"));
    }

    @Test
    public void shouldReturnConnectionString() throws Exception {
        CloudTagEnsembleProvider ensembleProvider = injector.getInstance(CloudTagEnsembleProvider.class);
        ensembleProvider.start();

        String connectionString = ensembleProvider.getConnectionString();

        Assert.assertNotNull(connectionString);
    }

}
