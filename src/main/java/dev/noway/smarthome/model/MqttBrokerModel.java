package dev.noway.smarthome.model;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "mqtt_broker", schema = "smart_home")
public class MqttBrokerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private int id;

    @Column(name = "label")
    private String label;

    @Column(name = "network")
    private String network;

    @Column(name = "hardwer")
    private String hardwer;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

    public MqttBrokerModel() {
    }

    public MqttBrokerModel(String network, String hardwer) {
        setLabel("");
        setNetwork(network);
        setHardwer(hardwer);
        setAddDate(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getHardwer() {
        return hardwer;
    }

    public void setHardwer(String hardwer) {
        this.hardwer = hardwer;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "MqttBrokerModel{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", network='" + network + '\'' +
                ", hardwer='" + hardwer + '\'' +
                ", addDate=" + addDate +
                '}';
    }
}
