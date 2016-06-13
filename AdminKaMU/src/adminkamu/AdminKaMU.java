package adminkamu;

import java.net.NetworkInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.client.event.FindEventListenersCallback;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.client.logging.BucketInfo;
import org.kaaproject.kaa.client.logging.LogBucket;
import org.kaaproject.kaa.client.logging.LogRecord;
import org.kaaproject.kaa.client.logging.LogStorage;
import org.kaaproject.kaa.client.logging.LogStorageStatus;
import org.kaaproject.kaa.client.logging.strategies.RecordCountLogUploadStrategy;
import org.kaaproject.kaa.common.endpoint.gen.SyncResponseResultType;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.kaaproject.kaa.schema.sample.event.kamu.ChangeProfile;
import org.kaaproject.kaa.schema.sample.event.kamu.KaMUEventClassFamily;
import org.kaaproject.kaa.schema.sample.event.kamu.RegisterDevice;
import org.kaaproject.kaa.schema.sample.event.kamu.RegistrationAnswer;
import org.kaaproject.kaa.schema.sample.logging.KaMU.KaMULog;

public class AdminKaMU {
    private static final Logger LOG = LoggerFactory.getLogger(AdminKaMU.class);
    static int profileID;
    static KaaClient kaaClient;
    static String target;
    
    public static void main(String[] args) {
        createKaaClient();
        kaaClient.start();
        attachUser();
        do {
            endpointmenu();
            //profilemenu();    
        } while (profileID != 0);  
    }
    
    //this menu lists all attached endpoints
    public static void endpointmenu(){
        Map<String, String> map = CassandraConnector.getDevices();
        System.out.println("Choose an endpoint");
        int iter = 0;
        
        
        for (Map.Entry<String, String> entry : map.entrySet()){
                System.out.println(iter + ". " + entry.getValue());
                //System.out.println(map.keySet().toArray()[0]);
                iter++;
        }
        
        
        int sw;
        Scanner in = new Scanner(System.in);
        sw = in.nextInt();
        String hash;
        
        switch(sw){
            case 0:
                hash = map.keySet().toArray()[sw].toString();
                profilemenu(hash);
                break;
            case 1:
                hash = map.keySet().toArray()[sw].toString();
                profilemenu(hash);
                break;
        }
        
        
        
        
    }
    
    //this menu lists all available measuring profiles for chosen endpoint
    public static void profilemenu(String hash){
        int psw;
        Scanner in = new Scanner(System.in); 
        System.out.println("1. Water level");
        System.out.println("2. Water flow");
        System.out.println("3. Water");
        System.out.println("4. Water something");
        System.out.println("5. Receive events");
        System.out.println("0. Exit");
        psw = in.nextInt();

        switch (psw) {
            case 1:
                profileID = 1;
                System.out.println("admincase 1");
                sendProfileAll();
                break;
            case 2:
                profileID = 2;
                System.out.println("admincase 2");
                sendProfileAll();
                break;
            case 3:
                profileID = 3;
                System.out.println("admincase 3");
                sendProfileSingleTarget(hash);
                break;
            case 4:
                profileID = 4;  
                System.out.println("admincase 4");
                registerAdmin();
                break;
            case 5:
                System.out.println("admincase 5");
                CassandraConnector.getDevices();
                break;
            case 0:
                System.exit(0); 
                break;
        }
    }
    
    
    public static void createKaaClient(){
        kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {
            @Override
            public void onStarted() {    
                //EndpointRegiterationManager manag = kaaClient
                //EndpointKeyHash hash = new EndpointKeyHash("To4XPbfRVtq/ZlGrC9Rm/53dou4=");
                //OnDetachEndpointOperationCallback resultListener = new OnDetachEndpointOperationCallback();
                //kaaClient.detachEndpoint(hash, null);
                //System.out.println("Endpoint removed?");
                System.out.println(kaaClient.getEndpointKeyHash());
                kaaClient.setLogUploadStrategy(new RecordCountLogUploadStrategy(1));                           
            }

            @Override
            public void onStopped() {
             
            }
        });
    }
    //b8:27:eb:a2:c3:12
    //Uha46p0kssp+Zo46f5ouPjyj3Js= target
    public static void attachUser(){
        kaaClient.attachUser("asd", "asd", new UserAttachCallback() {
            @Override
            public void onAttachResult(UserAttachResponse response) {
                System.out.println("Attach response " + response.getResult());
                
                if (response.getResult() == SyncResponseResultType.SUCCESS){
                    System.out.println("User attached");
                    receiveEvents();
                }
                else{
                    kaaClient.stop();
                    System.out.println("Stopped");
                }
            }
        });
    }
    public static void sendProfileAll(){
        //List<String> FQNs = new LinkedList<>();
        //FQNs.add(ChangeProfile.class.getName());
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();

        tecf.sendEventToAll(new ChangeProfile(profileID));
        System.out.println("Change profile request sent");
        //LogData log = new LogData("asdmacasd", "asdhashasd");
                //kaaClient.addLogRecord(log);
    }
    
    public static void receiveEvents(){
        //List<String> FQNs = new LinkedList<>();
        //FQNs.add(ChangeProfile.class.getName());
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        System.out.println("Listening to incoming events");
        tecf.addListener(new KaMUEventClassFamily.Listener() {
            @Override
            public void onEvent(ChangeProfile event, String source) {
                System.out.println("homo"); 
            }   

            @Override
            public void onEvent(RegisterDevice event, String source) {
                LogStorage asd = new LogStorage() {
                    @Override
                    public BucketInfo addLogRecord(LogRecord record) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public LogStorageStatus getStatus() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public LogBucket getNextBucket() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void removeBucket(int bucketId) {
                        System.out.println("Removed");
                        
                    }

                    @Override
                    public void rollbackBucket(int bucketId) {
                        System.out.println("Rollback");
                    }

                    @Override
                    public void close() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };            
                
                
                System.out.println("Device registration request received from:\nMAC: " + event.getMac() + "\nHash: " + event.getKeyhash());
                String macStr;
                String hashStr;
                macStr = event.getMac();
                hashStr = event.getKeyhash();
                KaMULog log = new KaMULog(false, macStr, hashStr);
                kaaClient.addLogRecord(log);
                LOG.info("Log record {} sent", log.toString());
                //asd.close();
                sendRegistrationAnswer(hashStr);
                
            }

            @Override
            public void onEvent(RegistrationAnswer event, String source) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }  
    
    public static void sendRegistrationAnswer(String target) {
        List<String> FQNs = new LinkedList<>();
        FQNs.add(ChangeProfile.class.getName());
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        RegistrationAnswer ctc = new RegistrationAnswer(true);
        tecf.sendEvent(ctc, target);
        System.out.println("Device registration answer sent");
    }
    
    public static void registerAdmin() {
        String macStr;
        String hashStr;
        macStr = getMac();
        hashStr = kaaClient.getEndpointKeyHash();
        KaMULog log = new KaMULog(true, macStr, hashStr);
        kaaClient.addLogRecord(log);
    }
    
    public static String getMac(){
        String macStr = "";
        try {
            NetworkInterface netInf = NetworkInterface.getNetworkInterfaces().nextElement();
            byte[] mac = netInf.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            macStr = sb.toString();
            //System.out.println(sb.toString());
            return macStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
    public static void sendProfileSingleTarget(String target){
        List<String> FQNs = new LinkedList<>();
        FQNs.add(ChangeProfile.class.getName());
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        kaaClient.findEventListeners(FQNs, new FindEventListenersCallback() {
            @Override
            public void onEventListenersReceived(List<String> eventListeners) {
                System.out.println("Address: ");

                if (kaaClient.isAttachedToUser()) {
                    System.out.println("kaaClient is attached to user");
                }
                else {
                    System.out.println("kaaClient is NOT attached to user");
                }

                ChangeProfile ctc = new ChangeProfile(70);
                // Assume the target variable is one of the received in the findEventListeners method
                tecf.sendEvent(ctc, target);
                System.out.println(target + " target");
            }   
        
            @Override
            public void onRequestFailed() {
                System.out.println("Send profile request failed");
            }
        });
    }
}
