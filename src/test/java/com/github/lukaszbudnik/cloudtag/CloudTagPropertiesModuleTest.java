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
        Assert.assertNull(configuration.getQualifierName());
        Assert.assertNull(configuration.getQualifierValue());
        Assert.assertNull(configuration.getUsePublicIp());
        Assert.assertEquals(Configuration.DEFAULT_ZOOKEEPER_PORT, configuration.getZookeeperPort());
    }

}
