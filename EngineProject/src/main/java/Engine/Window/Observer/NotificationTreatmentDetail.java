package Engine.Window.Observer;

import Engine.Window.WindowTreatmentDetail;

public class NotificationTreatmentDetail extends Component {
    private final WindowTreatmentDetail window;
    
    public NotificationTreatmentDetail(WindowTreatmentDetail window) {
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