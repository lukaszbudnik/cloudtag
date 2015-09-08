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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.*;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Set;

public class AsyncCloudTagEnsembleProviderTest {

    private static Injector injector;

    @BeforeClass
    public static void setupClass() throws Exception {
        injector = Guice.createInjector(new PropertiesElResolverModule("/cloudtag.properties"), new AbstractModule() {
            @Override
            protected void configure() {
                requestInjection(this);
            }

            @Inject
            private Configuration configuration;

            @Provides
            public ConnectionStringBuilder connectionStringBuilder() {
                NodeMetadata nodeMetadata1 = Mockito.mock(NodeMetadata.class);
                Mockito.when(nodeMetadata1.getUserMetadata()).thenReturn(ImmutableMap.of(configuration.getTagName(),
                        configuration.getTagValue(), configuration.getQualifierName(), configuration.getQualifierValue()));
                Mockito.when(nodeMetadata1.getPublicAddresses()).thenReturn(ImmutableSet.of("123.123.123.123"));

                NodeMetadata nodeMetadata2 = Mockito.mock(NodeMetadata.class);
                Mockito.when(nodeMetadata2.getUserMetadata()).thenReturn(ImmutableMap.of(configuration.getTagName(),
                        configuration.getTagValue(), configuration.getQualifierName(), configuration.getQualifierValue()));
                Mockito.when(nodeMetadata2.getPublicAddresses()).thenReturn(ImmutableSet.of("124.124.124.124"));

                final Set nodes = Sets.newHashSet(nodeMetadata1, nodeMetadata2);

                ConnectionStringBuilder connectionStringBuilder = new ConnectionStringBuilder(configuration) {
                    private int counter = 0;

                    Set<? extends ComputeMetadata> listNodes() {
                        if (counter == 0) {
                            counter = counter + 1;
                            return Collections.emptySet();
                        }
                        return nodes;
                    }
                };

                return connectionStringBuilder;
            }
        });
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
        Thread.sleep(10 * 1000);

        // and check now, the connection string should be already set in the background
        connectionString = ensembleProvider.getConnectionString();

        ensembleProvider.close();

        Assert.assertTrue(connectionString.contains("123.123.123.123:2181"));
        Assert.assertTrue(connectionString.contains("124.124.124.124:2181"));
    }

}
