package Engine.Window.Observer;

import Engine.Window.WindowRegion;

public class NotificationRegion extends Component {
    private final WindowRegion window;
    
    public NotificationRegion(WindowRegion window) {
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
