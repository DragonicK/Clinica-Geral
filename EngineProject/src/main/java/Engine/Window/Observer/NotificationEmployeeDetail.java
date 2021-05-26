package Engine.Window.Observer;

import Engine.Window.WindowEmployeeDetail;

public class NotificationEmployeeDetail extends Component {
    private final WindowEmployeeDetail window;
    
    public NotificationEmployeeDetail(WindowEmployeeDetail window) {
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