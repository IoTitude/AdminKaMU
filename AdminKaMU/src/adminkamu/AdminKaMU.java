package adminkamu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.client.event.FindEventListenersCallback;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.client.logging.strategies.RecordCountLogUploadStrategy;
import org.kaaproject.kaa.common.endpoint.gen.SyncResponseResultType;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.kaaproject.kaa.schema.sample.event.kamu.ChangeProfile;
import org.kaaproject.kaa.schema.sample.event.kamu.KaMUEventClassFamily;
import org.kaaproject.kaa.schema.sample.event.kamu.RegisterDevice;
import org.kaaproject.kaa.schema.sample.event.kamu.RegistrationAnswer;


public class AdminKaMU {
    static int profileID;
    static KaaClient kaaClient;
    static String target;
    static BaasBoxController baas;
    
    public static void main(String[] args) {
        createKaaClient();
        kaaClient.start();
        attachUser();        
        endpointmenu();           
    }
    
    //this menu lists all attached endpoints
    public static void endpointmenu(){
        System.out.println("Choose an endpoint");
        Map<String, String> map = parseDeviceMac();
        int iter = 0;
        for (Map.Entry<String, String> entry : map.entrySet()){
                System.out.println(iter + ". " + entry.getValue());
                iter++;
        }
        System.out.println("2. Get endpoints");       
        
        int sw;
        Scanner iin = new Scanner(System.in);
        sw = iin.nextInt();
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
            case 2:
                endpointmenu();
                break;
            case 3:
                System.exit(0);
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
                break;
            case 5:
                System.out.println("admincase 5");
                break;
            case 10:
                System.exit(0); 
                break;
        }
    }
    
    
    public static void createKaaClient(){
        kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {
            @Override
            public void onStarted() {    
                //System.out.println(kaaClient.getEndpointKeyHash());
                kaaClient.setLogUploadStrategy(new RecordCountLogUploadStrategy(1));                           
            }

            @Override
            public void onStopped() {
             
            }
        });
    }
    
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
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();

        tecf.sendEventToAll(new ChangeProfile(profileID));
        System.out.println("Change profile request sent");
        endpointmenu();
        //LogData log = new LogData("asdmacasd", "asdhashasd");
                //kaaClient.addLogRecord(log);
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

                ChangeProfile ctc = new ChangeProfile(3);
                // Assume the target variable is one of the received in the findEventListeners method
                tecf.sendEvent(ctc, target);
                System.out.println(target + " target");
                endpointmenu();
            }   
        
            @Override
            public void onRequestFailed() {
                System.out.println("Send profile request failed");
            }
        });
    }
    
    public static void receiveEvents(){
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        System.out.println("Listening to incoming events");
        tecf.addListener(new KaMUEventClassFamily.Listener() {
            @Override
            public void onEvent(ChangeProfile event, String source) {
                System.out.println("homo");
                receiveEvents();
            } 

            @Override
            public void onEvent(RegisterDevice event, String source) {
                System.out.println("Event received from: " + source);
                String mac = event.getMac();
                String hash = event.getKeyhash();
                registerDevice(mac, hash);
                sendRegistrationAnswer(hash);
                //sendProfileSingleTarget(hash);
                receiveEvents();
                endpointmenu();
            }

            @Override
            public void onEvent(RegistrationAnswer event, String source) {
                receiveEvents();
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
            }
        });
                
   }
    
    public static void registerDevice(String mac, String hash){
        String session = baas.logIn();
        JSONArray data = baas.getDeviceInfo(session);
        
        baas.updateDeviceHash(session, mac, hash);
    }
    
    public static void sendRegistrationAnswer(String target) {
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        RegistrationAnswer ctc = new RegistrationAnswer(true);
        tecf.sendEvent(ctc, target);
        System.out.println("Device registration answer sent");
    }
    
    public static Map parseDeviceMac() {
        String session = baas.logIn();
        JSONArray data = baas.getDeviceInfo(session);
        //System.out.println(data);
        Map<String, String> map = new HashMap();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            map.put(object.getString("hash"), object.getString("mac"));
        }
        return map;
    }
    
}
