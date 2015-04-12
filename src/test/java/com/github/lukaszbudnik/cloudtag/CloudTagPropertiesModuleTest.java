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

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class CloudTagPropertiesModuleTest {

    public static final String CLOUDTAG_PROPERTIES = "/cloudtag.properties";
    public static final String CLOUDTAG_WITHOUT_OPTIONALS_PROPERTIES = "/cloudtag_without_optionals.properties";

    @Test
    public void shouldNotFailWhenPropertiesResourceDoesNotExist() throws IOException {
        Injector injector = Guice.createInjector(new CloudTagPropertiesModule("/dev/null"));
        Assert.assertNotNull(injector);
    }

    @Test
    public void shouldInjectNamedDependenciesFromProperties() throws IOException {

        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(CLOUDTAG_PROPERTIES));

        Injector injector = Guice.createInjector(new CloudTagPropertiesModule());
        Configuration configuration = injector.getInstance(Configuration.class);

        Assert.assertEquals(properties.getProperty("cloudtag.provider"), configuration.getProvider());
        Assert.assertEquals(properties.getProperty("cloudtag.identity"), configuration.getIdentity());
        Assert.assertEquals(properties.getProperty("cloudtag.credential"), configuration.getCredential());
        Assert.assertEquals(properties.getProperty("cloudtag.tagName"), configuration.getTagName());
        Assert.assertEquals(properties.getProperty("cloudtag.tagValue"), configuration.getTagValue());
        Assert.assertEquals(properties.getProperty("cloudtag.qualifierName"), configuration.getQualifierName());
        Assert.assertEquals(properties.getProperty("cloudtag.qualifierValue"), configuration.getQualifierValue());
        Assert.assertEquals(properties.getProperty("cloudtag.usePublicIp"), configuration.getUsePublicIp().toString());
        Assert.assertEquals(properties.getProperty("cloudtag.zookeeperPort"), configuration.getZookeeperPort().toString());
    }

    @Test
    public void shouldInjectNamedDependenciesFromPropertiesAndNotFailOnOptionals() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(CLOUDTAG_WITHOUT_OPTIONALS_PROPERTIES));

        Injector injector = Guice.createInjector(new CloudTagPropertiesModule(CLOUDTAG_WITHOUT_OPTIONALS_PROPERTIES));
        Configuration configuration = injector.getInstance(Configuration.class);

        Assert.assertEquals(properties.getProperty("cloudtag.provider"), configuration.getProvider());
        Assert.assertEquals(properties.getProperty("cloudtag.identity"), configuration.getIdentity());
        Assert.assertEquals(properties.getProperty("cloudtag.credential"), configuration.getCredential());
        Assert.assertEquals(properties.getProperty("cloudtag.tagName"), configuration.getTagName());
        Assert.assertEquals(properties.getProperty("cloudtag.tagValue"), configuration.getTagValue());
        Assert.assertEquals(Configuration.DEFAULT_ZOOKEEPER_PORT, configuration.getZookeeperPort());
        Assert.assertEquals(Configuration.DEFAULT_USE_PUBLIC_IP, configuration.getUsePublicIp());
        Assert.assertEquals(Configuration.DEFAULT_ASYNC_INTERVAL_SECONDS, configuration.getAsyncIntervalSeconds());
        Assert.assertNull(configuration.getQualifierName());
        Assert.assertNull(configuration.getQualifierValue());
        Assert.assertFalse(configuration.useQualifier());
    }

}
