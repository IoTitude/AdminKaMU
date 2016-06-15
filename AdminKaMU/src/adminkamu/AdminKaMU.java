package adminkamu;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
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


public class AdminKaMU {
    static int profileID;
    static KaaClient kaaClient;
    static String target;
    
    public static void main(String[] args) {
        createKaaClient();
        kaaClient.start();
        attachUser();
        do {
            //endpointmenu();
            profilemenu();    
        } while (profileID != 0);  
    }
    
    //this menu lists all attached endpoints
    /*public static void endpointmenu(){
        System.out.println("Choose an endpoint");
        int iter = 0;
        for (Map.Entry<String, String> entry : map.entrySet()){
                System.out.println(iter + ". " + entry.getValue());
                iter++;
        }
        System.out.println("2. Get endpoints");       
        
        int sw;
        Scanner in = new Scanner(System.in);
        sw = in.nextInt();
        String hash;
        
        switch(sw){
            case 0:
                hash = map.keySet().toArray()[sw].toString();
                //profilemenu(hash);
                break;
            case 1:
                hash = map.keySet().toArray()[sw].toString();
                //profilemenu(hash);
                break;
            case 2:
                endpointmenu();
                break;
        }     
    }*/
    
    //this menu lists all available measuring profiles for chosen endpoint
    public static void profilemenu(){
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
                //sendProfileSingleTarget(hash);
                break;
            case 4:
                profileID = 4;  
                System.out.println("admincase 4");
                break;
            case 5:
                System.out.println("admincase 5");
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
                System.out.println(kaaClient.getEndpointKeyHash());
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
            }   
        
            @Override
            public void onRequestFailed() {
                System.out.println("Send profile request failed");
            }
        });
    }
}
