package dev.noway.smarthome.model;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "mqtt_catalog", schema = "smart_home")
public class MqttCatalogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private int id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "message")
    private String message;

    @Column(name = "broker_id")
    private int brokerId;

    @Column(name = "client_id")
    private int clientId;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
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

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate() {
        this.addDate = LocalDateTime.now();
    }

    public MqttCatalogModel(String topic, String message, int clientId, int brokerId) {
        this.setTopic(topic);
        this.setMessage(message);
        this.setAddDate();
        this.setClientId(clientId);
        this.setBrokerId(brokerId);
    }

    public String htmlTableRow() {
        return "<tr><td>" + this.id + "</td><td>" + this.topic + "</td><td>" + this.message + "</td><td>"+addDate.toString()+"</td></tr>";
    }

    @Override
    public String toString() {
        return "MQTT: id={"+this.id+"}, topic={" + this.topic + "}, payload={" + this.message + "}, date={"+addDate.toString()+"}";
    }
}
