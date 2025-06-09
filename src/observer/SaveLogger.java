package observer;

public class SaveLogger implements Observer {

    @Override
    public void update(String message) {
        System.out.println("🔔 Notification: "+message);
    }
}
