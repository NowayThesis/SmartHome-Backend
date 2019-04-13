package dev.noway.smarthome.model;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "mqtt_catalog", schema = "smart_home")
public class MqttCatalogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "message")
    private String message;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public MqttCatalogModel() {
    }

    public MqttCatalogModel(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.addDate = LocalDateTime.now();
    }

    public String htmlTableRow() {
        return "<tr><td>" + this.id + "</td><td>" + this.topic + "</td><td>" + this.message + "</td><td>"+addDate.toString()+"</td></tr>";
    }

    @Override
    public String toString() {
        return "MQTT: topic={" + this.topic + "}, payload={" + this.message + "}, date={"+addDate.toString()+"}";
    }
}
