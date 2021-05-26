package Engine.Window.Observer;

import Engine.Window.WindowPatientDetail;

public class NotificationPatientDetail extends Component {
    private final WindowPatientDetail window;
    
    public NotificationPatientDetail(WindowPatientDetail window) {
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