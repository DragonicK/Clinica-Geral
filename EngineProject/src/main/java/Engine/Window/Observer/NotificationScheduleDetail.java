package Engine.Window.Observer;

import Engine.Window.WindowScheduleDetail;

public class NotificationScheduleDetail extends Component {
    private final WindowScheduleDetail window;
    
    public NotificationScheduleDetail(WindowScheduleDetail window) {
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