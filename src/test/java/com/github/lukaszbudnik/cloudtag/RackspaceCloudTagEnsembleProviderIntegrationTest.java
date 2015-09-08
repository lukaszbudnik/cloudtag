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

import com.github.lukaszbudnik.gpe.PropertiesElResolverModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("You need valid Rackspace credentials to run this integration test")
public class RackspaceCloudTagEnsembleProviderIntegrationTest {

    private static Injector injector;

    @BeforeClass
    public static void setupClass() throws Exception {
        injector = Guice.createInjector(new PropertiesElResolverModule("/cloudtag_rackspace.properties"));
    }

    @Test
    public void shouldReturnConnectionString() throws Exception {
        CloudTagEnsembleProvider ensembleProvider = injector.getInstance(CloudTagEnsembleProvider.class);
        ensembleProvider.start();

        String connectionString = ensembleProvider.getConnectionString();

        Assert.assertNotNull(connectionString);
    }

}
