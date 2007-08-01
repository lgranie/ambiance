The directory test/resources contains all the resources that your unit tests need.
When you run unit test on component, Plexus need a file to load your component.
This file is named <YourComponentTest>.xml and place in the directory corresponding 
to the path of you component class.

The pom.xml file from the parent project ambiance-components uses Plexus
Component Descriptor Creator (http://plexus.codehaus.org/guides/quick-start/component-descriptor-creator.html).
This tool creates the components.xml of your component from annotations in your java code.
The file is create in the directory target/