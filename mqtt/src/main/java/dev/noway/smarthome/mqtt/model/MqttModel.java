package dev.noway.smarthome.mqtt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class MqttModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private int urlId;
    private String topic;
    private String message;
    private LocalDateTime postDate;

    public MqttModel(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.postDate = LocalDateTime.now();
    }

    public String htmlTableRow() {
        return "<tr><td>" + this.id + "</td><td>" + this.topic + "</td><td>" + this.message + "</td><td>"+postDate.toString()+"</td></tr>";
    }

    @Override
    public String toString() {
        return "MQTT: topic={" + this.topic + "}, payload={" + this.message + "}, date={"+postDate.toString()+"}";
    }
}
