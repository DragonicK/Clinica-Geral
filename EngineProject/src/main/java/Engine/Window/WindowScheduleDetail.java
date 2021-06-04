package Engine.Window;

import Engine.Common.Strings;
import Engine.Patient;
import Engine.Employee;
import Engine.Schedule;
import Engine.Database.DBOperationState;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationScheduleDetail;

import java.text.SimpleDateFormat;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.List;

public class WindowScheduleDetail extends javax.swing.JDialog {
    private DependencyRepository dependencies;
    private NotificationScheduleDetail notification;
    private List<Patient> patients;
    private List<Employee> employees;
    private DBOperationState operationState;
    
    // Usado em caso de edição.
    private int scheduleId;
    
    public WindowScheduleDetail() {
        initComponents();
        
        this.setModal(true);
        this.setResizable(false);
        
        RadioFind.add(RadioName);
        RadioFind.add(RadioDocument);
        
        RadioPerson.add(RadioPatient);
        RadioPerson.add(RadioEmployee);
    }
    
    public void UpdateWindow() {
        
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationScheduleDetail(this);
        
        // Adiciona as modificações que terão efeito nesta janela.
        notification.Add(Changes.Person);
        notification.Add(Changes.Patient);
        notification.Add(Changes.Employee);
        
        // Adiciona para o notificador.
        dependencies.Notifier.Add(notification);
    }
    
    public void Clear() {
        var empty = Strings.Empty;
        
        ComboPatient.removeAllItems();
        ComboEmployee.removeAllItems();
        TextFind.setText(empty);
        
        PatientTextName.setText(empty);
        PatientTextDocument.setText(empty);
        PatientTextEmail.setText(empty);
        
        EmployeeTextName.setText(empty);
        EmployeeTextDocument.setText(empty);
        EmployeeTextRole.setText(empty);
        
        TextDate.setText(empty);
    }
    
    public void SetOperationState(DBOperationState operation) {
        operationState = operation;
        
        if (operationState == DBOperationState.Insert) {
            ButtonRegister.setText("Cadastrar");
            this.setTitle("Cadastrar Novo Agendamento");
        }
        else if (operationState == DBOperationState.Update) {
            ButtonRegister.setText("Atualizar");
            this.setTitle("Atualizar Agendamento");
        }
    }
    
    public void LoadPatients() {
        var handler = dependencies.PatientHandler;
        
        if (handler != null) {
            patients = handler.GetPatients();
            ClearPatients();
            FillComboPatients();
        }
    }
    
    public void LoadEmployees() {
        var handler = dependencies.EmployeeHandler;
        
        if (handler != null) {
            employees = handler.GetEmployees();
            ClearEmployees();
            FillComboEmployee();
        }
    }
    
    public void SetSchedule(Schedule schedule) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        scheduleId = schedule.Id;
        
        var patientIndex = GetPatientIndex(schedule.PatientId);
        var employeeIndex = GetEmployeeIndex(schedule.EmployeeId);
        
        ComboPatient.setSelectedIndex(patientIndex);
        ComboEmployee.setSelectedIndex(employeeIndex);
        
        TextDate.setText(formatter.format(schedule.Date));
    }
    
    private void ClearPatients() {
        ComboPatient.removeAllItems();
    }
    
    private void ClearEmployees() {
        ComboEmployee.removeAllItems();
    }
    
    private void FillComboEmployee() {
        if (employees != null) {
            var count = employees.size();
            
            for (var i = 0; i < count; ++i) {
                ComboEmployee.addItem(employees.get(i).Name);
            }
        }
    }
    
    private void FillComboPatients() {
        if (patients != null) {
            var count = patients.size();
            
            for (var i = 0; i < count; ++i) {
                ComboPatient.addItem(patients.get(i).Name);
            }
        }
    }
    
    private void FindByPatient() {
        var handler = dependencies.PatientHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                patients = handler.FindByName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                patients = handler.FindByDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            patients = handler.GetPatients();
        }
        
        ClearPatients();
        FillComboPatients();
    }
    
    private void FindByEmployee() {
        var handler = dependencies.EmployeeHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                employees = handler.FindByName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                employees = handler.FindByDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            employees = handler.GetEmployees();
        }
        
        ClearEmployees();
        FillComboEmployee();
    }
    
    private int GetPatientIndex(int id) {
        var count = patients.size();
        
        for (var i = 0; i < count; ++i) {
            if (patients.get(i).Id == id) {
                return i;
            }
        }
        
        return -1;
    }
    
    private int GetEmployeeIndex(int id) {
        var count = employees.size();
        
        for (var i = 0; i < count; ++i) {
            if (employees.get(i).Id == id) {
                return i;
            }
        }
        
        return -1;
    }
    
    private boolean CanValidateControl() {
        if (ComboPatient.getSelectedIndex() < 0){
            ShowErrorMessage("Um paciente deve ser selecionado.");
            return false;
        }
        
        if (ComboEmployee.getSelectedIndex() < 0){
            ShowErrorMessage("Um colaborador deve ser selecionado.");
            return false;
        }
        
        if (TextDate.getText() == "  /  /       :  "){
            ShowErrorMessage("A data e hora não pode estar vazia.");
            return false;
        }
        
        if (TextDate.getText().length() == 0) {
            ShowErrorMessage("A data e hora não pode estar vazia.");
            return false;
        }
        
        return true;
    }
    
    private Schedule CreateSchedule() {
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        var schedule = new Schedule();
        
        var patient = patients.get(ComboPatient.getSelectedIndex());
        var employee = employees.get(ComboEmployee.getSelectedIndex());
        var date = TextDate.getText();
        
        try {
            schedule.Id = scheduleId;
            schedule.EmployeeId = employee.Id;
            schedule.PatientId = patient.Id;
            schedule.Date = formatter.parse(date);
            schedule.State = 0;
        }
        catch (ParseException ex) {
            return null;
        }
        
        return schedule;
    }
    
    private void ShowSuccessMessage(String message) {
        MessageBox.Show(this,
                message,
                "Sucesso",
                "Agendamento",
                MessageBoxInformation.Information
        );
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Fornecedor",
                MessageBoxInformation.Error
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioFind = new javax.swing.ButtonGroup();
        RadioPerson = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        PatientTextDocument = new javax.swing.JTextField();
        EmployeeTextRole = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        TextFind = new javax.swing.JTextField();
        RadioName = new javax.swing.JRadioButton();
        RadioDocument = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        RadioPatient = new javax.swing.JRadioButton();
        RadioEmployee = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        PatientTextName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        EmployeeTextDocument = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ButtonRegister = new javax.swing.JButton();
        EmployeeTextName = new javax.swing.JTextField();
        ComboPatient = new javax.swing.JComboBox<>();
        PatientTextEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ComboEmployee = new javax.swing.JComboBox<>();
        TextDate = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agendamento");
        setResizable(false);

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setText("Email: ");

        jLabel9.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Selecione um colaborador para a consulta ...");

        PatientTextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextDocument.setEnabled(false);

        EmployeeTextRole.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        EmployeeTextRole.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setText("CPF:");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar Por:");

        jLabel10.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pressione ENTER para iniciar a pesquisa.");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        RadioPatient.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioPatient.setSelected(true);
        RadioPatient.setText("Paciente");

        RadioEmployee.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioEmployee.setText("Colaborador");

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel11.setText("Pesquisar Por:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(RadioPatient)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RadioEmployee)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(RadioPatient)
                    .addComponent(RadioEmployee))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        PatientTextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextName.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setText("Nome: ");

        jLabel6.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Selecione uma pessoa como paciente ...");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setText("Cargo: ");

        EmployeeTextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        EmployeeTextDocument.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setText("CPF:");

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        EmployeeTextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        EmployeeTextName.setEnabled(false);

        ComboPatient.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboPatient.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPatientItemStateChanged(evt);
            }
        });

        PatientTextEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextEmail.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setText("Nome: ");

        ComboEmployee.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboEmployee.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboEmployeeItemStateChanged(evt);
            }
        });

        try {
            TextDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TextDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Data da Consulta:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ComboPatient, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PatientTextName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PatientTextDocument, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PatientTextEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EmployeeTextName)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EmployeeTextDocument)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EmployeeTextRole)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(ComboEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextDate, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(204, 204, 204))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(PatientTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientTextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(5, 5, 5)
                        .addComponent(EmployeeTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EmployeeTextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EmployeeTextRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ButtonRegister)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (RadioPatient.isSelected()){
                FindByPatient();
            }
            else if (RadioEmployee.isSelected()) {
                FindByEmployee();
            }
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        if (CanValidateControl()) {
            var schedule = CreateSchedule();
            var handler = dependencies.ScheduleHandler;
            
            if (schedule != null) {
                
                if (operationState == DBOperationState.Update) {
                    handler.Update(schedule);
                    dependencies.Notifier.Notify(Changes.Schedule);
                    
                    ShowSuccessMessage("Agendamento atualizado.");
                }
                
                if (operationState == DBOperationState.Insert) {
                    handler.Put(schedule);
                    dependencies.Notifier.Notify(Changes.Schedule);
                    
                    Clear();
                    ShowSuccessMessage("Paciente cadastrado.");
                }
            }
        }
    }//GEN-LAST:event_ButtonRegisterActionPerformed

    private void ComboPatientItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPatientItemStateChanged
        var index = ComboPatient.getSelectedIndex();
        
        if (index >= 0 ) {
            var patient = patients.get(index);
            
            PatientTextName.setText(patient.Name);
            PatientTextDocument.setText(patient.Document);
            PatientTextEmail.setText(patient.Email);
        }
        else {
            PatientTextName.setText(Strings.Empty);
            PatientTextDocument.setText(Strings.Empty);
            PatientTextEmail.setText(Strings.Empty);
        }
    }//GEN-LAST:event_ComboPatientItemStateChanged

    private void ComboEmployeeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboEmployeeItemStateChanged
        var index = ComboEmployee.getSelectedIndex();
        
        if (index >= 0 ) {
            var employee = employees.get(index);
            
            EmployeeTextName.setText(employee.Name);
            EmployeeTextDocument.setText(employee.Document);
            EmployeeTextRole.setText(employee.Role);
        }
        else {
            EmployeeTextName.setText(Strings.Empty);
            EmployeeTextDocument.setText(Strings.Empty);
            EmployeeTextRole.setText(Strings.Empty);
        }
    }//GEN-LAST:event_ComboEmployeeItemStateChanged
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
* For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
*/
try {
    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            javax.swing.UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (ClassNotFoundException ex) {
    java.util.logging.Logger.getLogger(WindowScheduleDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (InstantiationException ex) {
    java.util.logging.Logger.getLogger(WindowScheduleDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (IllegalAccessException ex) {
    java.util.logging.Logger.getLogger(WindowScheduleDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (javax.swing.UnsupportedLookAndFeelException ex) {
    java.util.logging.Logger.getLogger(WindowScheduleDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
}
//</editor-fold>

/* Create and display the form */
java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new WindowScheduleDetail().setVisible(true);
    }
});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JComboBox<String> ComboEmployee;
    private javax.swing.JComboBox<String> ComboPatient;
    private javax.swing.JTextField EmployeeTextDocument;
    private javax.swing.JTextField EmployeeTextName;
    private javax.swing.JTextField EmployeeTextRole;
    private javax.swing.JTextField PatientTextDocument;
    private javax.swing.JTextField PatientTextEmail;
    private javax.swing.JTextField PatientTextName;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.JRadioButton RadioEmployee;
    private javax.swing.ButtonGroup RadioFind;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.JRadioButton RadioPatient;
    private javax.swing.ButtonGroup RadioPerson;
    private javax.swing.JFormattedTextField TextDate;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
