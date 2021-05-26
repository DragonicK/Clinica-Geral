package Engine.Window.Observer;

import Engine.Window.WindowProduct;

public class NotificationProduct  extends Component {
    private final WindowProduct window;
    
    public NotificationProduct(WindowProduct window) {
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