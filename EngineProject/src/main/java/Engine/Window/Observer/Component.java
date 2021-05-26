package Engine.Window.Observer;

import java.util.HashSet;

public class Component implements IComponent {
    protected HashSet<Changes> hash;
    
    public Component() {
        hash = new HashSet<>();
    }
    
    @Override
    public void Add(Changes changes) {
        hash.add(changes);
    }
    
    @Override
    public void Remove(Changes changes) {
        hash.remove(changes);
    }
    
    @Override
    public boolean Contains(Changes changes) {
        return hash.contains(changes);
    }
    
    @Override
    public void ExecuteUpdate() {
        
    }
}