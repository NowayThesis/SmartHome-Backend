package dev.noway.smarthome.service;

//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Service;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.UUID;
//
//@Service
//@ComponentScan
//public class LocalMachineInformationService {
//
//
//    public InetAddress getIp(){
//        try {
//            return InetAddress.getLocalHost();
//        } catch (UnknownHostException e1) {
//            e1.printStackTrace();
//        }
//        return null;
//    }
//
//    public NetworkInterface getNetworkAddress(){
//        try {
//            return NetworkInterface.getByInetAddress(getIp());
//        } catch (SocketException e1) {
//            e1.printStackTrace();
//            return null;
//        }
//    }
//
//    public String getHardwerAddress(){
//        byte[] mac = new byte[0];
//        try {
//            mac = getNetworkAddress().getHardwareAddress();
//        } catch (SocketException e1) {
//            e1.printStackTrace();
//        }
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < mac.length; i++) {
//            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//        }
//        return sb.toString();
//    }
//}
