package Engine.Window;

import Engine.Common.Strings;
import Engine.Product;
import Engine.Schedule;
import Engine.Treatment;
import Engine.TreatmentProduct;
import Engine.Communication.DependencyRepository;
import Engine.Database.DBOperationState;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationTreatmentDetail;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WindowTreatmentDetail extends javax.swing.JDialog {
    private NotificationTreatmentDetail notification;
    private DependencyRepository dependencies;
    private DBOperationState operationState;
    private DBOperationState productOperationState;
    private List<TreatmentProduct> treatmentProducts;
    private List<TreatmentProduct> removedTreatmentProducts;
    private List<Product> products;
    
    private int treatmentId;
    private Date finishedDate;
    private int scheduleId;
    private int selectedRow;
    
    public WindowTreatmentDetail() {
        initComponents();
        
        this.setResizable(false);
        this.setModal(true);
        
        treatmentProducts = new ArrayList<TreatmentProduct>();
        removedTreatmentProducts = new ArrayList<TreatmentProduct>();
    }
    
    public void UpdateWindow() {
        
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationTreatmentDetail(this);
        notification.Add(Changes.Person);
        notification.Add(Changes.Employee);
        notification.Add(Changes.Schedule);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void Clear() {
        treatmentId = 0;
        scheduleId = 0;
        selectedRow= -1;
        finishedDate = GetDateNow();
        
        PatientTextName.setText(Strings.Empty);
        EmployeeTextName.setText(Strings.Empty);
        ScheduleStartDate.setText(Strings.Empty);
        ScheduleEndDate.setText(Strings.Empty);
        
        treatmentProducts.clear();
        removedTreatmentProducts.clear();
        
        ClearTable();
        ClearComboProducts();
        
        TextFind.setText(Strings.Empty);
        TextCount.setText(Strings.Empty);
        
        SetEnabledProductPanel(false);
    }
    
    public void SetOperationState(DBOperationState operation) {
        operationState = operation;
        
        if (operation == DBOperationState.Insert) {
            this.setTitle("Finalizar e Cadastrar Tratamento");
            ButtonRegister.setText("Cadastrar");
        }
        else if (operation == DBOperationState.Update) {
            this.setTitle("Atualizar Tratamento");
            ButtonRegister.setText("Atualizar");
        }
    }
    
    public void SetTreatment(Treatment treatment) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        treatmentId = treatment.Id;
        scheduleId = treatment.ScheduleId;
        finishedDate = treatment.FinishedDate;
        PatientTextName.setText(treatment.PatientName);
        EmployeeTextName.setText(treatment.EmployeeName);
        ScheduleStartDate.setText(formatter.format(treatment.Date));
        ScheduleEndDate.setText(formatter.format(treatment.FinishedDate));
        
    }
    
    public void SetSchedule(Schedule schedule) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        scheduleId = schedule.Id;
        PatientTextName.setText(schedule.PatientName);
        EmployeeTextName.setText(schedule.EmployeeName);
        ScheduleStartDate.setText(formatter.format(schedule.Date));
        ScheduleEndDate.setText(formatter.format(GetDateNow()));
    }
    
    public void LoadTreatmentProducts() {
        var handler = dependencies.TreatmentHandler;
        
        if (handler != null) {
            treatmentProducts = handler.GetProducts(treatmentId);
            ClearTable();
            FillTreatmentProducts();
        }
    }
    
    private void FillTreatmentProducts() {
        if (treatmentProducts != null) {
            var count = treatmentProducts.size();
            var model = (DefaultTableModel)TableTreatment.getModel();
            
            for (var i = 0; i < count; i++) {
                var product = treatmentProducts.get(i);
                
                Object[] row = {
                    product.Code,
                    product.Name,
                    product.Count,
                    String.format("R$ " + "%1$,.2f", product.Price)
                };
                
                model.addRow(row);
            }
        }
    }
    
    private void ClearTable() {
        var model = (DefaultTableModel)TableTreatment.getModel();
        model.setRowCount(0);
        
        selectedRow = -1;
        
        TableTreatment.revalidate();
    }
    
    private void ShowSuccessMessage(String message) {
        MessageBox.Show(this,
                message,
                "Sucesso",
                "Tratamento",
                MessageBoxInformation.Information
        );
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Tratamento",
                MessageBoxInformation.Error
        );
    }
    
    private void Find() {
        var handler = dependencies.ProductHandler;
        
        if (TextFind.getText().length() > 0) {
            products = handler.Find(TextFind.getText().trim());
        }
        else {
            products = handler.GetProducts();
        }
        
        ClearComboProducts();
        FillComboProducts();
    }
    
    private void ClearComboProducts() {
        ComboProducts.removeAllItems();
    }
    
    private void FillComboProducts() {
        if (products != null) {
            var count = products.size();
            
            for (var i = 0; i < count; ++i) {
                ComboProducts.addItem(products.get(i).Name);
            }
        }
    }
    
    private Date GetDateNow() {
        var calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    private void SetEnabledButtons(boolean enabled) {
        ButtonRemove.setEnabled(enabled);
        ButtonEdit.setEnabled(enabled);
    }
    
    private void SetEnabledProductPanel(boolean enabled) {
        TextFind.setEnabled(enabled);
        ComboProducts.setEnabled(enabled);
        TextCount.setEnabled(enabled);
        ButtonUpdate.setEnabled(enabled);
        ButtonCancel.setEnabled(enabled);
    }
    
    private boolean IsValidUsedProductCount() {
        var result = Strings.HasLetters(TextCount.getText());
        
        if (result) {
            ShowErrorMessage("Use somente números na quantidade.");
            return false;
        }
        
        var count = Integer.parseInt(TextCount.getText());
        
        if (count <= 0) {
            ShowErrorMessage("Uma quantidade maior que zero deve ser informada.");
            return false;
        }
        
        return true;
    }
    
    private Treatment CreateTreatment() {
        var treatment = new Treatment();
        
        treatment.Id = treatmentId;
        treatment.ScheduleId = scheduleId;
        treatment.FinishedDate = finishedDate;
        treatment.Products.addAll(treatmentProducts);
        treatment.Products.addAll(removedTreatmentProducts);
        
        if (operationState == DBOperationState.Insert) {
            treatment.FinishedDate = GetDateNow();
        }
        
        return treatment;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonRegister = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        TextFind = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TextCount = new javax.swing.JTextField();
        ButtonUpdate = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        ComboProducts = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        ButtonCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        PatientTextName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        EmployeeTextName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ScheduleStartDate = new javax.swing.JTextField();
        ScheduleEndDate = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableTreatment = new javax.swing.JTable();
        ButtonAdd = new javax.swing.JButton();
        ButtonEdit = new javax.swing.JButton();
        ButtonRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tratamento");

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todos os produtos.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Adicionar Produto");

        jLabel2.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Pressione ENTER para iniciar a pesquisa.");

        TextCount.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextCount.setText("0");
        TextCount.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todos os produtos.");
        TextCount.setEnabled(false);

        ButtonUpdate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonUpdate.setText("Salvar");
        ButtonUpdate.setEnabled(false);
        ButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonUpdateActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Quantidade:");

        ComboProducts.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboProducts.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Selecione um produto para adicionar à lista ...");

        ButtonCancel.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonCancel.setText("Cancelar");
        ButtonCancel.setEnabled(false);
        ButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(TextFind, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(ButtonUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(ButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(TextCount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ComboProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextCount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setText("Paciente:");

        PatientTextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextName.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setText("Colaborador:");

        EmployeeTextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        EmployeeTextName.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setText("Data da Consulta:");

        ScheduleStartDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ScheduleStartDate.setEnabled(false);

        ScheduleEndDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ScheduleEndDate.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setText("Data de Finalização:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(198, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(EmployeeTextName, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PatientTextName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScheduleStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ScheduleEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(153, 153, 153))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(PatientTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ScheduleStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(5, 5, 5)
                        .addComponent(EmployeeTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ScheduleEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel9.getAccessibleContext().setAccessibleName("");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Produtos Utilizados");

        TableTreatment.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TableTreatment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Quantidade", "Preço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableTreatment.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TableTreatment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableTreatmentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableTreatment);

        ButtonAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonAdd.setText("Adicionar");
        ButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddActionPerformed(evt);
            }
        });

        ButtonEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonEdit.setText("Editar");
        ButtonEdit.setEnabled(false);
        ButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditActionPerformed(evt);
            }
        });

        ButtonRemove.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRemove.setText("Remover");
        ButtonRemove.setEnabled(false);
        ButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableTreatmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableTreatmentMouseClicked
        var source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        
        SetEnabledButtons(false);
        
        selectedRow = -1;
        
        if (row >= 0 && treatmentProducts.size() > 0) {
            SetEnabledButtons(true);
            selectedRow = row;
        }
    }//GEN-LAST:event_TableTreatmentMouseClicked

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Find();
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void ButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonUpdateActionPerformed
        if (productOperationState == DBOperationState.None) {
            return;
        }
        
        var index = ComboProducts.getSelectedIndex();
        
        if (index >= 0) {
            if (!IsValidUsedProductCount()) {
                return;
            }
            
            var product = products.get(index);
            
            if (productOperationState == DBOperationState.Insert) {
                var tProduct = new TreatmentProduct();
                
                tProduct.TreatmentId = treatmentId;
                tProduct.ProductId = product.Id;
                tProduct.Code = product.Code;
                tProduct.Name = product.Name;
                tProduct.Price = product.Price;
                tProduct.Count = Integer.parseInt(TextCount.getText());
                tProduct.OperationState = DBOperationState.Insert;
                
                treatmentProducts.add(tProduct);
            }
            
            if (productOperationState == DBOperationState.Update) {
                var tProduct = treatmentProducts.get(selectedRow);
                
                tProduct.TreatmentId = treatmentId;
                tProduct.ProductId = product.Id;
                tProduct.Code = product.Code;
                tProduct.Name = product.Name;
                tProduct.Price = product.Price;
                tProduct.Count = Integer.parseInt(TextCount.getText());
                tProduct.OperationState = DBOperationState.Update;
            }
            
            ClearTable();
            FillTreatmentProducts();
            
            productOperationState = DBOperationState.None;
            SetEnabledProductPanel(false);
            ButtonAdd.setEnabled(true);
            
            TextFind.setText(Strings.Empty);
            TextCount.setText("0");
            ComboProducts.removeAllItems();
            ComboProducts.setSelectedIndex(-1);
            products.clear();
        }
        else {
            ShowErrorMessage("Um produto deve estar selecionado");
        }
    }//GEN-LAST:event_ButtonUpdateActionPerformed

    private void ButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelActionPerformed
        productOperationState = DBOperationState.None;
        SetEnabledProductPanel(false);
        ButtonAdd.setEnabled(true);
        
        TextFind.setText(Strings.Empty);
        TextCount.setText("0");
        ComboProducts.removeAllItems();
        ComboProducts.setSelectedIndex(-1);
        products.clear();   
    }//GEN-LAST:event_ButtonCancelActionPerformed

    private void ButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddActionPerformed
        SetEnabledButtons(false);
        ButtonAdd.setEnabled(false);
        SetEnabledProductPanel(true);
        productOperationState = DBOperationState.Insert;
    }//GEN-LAST:event_ButtonAddActionPerformed

    private void ButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRemoveActionPerformed
        if (selectedRow >= 0) {
            var tProduct = treatmentProducts.get(selectedRow);
            
            treatmentProducts.remove(tProduct);
                   
            // Somente adiciona para a lista de removidos, se
            // o produto já foi adicionado ao banco.
            if (tProduct.Id > 0 ) {
                tProduct.OperationState = DBOperationState.Delete;
                removedTreatmentProducts.add(tProduct);
            }
            
            selectedRow = -1;
            SetEnabledButtons(false);
            productOperationState = DBOperationState.None;
            
            ClearTable();
            FillTreatmentProducts();
        }       
    }//GEN-LAST:event_ButtonRemoveActionPerformed

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
        SetEnabledButtons(false);
        ButtonAdd.setEnabled(false);
        SetEnabledProductPanel(true);
        productOperationState = DBOperationState.Update;
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        var handler = dependencies.TreatmentHandler;
        var treatment = CreateTreatment();
        var success = false;
        
        if (operationState == DBOperationState.Insert) {
            handler.Put(treatment);
            handler.UpdateProducts(treatment.Products);
            
            var scheduler = dependencies.ScheduleHandler;
            var schedule = scheduler.Get(treatment.ScheduleId);
            
            // Define como finalizado.
            schedule.State = 1;
            scheduler.Update(schedule);
            
            dependencies.Notifier.Notify(Changes.Schedule);
            dependencies.Notifier.Notify(Changes.Treatment);
            
            Clear();
            
            ShowSuccessMessage("O tratamento foi finalizado e salvo.");
        }
        
        if (operationState == DBOperationState.Update) {
            handler.Update(treatment);
            handler.UpdateProducts(treatment.Products);
            
            dependencies.Notifier.Notify(Changes.Treatment);
            
            ShowSuccessMessage("O tratamento foi atualizado.");
        }
    }//GEN-LAST:event_ButtonRegisterActionPerformed
    
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
    java.util.logging.Logger.getLogger(WindowTreatmentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (InstantiationException ex) {
    java.util.logging.Logger.getLogger(WindowTreatmentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (IllegalAccessException ex) {
    java.util.logging.Logger.getLogger(WindowTreatmentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (javax.swing.UnsupportedLookAndFeelException ex) {
    java.util.logging.Logger.getLogger(WindowTreatmentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
}
//</editor-fold>

/* Create and display the form */
java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new WindowTreatmentDetail().setVisible(true);
    }
});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonAdd;
    private javax.swing.JButton ButtonCancel;
    private javax.swing.JButton ButtonEdit;
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JButton ButtonRemove;
    private javax.swing.JButton ButtonUpdate;
    private javax.swing.JComboBox<String> ComboProducts;
    private javax.swing.JTextField EmployeeTextName;
    private javax.swing.JTextField PatientTextName;
    private javax.swing.JTextField ScheduleEndDate;
    private javax.swing.JTextField ScheduleStartDate;
    private javax.swing.JTable TableTreatment;
    private javax.swing.JTextField TextCount;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}