package kcz.side.lightbulbsbackend;

import kcz.side.lightbulbsbackend.model.Bulb;
import kcz.side.lightbulbsbackend.model.BulbColor;
import kcz.side.lightbulbsbackend.service.BulbService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.InetAddress;

@SpringBootApplication
public class LightbulbsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightbulbsBackendApplication.class, args);
    }

//    @Component
//    private static class runner implements CommandLineRunner {
//        BulbService bulbService;
//        @Override
//        public void run(String... args) throws Exception {
//            bulbService = new BulbService();
//            InetAddress inetAddress = InetAddress.getLocalHost();
//            BulbColor color = new BulbColor(0,0,0);
//            Bulb bulb = new Bulb(1, inetAddress, color, 0);
//            bulbService.addBulb(bulb);
//            bulbService.turnOn(bulb.getId());
//        }
//    }
}
