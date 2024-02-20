# podman build -t quarkus/modbus-server .
# podman run -d --name modbus-server -p 5020:5020 quarkus/modbus-server
# podman exec -it modbus-server /bin/bash
FROM registry.access.redhat.com/ubi9/ubi:9.3-1552
WORKDIR "/root"
RUN curl https://www.modbusdriver.com/downloads/diagslave.tgz -o diagslave.tgz && tar xzf diagslave.tgz && \
    curl https://www.modbusdriver.com/downloads/modpoll.tgz -o modpoll.tgz && tar xzf modpoll.tgz
EXPOSE 5020
CMD nohup /root/diagslave/x86_64-linux-gnu/diagslave -m tcp -p 5020 & sleep infinity