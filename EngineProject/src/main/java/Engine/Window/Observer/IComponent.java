package Engine.Window.Observer;

public interface IComponent {   
    void Add(Changes changes);
    void Remove(Changes changes);
    boolean Contains(Changes changes);
    void ExecuteUpdate();
}