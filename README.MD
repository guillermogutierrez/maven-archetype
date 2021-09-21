# Archetype PoC

Maven archetype created from the repo https://github.com/amido/stacks-java

## Steps done

1. Clone the repo
2. Create a pom.xml file in the root folder. The archetype plugin needs a pom.xml file to create the initial template.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.amido</groupId>
    <artifactId>stacks-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <name>stacks-api-archetype</name>
    <description>Archetype project for Java</description>

    <properties>
        <java.version>11</java.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.projectreactor</groupId>
                <artifactId>reactor-bom</artifactId>
                <version>2020.0.8</version>
                <type>pom</type>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```

3. Rename Java folder to Source (Java folder in the project structure got ignored by the mvn archetype:
   create-from-project command)
4. Create a property file archetype.properties

```properties
# package from the original code to be replaced
package=com.amido.stacks 
# groupId from the original code to be replaced
groupId=com.amido
# Artiufact ID from the original code to be replaced
artifactId=stacks
# Custom property. Each appearence of /v1/menu% will get replaced with the ${apiPath} variable
apiPath=/v1/menu%
```

5. Create the archetype template

```shell
mvn archetype:create-from-project -DpropertyFile='./archetype.properties
```

6. Copy the Dockerfile from the source folder into the archetype (For some reason, the archetype plugin ignores files
   without extension)

```shell
cp ./source/Dockerfile ./target/generated-sources/archetype/src/main/resources/archetype-resources/source/Dockerfile
```

7. Edit the Dockerfile file and include a placeholder to the new package

Replace

```docker
ENTRYPOINT ["java","-cp","app:app/lib/*","com.amido.stacks.Application"]
```

with

```docker
ENTRYPOINT ["java","-cp","app:app/lib/*","${package}.Application"]
```

8. (optional) Rename back the source folder from step 3 to Java. The archetype-metadata.xml file must be updated to,
   please replace any references to the source folder with java
9. Include the Dockerfile in the archetype-metadata.xml file located in

```shell 
./target/generated-sources/archetype/src/main/resources/META-INF/maven/archetype-metadata.xml
```

```xml

<fileSet filtered="true" encoding="UTF-8">
    <directory>java</directory>
    <includes>
        <include>**/Dockerfile</include>
        <include>**/*.xml</include>
        <include>**/*.properties</include>
    </includes>
</fileSet>
```

```xml

<fileSet encoding="UTF-8">
    <directory>java</directory>
    <includes>
        <include>**/Dockerfile</include>
        <include>**/*.</include>
        <include>**/*.gitignore</include>
        <include>**/*.cmd</include>
        <include>**/*.config</include>
    </includes>
</fileSet>
```   

9. At this point the archetype is ready to use, but we can move it into its own project.

```shell
cp -r ../stacks-java/target/generated-sources/archetype {PATH_TO_DESIRED_FOLDER}
```

10. Move to the new path and install the new archetype in the local maven repo

```shell
cd  {PATH_TO_DESIRED_FOLDER}
mvn install
```

11. Now you're ready to use the new archetype to create projects based on the amido-stacks repo by running

```shell
mvn archetype:generate -DarchetypeGroupId='com.amido' -DarchetypeArtifactId='stacks-api-archetype' -DarchetypeVersion='1.0.7-SNAPSHOT' -DgroupId='org.guillo' -DartifactId='stacks-test' -Dversion='1.0.0-SNAPSHOT' -Dpackage='org.guillo.test.api'
```

- DarchetypeGroupId: groupid of the archetype to use
- DarchetypeArtifactId: artifactId of the archetype to use
- DarchetypeVersion: version of the archetype to use
- DgroupId: New group Id
- DartifactId: New artifact Id
- Dversion: Version for the project using the archetype
- Dpackage: Package for the project using the archetype
