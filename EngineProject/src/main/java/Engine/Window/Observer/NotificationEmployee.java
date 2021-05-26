package Engine.Window.Observer;

import Engine.Window.WindowEmployee;

public class NotificationEmployee extends Component {
    private final WindowEmployee window;
    
    public NotificationEmployee(WindowEmployee window) {
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
