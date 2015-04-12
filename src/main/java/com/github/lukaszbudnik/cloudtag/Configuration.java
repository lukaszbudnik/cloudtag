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

import com.google.inject.Inject;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class Configuration {

    public static final Boolean DEFAULT_USE_PUBLIC_IP = Boolean.FALSE;
    public static final Integer DEFAULT_ZOOKEEPER_PORT = 2181;
    public static final Integer DEFAULT_ASYNC_INTERVAL_SECONDS = 60;

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
    private Boolean usePublicIp = Boolean.FALSE;

    @Inject(optional = true)
    @Named("cloudtag.zookeeperPort")
    private Integer zookeeperPort = DEFAULT_ZOOKEEPER_PORT;

    @Inject(optional = true)
    @Named("cloudtag.asyncIntervalSeconds")
    private Integer asyncIntervalSeconds = DEFAULT_ASYNC_INTERVAL_SECONDS;

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
        return zookeeperPort;
    }

    public void setZookeeperPort(Integer zookeeperPort) {
        this.zookeeperPort = zookeeperPort;
    }

    public Integer getAsyncIntervalSeconds() {
        return asyncIntervalSeconds;
    }

    public void setAsyncIntervalSeconds(Integer asyncIntervalSeconds) {
        this.asyncIntervalSeconds = asyncIntervalSeconds;
    }

}