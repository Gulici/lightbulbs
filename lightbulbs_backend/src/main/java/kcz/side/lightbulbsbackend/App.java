package kcz.side.lightbulbsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
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
