package com.github.lukaszbudnik.cloudtag;

import com.google.inject.Inject;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class Configuration {

    public static Integer DEFAULT_ZOOKEEPER_PORT = 2181;

    @Inject
    @Named("cloudtag.provider")
    private String provider;

    @Inject
    @Named("cloudtag.identity")
    private String identity;

    @Inject
    @Named("cloudtag.credential")
    private String credential;

    @Inject
    @Named("cloudtag.tagName")
    private String tagName;

    @Inject
    @Named("cloudtag.tagValue")
    private String tagValue;

    @Inject(optional = true)
    @Named("cloudtag.qualifierName")
    private String qualifierName;

    @Inject(optional = true)
    @Named("cloudtag.qualifierValue")
    private String qualifierValue;

    @Inject(optional = true)
    @Named("cloudtag.usePublicIp")
    private Boolean usePublicIp;

    @Inject(optional = true)
    @Named("cloudtag.zookeeperPort")
    private Integer zookeeperPort;

    public boolean usePublicIp() {
        return Boolean.TRUE.equals(usePublicIp);
    }

    public boolean useQualifier() {
        return qualifierName != null && !qualifierName.isEmpty()
                && qualifierValue != null && !qualifierValue.isEmpty();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getQualifierName() {
        return qualifierName;
    }

    public void setQualifierName(String qualifierName) {
        this.qualifierName = qualifierName;
    }

    public String getQualifierValue() {
        return qualifierValue;
    }

    public void setQualifierValue(String qualifierValue) {
        this.qualifierValue = qualifierValue;
    }

    public Boolean getUsePublicIp() {
        return usePublicIp;
    }

    public void setUsePublicIp(Boolean usePublicIp) {
        this.usePublicIp = usePublicIp;
    }

    public Integer getZookeeperPort() {
        return zookeeperPort != null ? zookeeperPort : DEFAULT_ZOOKEEPER_PORT;
    }

    public void setZookeeperPort(Integer zookeeperPort) {
        this.zookeeperPort = zookeeperPort;
    }
}