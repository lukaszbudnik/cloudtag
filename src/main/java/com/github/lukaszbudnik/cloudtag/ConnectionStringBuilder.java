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

    public String getConnectionString() {
        Set<? extends ComputeMetadata> nodes = computeService.listNodes();
        FluentIterable<String> connectionStrings = FluentIterable
                .from(nodes)
                .filter(NodeMetadata.class)
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
                        if (configuration.usePublicIp() && input.getPublicAddresses().iterator().hasNext()) {
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