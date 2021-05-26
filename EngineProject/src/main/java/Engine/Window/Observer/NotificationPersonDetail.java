package Engine.Window.Observer;

import Engine.Window.WindowPersonDetail;

public class NotificationPersonDetail extends Component {
    private final WindowPersonDetail window;
    
    public NotificationPersonDetail(WindowPersonDetail window) {
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