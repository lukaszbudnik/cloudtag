# CloudTag [![Build Status](https://travis-ci.org/lukaszbudnik/cloudtag.svg?branch=master)](https://travis-ci.org/lukaszbudnik/cloudtag) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lukaszbudnik.cloudtag/cludtag/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.lukaszbudnik.cloudtag/cloudtag)

CloudTag is a Curator's `EnsembleProvider` implementation which returns a connection string based on cloud servers with matching tags.

The idea is simple, CloudTag lists all nodes in your cloud and, based on tag name and its value, filters nodes which
are ZooKeeper nodes and constructs a connection string.

Minimal configuration looks like this:

```
cloudtag.provider=aws-ec2
cloudtag.identity=ABCXXXXXXXXXXXXX
cloudtag.credential=DEFYYYYYYYYYYY
cloudtag.tagName=Role
cloudtag.tagValue=NAT
```

If you have more servers and simple tag-based filtering does not work for you, you can enable qualifiers.
Qualifiers can be treated as a second dimension filter. An example could include filtering by environment
(using qualifier) and machine type (using tag):

```
cloudtag.qualifierName=Environment
cloudtag.qualifierValue=EUProd
cloudtag.tagName=Role
cloudtag.tagValue=NAT
```

For more configuration options please refer to examples.

# Cloud providers

CloudTag is implemented using Apache jclouds library. The following jclouds providers were tested (see integration tests):

* `aws-ec2`
* `rackspace-cloudservers-uk`
* `rackspace-cloudservers-us`

CloudTag is using jclouds' `UserMetaData` class for both qualifier and tag filtering. I'm sure you already know it,
but just in case, please mind that jclouds' `UserMetaData` is mapped to different things for different clouds:

* AWS - tags
* Open Stack-compliant (like Rackspace) - meta data

In theory every jclouds provider will work with CloudTag, but I didn't test them all.

# Test/dev/local provider

You can also setup CloudTag for a test/dev/local environment using a single server provider configuration:

```
cloudtag.provider=single-server
cloudtag.identity=ignored
cloudtag.credential=ignored
cloudtag.tagName=ignored
cloudtag.tagValue=ignored
cloudtaq.singleServer.address=192.168.99.100
cloudtag.singleServer.port=32773
```

# EnsembleProvider implementations

There are two Curator's `EnsembleProvider` implementations:

 * `CloudTagEnsembleProvider` - a call to `getConnectionString` is synchronous and blocking
 * `AsynCloudTagEnsembleProvider` - uses `ScheduledExecutorService` under the hood to periodically generate
a connection string which then is returned by `getConnectionString` in a non-blocking way

# Examples

Please refer to `src/test/java` for examples.

# Download

Use the following Maven dependency:

```xml
<dependency>
  <groupId>com.github.lukaszbudnik.cloudtag</groupId>
  <artifactId>cloudtag</artifactId>
  <version>{version}</version>
</dependency>
```

or open [search.maven.org](http://search.maven.org/#search|ga|1|com.github.lukaszbudnik.cloudtag)
and copy and paste dependency id for your favourite dependency management tool.


# License

Copyright 2015 Łukasz Budnik

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

