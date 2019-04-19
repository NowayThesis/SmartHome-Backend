package dev.noway.smarthome.model;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "mqtt_broker", schema = "smart_home")
public class MqttBrokerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSSZ")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

}
