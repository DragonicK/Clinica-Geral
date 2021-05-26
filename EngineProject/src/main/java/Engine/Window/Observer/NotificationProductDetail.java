package Engine.Window.Observer;

import Engine.Window.WindowProductDetail;

public class NotificationProductDetail extends Component {
    private final WindowProductDetail window;
    
    public NotificationProductDetail(WindowProductDetail window) {
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
