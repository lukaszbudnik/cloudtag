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

import java.util.Set;

public class CloudTagEnsembleProviderTest {

    private static Injector injector;

    @BeforeClass
    public static void setupClass() {
        injector = Guice.createInjector(new CloudTagPropertiesModule("/cloudtag_without_optionals.properties"), new AbstractModule() {
            @Override
            protected void configure() {
                requestInjection(this);
            }

            @Inject
            private Configuration configuration;

            @Provides
            public ConnectionStringBuilder connectionStringBuilder() {
                NodeMetadata nodeMetadata1 = Mockito.mock(NodeMetadata.class);
                Mockito.when(nodeMetadata1.getUserMetadata()).thenReturn(ImmutableMap.of(configuration.getTagName(), configuration.getTagValue()));
                Mockito.when(nodeMetadata1.getPublicAddresses()).thenReturn(ImmutableSet.of("123.123.123.123"));
                Mockito.when(nodeMetadata1.getPrivateAddresses()).thenReturn(ImmutableSet.of("10.0.0.123"));

                NodeMetadata nodeMetadata2 = Mockito.mock(NodeMetadata.class);
                Mockito.when(nodeMetadata2.getUserMetadata()).thenReturn(ImmutableMap.of(configuration.getTagName(), configuration.getTagValue()));
                Mockito.when(nodeMetadata2.getPublicAddresses()).thenReturn(ImmutableSet.of("124.124.124.124"));
                Mockito.when(nodeMetadata2.getPrivateAddresses()).thenReturn(ImmutableSet.of("10.0.0.124"));

                final Set nodes = Sets.newHashSet(nodeMetadata1, nodeMetadata2);

                ConnectionStringBuilder connectionStringBuilder = new ConnectionStringBuilder(configuration) {
                    Set<? extends ComputeMetadata> listNodes() {
                        return nodes;
                    }
                };

                return connectionStringBuilder;
            }
        });
    }

    @Test
    public void shouldReturnConnectionString() throws Exception {
        CloudTagEnsembleProvider ensembleProvider = injector.getInstance(CloudTagEnsembleProvider.class);
        ensembleProvider.start();

        String connectionString = ensembleProvider.getConnectionString();

        ensembleProvider.close();

        Assert.assertTrue(connectionString.contains("10.0.0.123:2181"));
        Assert.assertTrue(connectionString.contains("10.0.0.124:2181"));
    }

}
