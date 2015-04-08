package com.github.lukaszbudnik.cloudtag;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AsyncCloudTagEnsembleProviderTest {

    private static Injector injector;

    @BeforeClass
    public static void setupClass() {
        injector = Guice.createInjector(new CloudTagPropertiesModule());
    }

    @Test
    public void shouldReturnConnectionString() throws Exception {
        AsyncCloudTagEnsembleProvider ensembleProvider = injector.getInstance(AsyncCloudTagEnsembleProvider.class);
        ensembleProvider.start();

        String connectionString = ensembleProvider.getConnectionString();

        // listing all nodes usually takes some time and first call returns null
        Assert.assertNull(connectionString);

        // give the async cloud tag ensemble provider some time...
        // for my AWS cloud env it really takes that long...
        Thread.sleep(60 * 1000);

        // and check now, the connection string should be already set in the background
        connectionString = ensembleProvider.getConnectionString();
        Assert.assertNotNull(connectionString);
    }

}
