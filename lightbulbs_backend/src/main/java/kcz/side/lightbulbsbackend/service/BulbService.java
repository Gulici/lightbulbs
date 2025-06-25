package kcz.side.lightbulbsbackend.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kcz.side.lightbulbsbackend.config.SceneConfigProvider;
import kcz.side.lightbulbsbackend.model.Bulb;
import kcz.side.lightbulbsbackend.model.BulbColor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BulbService {

    private final Map<Integer, Bulb> bulbMap = new HashMap<>();
    JsonObject config;

    public BulbService(SceneConfigProvider configProvider) {
        log.info("BulbService  started");
        config = configProvider.getConfig();
        JsonObject bulbs = config.getAsJsonObject("bulbs");
        addBulbsFromConfig(bulbs);
        log.info("BulbService created");
    }

    public void turnOn(int id){
        Bulb bulb = bulbMap.get(id);
        if(bulb != null){
           setState(bulb, true);
        }
        else throw new IllegalArgumentException("Bulb: " + id + " is not found");
    }

    public void turnOff(int id){
        Bulb bulb = bulbMap.get(id);
        if(bulb != null){
            setState(bulb, false);
        }
        else throw new IllegalArgumentException("Bulb: " + id + " is not found");
    }

    private void setState(Bulb bulb, boolean state) {
        JsonObject params = new JsonObject();
        params.addProperty("state", state);
        sendMessage("setState", params, bulb);
    }

    public void setColor(int id, BulbColor color){
        Bulb bulb = bulbMap.get(id);
        if(bulb != null){
            int r = color.getR();
            int g = color.getG();
            int b = color.getB();
            int d = Math.max(color.getD(), 10);
            if (r > 255 || g > 255 || b > 255 || d > 100 || r < 0 && g < 0 && b < 0) {
                throw new IllegalArgumentException("Color: " + color + " is invalid");
            }

            JsonObject params = new JsonObject();
            params.addProperty("r", r);
            params.addProperty("g", g);
            params.addProperty("b", b);
            params.addProperty("dimming", d);

            sendMessage("setPilot", params, bulb);
        }
    }

    public void setTemp(int id, BulbColor color){
        Bulb bulb = bulbMap.get(id);
        if(bulb != null) {
            int d = Math.max(color.getD(), 10);
            if (d > 100) {
                throw new IllegalArgumentException("Color: " + color + " is invalid");
            }

            JsonObject params = new JsonObject();
            params.addProperty("temp", color.getTemp());
            params.addProperty("dimming", d);

            sendMessage("setPilot", params, bulb);
        }
    }

    public void setScene(String sceneId) {
        JsonObject scenes = config.getAsJsonObject("scenes");
        if (scenes == null) {
            throw new IllegalStateException("No entry 'scenes' in config");
        }
        JsonObject scene = scenes.getAsJsonObject(sceneId);
        if (scene == null) {
            throw new IllegalArgumentException("Scene ID " + sceneId + " not found in config");
        }

        for (Bulb bulb : bulbMap.values()) {
            turnOff(bulb.getId());
        }

        for (Map.Entry<String, JsonElement> entry : scene.entrySet()) {
            int id = Integer.parseInt(entry.getKey());
            JsonObject params = entry.getValue().getAsJsonObject();

            Bulb bulb = bulbMap.get(id);
            if (bulb != null) {
                sendMessage("setPilot", params, bulb);
            } else {
                log.error("Bulb not found, ID: {}", id);
            }
        }
    }


    private void sendMessage(String method, JsonObject params, Bulb bulb){
        JsonObject data = new JsonObject();
        data.addProperty("method", method);
        data.add("params", params);

        InetAddress address = bulb.getAddress();
        sendUdpPacket(data, address);
    }


    private void sendUdpPacket(JsonObject dataJson, InetAddress ip) {
        final int port = 38899;
        final int maxRetries = 4;
        final int timeoutMillis = 250;

        log.info("Sending UDP packet");
        log.info("IP: {}", ip.getHostAddress());
        log.info("Data: {}", dataJson.toString());

        byte[] data = dataJson.toString().getBytes(StandardCharsets.UTF_8);
        byte[] buf = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(timeoutMillis);

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, ip, port);
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);

            int attempt = 0;

            while (attempt < maxRetries) {
                try {
                    log.info("Attempt {}/{}", attempt + 1, maxRetries);
                    socket.send(sendPacket);
                    socket.receive(receivePacket); // throws SocketTimeoutException if no response
                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                    log.info("Received: {}", message);

                    JsonObject response = JsonParser.parseString(message).getAsJsonObject();
                    if (response.has("result") &&
                            response.getAsJsonObject("result").has("success") &&
                            response.getAsJsonObject("result").get("success").getAsBoolean()) {
                        return;
                    } else {
                        throw new IllegalArgumentException("Error: " + response);
                    }

                } catch (SocketTimeoutException e) {
                    log.warn("No response received (timeout), retrying...");
                    attempt++;
                }
            }

            throw new RuntimeException("No response from device after " + maxRetries + " attempts");

        } catch (IOException e) {
            throw new RuntimeException("UDP communication failed", e);
        }
    }


    public void addBulb(Bulb bulb){
        bulbMap.put(bulb.getId(), bulb);
    }

    private void addBulbsFromConfig(JsonObject bulbsConfig) {
        for (Map.Entry<String, JsonElement> entry : bulbsConfig.entrySet()) {
            try {
                int id = Integer.parseInt(entry.getKey());
                String ipStr = entry.getValue().getAsString();
                InetAddress ip = InetAddress.getByName(ipStr);
                addBulb(new Bulb(id, ip));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
