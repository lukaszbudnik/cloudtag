# CloudTag
CloudTag is a Curator ensemble provider which returns connection string based on cloud servers with matching tags.

The idea is simple, CloudTag lists all nodes in your cloud and, based on tag name and its value, filters nodes which
are ZooKeeper nodes and constructs a connection string.

The minimal configuration looks like this:

```
cloudtag.provider=aws-ec2
cloudtag.identity=ABCXXXXXXXXXXXXX
cloudtag.credential=DEFYYYYYYYYYYY
cloudtag.tagName=Name
cloudtag.tagValue=NAT01
```

# EnsembleProvider implementations

There are two Curator's `EnsembleProvider` implementations:

 * `CloudTagEnsembleProvider` - a call to `getConnectionString` method is synchronous
 * `AsynCloudTagEnsembleProvider` - uses `ScheduledExecutorService` under the hood to periodically generate
a connection string which then is returned by `getConnectionString` in a non-blocking way

# Examples

Please refer to `src/test/java` for examples.

CloudTag was tested using jcloud's `aws-ec2` and `rackspace-cloudservers-uk`/`rackspace-cloudservers-us` providers.

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

