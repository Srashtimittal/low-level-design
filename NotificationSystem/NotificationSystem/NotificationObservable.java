import java.util.ArrayList;
import java.util.List;

// Concrete Observable
public class NotificationObservable implements IObservable {
    private List<IObserver> observers = new ArrayList<>();
    private INotification currentNotification = null;

    public void addObserver(IObserver obs) {
        observers.add(obs);
    }

    public void removeObserver(IObserver obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for (IObserver obs : observers) {
            obs.update();
        }
    }

    public void setNotification(INotification notification) {
        this.currentNotification = notification;
        notifyObservers();
    }

    public INotification getNotification() {
        return currentNotification;
    }

    public String getNotificationContent() {
        return currentNotification.getContent();
    }
}
