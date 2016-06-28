package adminkamu;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
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
import org.kaaproject.kaa.schema.sample.event.kamu.RegistrationAnswer;
import org.kaaproject.kaa.schema.sample.event.kamu.RegistrationRequest;
import org.kaaproject.kaa.schema.sample.event.kamu.RestartDevice;
import org.kaaproject.kaa.schema.sample.event.kamu.UpdateDevice;


public class AdminKaMU {
    static int profileID;
    static KaaClient kaaClient;
    static String target;
    
    public static void main(String[] args) {
        System.out.println("asd");
        createKaaClient();
        kaaClient.start();
        System.out.println(kaaClient.getEndpointKeyHash());
        attachUser();  
        AdminUI frame = new AdminUI();
        frame.setVisible(true);           
    }
  
    public static void createKaaClient(){
        kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {
            @Override
            public void onStarted() {                         
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
    public void sendProfileToAll(){
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        tecf.sendEventToAll(new ChangeProfile(1, 1));
        System.out.println("Change profile request sent");
    }
     
    
    public static void sendProfileToSingleTarget(String target){
        List<String> FQNs = new LinkedList<>();
        FQNs.add(ChangeProfile.class.getName());
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        kaaClient.findEventListeners(FQNs, new FindEventListenersCallback() {
            @Override
            public void onEventListenersReceived(List<String> eventListeners) {
                ChangeProfile ctc = new ChangeProfile(1, 1);
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
    
    public static void receiveEvents(){
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        System.out.println("Listening to incoming events");
        tecf.addListener(new KaMUEventClassFamily.Listener() {
            @Override
            public void onEvent(ChangeProfile event, String source) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } 
            
            @Override
            public void onEvent(RegistrationRequest event, String source) {
                System.out.println("Event received from: " + source);
                String mac = event.getMac();
                String hash = event.getHash();
                System.out.println(mac + " " + hash);
                registerDevice(mac, hash);
            }

            @Override
            public void onEvent(RegistrationAnswer event, String source) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onEvent(UpdateDevice event, String source) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onEvent(RestartDevice event, String source) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
                
   }
    
    public static void registerDevice(String mac, String hash){
        if (AdminUI.devices.containsValue(hash)) {
            System.out.println("Device already registered.");
            sendRegistrationAnswer(hash, true);
            //sendProfileToSingleTarget(hash);
        }
        else {
            System.out.println("Registering device.");
            String session = AdminUI.session;
            boolean status = AdminUI.bbc.updateDeviceHash(session, mac, hash);
            System.out.println(status);
            if (status) {
                sendRegistrationAnswer(hash, status);
                //sendProfileToSingleTarget(hash);
            }
            else {
                System.out.println("Error trying to register device.");
                sendRegistrationAnswer(hash, status);
            } 
        }
    }
    
    public static void sendRegistrationAnswer(String target, boolean status) {
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily tecf = eventFamilyFactory.getKaMUEventClassFamily();
        RegistrationAnswer ctc = new RegistrationAnswer(status, 50, 1);
        tecf.sendEvent(ctc, target);
        System.out.println("Device registration answer sent");
    }
    /*
    public void restartApplication(int seconds)
    {
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    try {
                        StringBuilder cmd = new StringBuilder();
                        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
                        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                            cmd.append(jvmArg + " ");
                        }
                        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
                        cmd.append(AdminKaMU.class.getName()).append(" ");
                        Runtime.getRuntime().exec(cmd.toString());
                        System.exit(0);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }, 
        seconds);
    }
    */
}
