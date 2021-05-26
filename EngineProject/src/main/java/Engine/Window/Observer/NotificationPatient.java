package Engine.Window.Observer;

import Engine.Window.WindowPatient;

public class NotificationPatient extends Component {
    private final WindowPatient window;
    
    public NotificationPatient(WindowPatient window) {
        this.window = window;
    }
    
    @Override
    public void ExecuteUpdate() {
        if (window != null) {
            if (window.isVisible()) {
                window.UpdateWindow();
            }
        }
    }
}