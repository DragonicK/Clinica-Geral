package Engine.Window.Observer;

import Engine.Window.WindowMain;

public class NotificationMain extends Component {
    private final WindowMain window;
    
    public NotificationMain(WindowMain window) {
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