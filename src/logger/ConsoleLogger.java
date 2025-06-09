package logger;

public class ConsoleLogger implements Logger {

    @Override
    public void log(String type, String message) {
        System.out.println("[Console][" + type + "] " + message);
    }
}
