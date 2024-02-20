package com.stephennimmo.modbus;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.tcp.netty.NettyTcpModbusClientConfig;
import net.solarnetwork.io.modbus.tcp.netty.TcpNettyModbusClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ModbusClientProvider {

    @ConfigProperty(name = "modbus.host")
    String modbusHost;

    @ConfigProperty(name = "modbus.port")
    int modbusPort;

    @Produces
    ModbusClient modbusClient() {
        Log.infof("Client Configuration: %s:%s", modbusHost, modbusPort);
        NettyTcpModbusClientConfig config = new NettyTcpModbusClientConfig(modbusHost, modbusPort);
        config.setAutoReconnect(true);
        config.setAutoReconnectDelaySeconds(5);
        ModbusClient client = new TcpNettyModbusClient(config);
        client.start();
        return client;
    }

}
