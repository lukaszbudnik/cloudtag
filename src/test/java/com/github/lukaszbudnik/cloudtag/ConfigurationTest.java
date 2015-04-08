package com.github.lukaszbudnik.cloudtag;

import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void shouldHandleDefault() {
        Configuration configuration = new Configuration();

        Assert.assertEquals(Configuration.DEFAULT_ZOOKEEPER_PORT, configuration.getZookeeperPort());
        Assert.assertFalse(configuration.useQualifier());
        Assert.assertFalse(configuration.usePublicIp());
    }

    @Test
    public void shouldReturnUsePublicCorrectly() {
        Configuration configuration = new Configuration();

        Assert.assertFalse(configuration.usePublicIp());

        configuration.setUsePublicIp(false);

        Assert.assertFalse(configuration.usePublicIp());

        configuration.setUsePublicIp(true);

        Assert.assertTrue(configuration.usePublicIp());
    }

    @Test
    public void shouldReturnUseQualifierCorrectly() {
        Configuration configuration = new Configuration();

        Assert.assertFalse(configuration.useQualifier());

        configuration.setQualifierName("name");

        Assert.assertFalse(configuration.useQualifier());

        configuration.setQualifierValue("value");

        Assert.assertTrue(configuration.useQualifier());
    }

}
