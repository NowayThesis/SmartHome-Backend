package dev.noway.smarthome.utils;
//
//import dev.noway.smarthome.model.MqttBrokerModel;
//import dev.noway.smarthome.service.LocalMachineInformationService;
//import dev.noway.smarthome.service.MqttBrokerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Service;
//
//@Service
//@ComponentScan
//public class MqttBrokerActual {
//
//    private  MqttBrokerModel actualBrokerModel;
//
//    @Autowired
//    private MqttBrokerService mqttBrokerService;
//
//    public void setactualMqttBroker(LocalMachineInformationService localMachineInformationService) {
//        if (mqttBrokerService.findHardwer(localMachineInformationService.getHardwer()) == null){
//            System.out.println("Net:"+localMachineInformationService.getNetworkAddress().toString());
//            System.out.println("Hard"+localMachineInformationService.getHardwer());
//            actualBrokerModel =
//                    mqttBrokerService.save(
//                            new MqttBrokerModel(
//                                    localMachineInformationService.getNetworkAddress().toString(),
//                                    localMachineInformationService.getHardwer()
//                            )
//            );
//        } else {
//            System.out.println("b");
//            actualBrokerModel = mqttBrokerService.findHardwer(localMachineInformationService.getHardwer());
//        }
//    }
//
//    public MqttBrokerModel getActualBrokerModel() {
//        return actualBrokerModel;
//    }
//
//}
