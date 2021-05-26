package Engine.Window.Observer;

public interface INotifier {
    void Add(IComponent component);
    void Remove(IComponent component);
    void Notify(Changes changes);
}