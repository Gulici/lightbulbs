package kcz.side.lightbulbsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.InetAddress;

@Data
@AllArgsConstructor
public class Bulb {
    private int id;
    private InetAddress address;
//    private BulbColor color;
//    private int d;

//    public void setAll(BulbColor color, int d) {
//        this.color = color;
//        this.d = d;
//    }
}
