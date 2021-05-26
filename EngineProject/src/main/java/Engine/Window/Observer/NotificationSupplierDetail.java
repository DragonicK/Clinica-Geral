package Engine.Window.Observer;

import Engine.Window.WindowSupplierDetail;

public class NotificationSupplierDetail extends Component {
    private final WindowSupplierDetail window;
    
    public NotificationSupplierDetail(WindowSupplierDetail window) {
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