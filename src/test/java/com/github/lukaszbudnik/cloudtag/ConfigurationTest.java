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
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void shouldReturnDefaults() {
        Configuration configuration = new Configuration();

        Assert.assertEquals(Configuration.DEFAULT_ZOOKEEPER_PORT, configuration.getZookeeperPort());
        Assert.assertEquals(Configuration.DEFAULT_USE_PUBLIC_IP, configuration.getUsePublicIp());
        Assert.assertEquals(Configuration.DEFAULT_ASYNC_INTERVAL_SECONDS, configuration.getAsyncIntervalSeconds());
        Assert.assertFalse(configuration.useQualifier());
    }

    @Test
    public void shouldReturnDefaultsWhenCreatedUsingInjector() throws Exception {
        Injector injector = Guice.createInjector(new PropertiesElResolverModule("/cloudtag_without_optionals.properties"));

        Configuration configuration = injector.getInstance(Configuration.class);

        Assert.assertEquals(Configuration.DEFAULT_ZOOKEEPER_PORT, configuration.getZookeeperPort());
        Assert.assertEquals(Configuration.DEFAULT_USE_PUBLIC_IP, configuration.getUsePublicIp());
        Assert.assertEquals(Configuration.DEFAULT_ASYNC_INTERVAL_SECONDS, configuration.getAsyncIntervalSeconds());
        Assert.assertFalse(configuration.useQualifier());
    }

    @Test
    public void shouldReturnUseQualifierCorrectly() {
        Configuration configuration = new Configuration();

        Assert.assertFalse(configuration.useQualifier());

        configuration.setQualifierName("name");

        Assert.assertFalse(configuration.useQualifier());

        configuration.setQualifierValue("value");

        // use qualifier returns true only if both qualifier name and value is set
        Assert.assertTrue(configuration.useQualifier());
    }

}
