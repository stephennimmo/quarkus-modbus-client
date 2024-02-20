# quarkus-modbus-client

https://stephennimmo.com/modbus-integration-at-the-edge-with-quarkus-native-image-compilation
https://www.modbusdriver.com/diagslave.html
https://www.modbusdriver.com/modpoll.html

# Build/Run modbus server
```
podman build -t quarkus/modbus-server .
podman run -d --rm --name modbus-server --network modbus -p 5020:5020 quarkus/modbus-server
```

# Use Modpoll
```
podman exec -it modbus-server /bin/bash

# Read
/root/modpoll/x86_64-linux-gnu/modpoll -m tcp -p 5020 -a 1 -r 1 -c 1 -1 127.0.0.1

# Write
/root/modpoll/x86_64-linux-gnu/modpoll -m tcp -p 5020 -a 1 -r 1 -c 1 -1 127.0.0.1 4
```

# Run local and connect to modbus-server
```
./mvnw clean quarkus:dev
```

# Package and Run
```
./mvnw clean package -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
podman build -f src/main/docker/Dockerfile.native-micro -t quarkus/quarkus-modbus-client .
podman run -i --rm --network modbus -e MODBUS_HOST=modbus-server -e MODBUS_PORT=5020 quarkus/quarkus-modbus-client
```

----

# Other Notes
```
curl https://www.modbusdriver.com/downloads/diagslave.tgz -o diagslave.tgz
tar xzf diagslave.tgz
curl https://www.modbusdriver.com/downloads/modpoll.tgz -o modpoll.tgz
tar xzf modpoll.tgz
```

# Setup 
```
podman machine ssh 
ip a
```

# Start 
`nohup /root/diagslave/x86_64-linux-gnu/diagslave -m tcp -p 5020 &`

# Read
`/root/modpoll/x86_64-linux-gnu/modpoll -m tcp -p 5020 -a 1 -r 1 -c 1 -1 127.0.0.1`

# Write
`/root/modpoll/x86_64-linux-gnu/modpoll -m tcp -p 5020 -a 1 -r 1 -c 1 -1 127.0.0.1 4`




```
podman run --rm -it registry.access.redhat.com/ubi9/ubi /bin/bash
```


# Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-modbus-client-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

