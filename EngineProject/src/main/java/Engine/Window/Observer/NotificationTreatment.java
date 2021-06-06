package Engine.Window.Observer;

import Engine.Window.WindowTreatment;

public class NotificationTreatment extends Component {
    private final WindowTreatment window;
    
    public NotificationTreatment(WindowTreatment window) {
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