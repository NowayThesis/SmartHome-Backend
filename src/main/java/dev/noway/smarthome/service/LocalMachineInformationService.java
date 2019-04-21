package dev.noway.smarthome.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

@Service
@ComponentScan
public class LocalMachineInformationService {

    private InetAddress ip = null;
    private NetworkInterface network = null;
    private String hardwer = null;
    private String clientId = "";

    public LocalMachineInformationService() {
        ip = getIp();
        network = getNetworkAddress();
        hardwer = getHardwerAddress();
        clientId = getClientId();
    }

    public String getClientId() {
        if (getHardwer() == null){
            return UUID.randomUUID().toString();
        } else  {
            return getHardwer();
        }
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public InetAddress getIp(){
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public NetworkInterface getNetworkAddress(){
        try {
            return NetworkInterface.getByInetAddress(getIp());
        } catch (SocketException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public String getHardwerAddress(){
        byte[] mac = new byte[0];
        try {
            mac = getNetworkAddress().getHardwareAddress();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public NetworkInterface getNetwork() {
        return network;
    }

    public void setNetwork(NetworkInterface network) {
        this.network = network;
    }

    public String getHardwer() {
        return hardwer;
    }

    public void setHardwer(String hardwer) {
        this.hardwer = hardwer;
    }
}
