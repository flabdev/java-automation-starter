package api.exceptions;
public class AutomationException extends Exception {

    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(Exception ex) {
        super(ex);
    }

}