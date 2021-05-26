package Engine.Window.Observer;

import java.util.List;
import java.util.ArrayList;

public class Notifier implements INotifier {
    private final List<IComponent> components;
    
    public Notifier() {
        components = new ArrayList<>();
    }
    
    @Override
    public void Add(IComponent component) {
        components.add(component);
    }
    
    @Override
    public void Remove(IComponent component) {
        components.remove(component);
    }
    
    @Override
    public void Notify(Changes changes) {
        var count = components.size();
        
        for (var i = 0; i < count; ++i) {
            var component = components.get(i);
            
            if (component.Contains(changes)) {
                component.ExecuteUpdate();
            }
        }
    }
}