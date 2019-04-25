package dev.noway.smarthome.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "mqtt_last_message", schema = "smart_home")
public class MqttLastMessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private int id;

    private String topic;
    private String message;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

    public MqttLastMessageModel() {
    }

    public MqttLastMessageModel(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.setAddDate(LocalDateTime.now());
    }
}
