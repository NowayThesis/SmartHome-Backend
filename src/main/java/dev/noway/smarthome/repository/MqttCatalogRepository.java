package dev.noway.smarthome.repository;

import dev.noway.smarthome.model.MqttCatalogModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface MqttCatalogRepository extends CrudRepository<MqttCatalogModel, Integer> {

    @Query("from MqttCatalogModel m where m.topic like %:topic%")
    List<MqttCatalogModel> findTopicFilter(@Param("topic") String topic);

    @Query("from MqttCatalogModel m where m.message LIKE %:message%")
    List<MqttCatalogModel> findMessageFilter(@Param("message") String message);


}