package com.stephennimmo.modbus;

import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.ModbusMessage;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Startup
public class ModbusClientPoller {

    public static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Inject
    ModbusClient modbusClient;

    void onStartup(@Observes StartupEvent event) {
        Log.info("Starting...");
        executor.scheduleAtFixedRate(this::read, 5000, 1000, TimeUnit.MILLISECONDS);
    }

    void read() {
        if (!modbusClient.isConnected()) {
            Log.error("ModbusClient is not connected...");
            return;
        }
        try {
            ModbusMessage request = RegistersModbusMessage.readHoldingsRequest(1, 0, 1);
            long start = System.currentTimeMillis();
            RegistersModbusMessage response = modbusClient.send(request).unwrap(RegistersModbusMessage.class);
            Log.infof("Response: %s [%s], executionTime: %s", response, response.dataDecode()[0], System.currentTimeMillis() - start);
        } catch (Exception e) {
            Log.error("Unable to Read: %s", e.getMessage(), e);
        }
    }

    void onShutdown(@Observes ShutdownEvent event) {
        Log.info("Shutdown...");
        executor.shutdown();
    }

}