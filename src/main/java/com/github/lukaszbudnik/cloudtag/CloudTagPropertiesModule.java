package com.github.lukaszbudnik.cloudtag;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

public class CloudTagPropertiesModule implements Module {

    public static final String DEFAULT_CLOUDTAG_PROPERTIES = "/cloudtag.properties";

    private final String cloudTagProperties;

    public CloudTagPropertiesModule() {
        this(DEFAULT_CLOUDTAG_PROPERTIES);
    }

    public CloudTagPropertiesModule(String cloudTagProperties) {
        this.cloudTagProperties = cloudTagProperties;
    }

    @Override
    public void configure(Binder binder) {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream(cloudTagProperties));
            Names.bindProperties(binder, properties);
        } catch (IOException e) {
            // if a properties file is not found this module will do nothing
            // later on injector will fail if named dependencies will not be resolved
            // so no panic :)
        }
    }
}
