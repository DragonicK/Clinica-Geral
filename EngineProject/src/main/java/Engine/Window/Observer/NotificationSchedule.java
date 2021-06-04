package Engine.Window.Observer;

import Engine.Window.WindowSchedule;

public class NotificationSchedule extends Component {
    private final WindowSchedule window;
    
    public NotificationSchedule(WindowSchedule window) {
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