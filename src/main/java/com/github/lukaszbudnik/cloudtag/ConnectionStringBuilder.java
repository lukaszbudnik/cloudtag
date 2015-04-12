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

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;

import java.util.Map;
import java.util.Set;

public class ConnectionStringBuilder {

    private Configuration configuration;

    private ComputeService computeService;

    @Inject
    public ConnectionStringBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        ComputeServiceContext computeServiceContext = ContextBuilder.newBuilder(configuration.getProvider()).credentials(configuration.getIdentity(), configuration.getCredential()).build(ComputeServiceContext.class);
        computeService = computeServiceContext.getComputeService();
    }

    // this method is mocked in unit tests
    Set<? extends ComputeMetadata> listNodes() {
        return computeService.listNodes();
    }

    public String getConnectionString() {
        Set<? extends ComputeMetadata> nodes = listNodes();
        FluentIterable<String> connectionStrings = FluentIterable
                .from(nodes)
                .filter(new Predicate<ComputeMetadata>() {
                    @Override
                    public boolean apply(ComputeMetadata input) {
                        return input instanceof NodeMetadata;
                    }
                })
                .transform(new Function<ComputeMetadata, NodeMetadata>() {
                    @Override
                    public NodeMetadata apply(ComputeMetadata input) {
                        return (NodeMetadata) input;
                    }
                })
                .filter(new Predicate<NodeMetadata>() {
                    @Override
                    public boolean apply(NodeMetadata input) {
                        Map<String, String> metadata = input.getUserMetadata();
                        return isTagMatch(metadata) && isQualifierMatch(metadata);
                    }
                })
                .transform(new Function<NodeMetadata, String>() {
                    @Override
                    public String apply(NodeMetadata input) {
                        String ip;
                        if (Boolean.TRUE.equals(configuration.getUsePublicIp()) && input.getPublicAddresses().iterator().hasNext()) {
                            ip = input.getPublicAddresses().iterator().next();
                        } else {
                            ip = input.getPrivateAddresses().iterator().next();
                        }
                        return ip + ":" + configuration.getZookeeperPort();
                    }
                });

        String connectionString = Joiner.on(',').join(connectionStrings);

        return connectionString;
    }

    boolean isTagMatch(Map<String, String> metadata) {
        return metadata.containsKey(configuration.getTagName()) && metadata.get(configuration.getTagName()).equals(configuration.getTagValue());
    }

    boolean isQualifierMatch(Map<String, String> metadata) {
        return !configuration.useQualifier() || (configuration.useQualifier() && metadata.containsKey(configuration.getQualifierName()) && metadata.get(configuration.getQualifierName()).equals(configuration.getQualifierValue()));
    }
}