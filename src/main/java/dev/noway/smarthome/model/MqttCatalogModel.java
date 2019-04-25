package dev.noway.smarthome.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity()
@Table(name = "mqtt_catalog", schema = "smart_home")
public class MqttCatalogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private int id;

    private String topic;
    private String message;

    @Column(name = "broker_id")
    private int brokerId;

    @Column(name = "client_id")
    private int clientId;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;


    public MqttCatalogModel() { }

    public MqttCatalogModel(String topic, String message) {
        this.setTopic(topic);
        this.setMessage(message);
        this.setAddDate(LocalDateTime.now());
        this.setClientId(0);
        this.setBrokerId(0);
    }

    public MqttCatalogModel(String topic, String message, int clientId, int brokerId) {
        this.setTopic(topic);
        this.setMessage(message);
        this.setAddDate(LocalDateTime.now());
        this.setClientId(clientId);
        this.setBrokerId(brokerId);
    }

    @Override
    public String toString() {
        return "MQTT: id={"+this.id+"}, topic={" + this.topic + "}, payload={" + this.message + "}, date={"+addDate.toString()+"}";
    }
}