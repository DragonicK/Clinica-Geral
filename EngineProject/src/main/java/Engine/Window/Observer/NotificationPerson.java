package Engine.Window.Observer;

import Engine.Window.WindowPerson;

public class NotificationPerson extends Component {
    private final WindowPerson window;
    
    public NotificationPerson(WindowPerson window) {
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