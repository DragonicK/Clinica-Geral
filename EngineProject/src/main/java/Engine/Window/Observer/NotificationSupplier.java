package Engine.Window.Observer;

import Engine.Window.WindowSupplier;

public class NotificationSupplier extends Component {
    private final WindowSupplier window;
    
    public NotificationSupplier(WindowSupplier window) {
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