package Engine.Communication;

import Engine.Window.Observer.INotifier;
import Engine.Handler.IPersonHandler;
import Engine.Handler.IRegionHandler;
import Engine.Handler.IProductHandler;
import Engine.Handler.IPatientHandler;
import Engine.Handler.ISupplierHandler;
import Engine.Handler.IEmployeeHandler;
import Engine.Handler.IScheduleHandler;
import Engine.Handler.ITreatmentHandler;
import Engine.Database.DBConfiguration;

public class DependencyRepository {
    // Notifica todas as janelas quando corre uma mudança.
    public INotifier Notifier;
    // Controlador de cidades e países.
    public IRegionHandler RegionHandler;
    // Controlador de pessoas ...
    public IPersonHandler PersonHandler;
    // Controlador de produtos.
    public IProductHandler ProductHandler;
    // Controlador de pacientes.
    public IPatientHandler PatientHandler;
    // Controlador de fornecedores.
    public ISupplierHandler SupplierHandler;
    // Controlador de agendamentos.
    public IScheduleHandler ScheduleHandler;
    // Controlador de colaboradores.
    public IEmployeeHandler EmployeeHandler;
    public ITreatmentHandler TreatmentHandler;   
    public DBConfiguration DBConfiguration;
}