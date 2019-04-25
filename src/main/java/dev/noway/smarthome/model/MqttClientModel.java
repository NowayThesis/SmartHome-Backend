package dev.noway.smarthome.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "mqtt_client", schema = "smart_home")
public class MqttClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private int id;

    private String label;
    private String network;
    private String hardwer;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

    public MqttClientModel() {
    }

    public MqttClientModel(String network, String hardwer) {
        this.setLabel("");
        this.setNetwork(network);
        this.setHardwer(hardwer);
        this.setAddDate(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "MqttClientModel{" +
                "id=" + this.id +
                ", label='" + this.label + '\'' +
                ", network='" + this.network + '\'' +
                ", hardwer='" + this.hardwer + '\'' +
                ", addDate=" + this.addDate +
                '}';
    }
}
