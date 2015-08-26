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
import org.junit.BeforeClass;
import org.junit.Test;

public class ConnectionStringBuilderSingleServerTest {

    private static Injector injector;

    @BeforeClass
    public static void setupClass() {
        injector = Guice.createInjector(new CloudTagPropertiesModule("/cloudtag_single_server.properties"));
    }

    @Test
    public void shouldReturnSingleServerConnectionString() throws Exception {
        ConnectionStringBuilder connectionStringBuilder = injector.getInstance(ConnectionStringBuilder.class);

        String connectionString = connectionStringBuilder.getConnectionString();

        Assert.assertEquals("192.168.99.100:32773", connectionString);
    }

}
