package Engine.Window;

import Engine.City;
import Engine.Country;
import Engine.Common.Strings;
import Engine.Database.DBOperationState;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationRegion;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class WindowRegion extends javax.swing.JFrame {
    private NotificationRegion notification;
    // Configurações e interfaces.
    private DependencyRepository dependencies;
    // Lista de países.
    private List<Country> countries;
    // Lista de cidades.
    private List<City> cities;
    // Operação de inserção ou atualização.
    private DBOperationState countryOperationState;
    private DBOperationState cityOperationState;
    // País selecionado na lista.
    private int selectedCountry;
    private int selectedCity;
    
    public WindowRegion() {
        initComponents();
    }
    
    public void UpdateWindow() {
        // Esta janela é alterada manualmente.
        // Portanto, nenhum código é inserido.
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationRegion(this);
        notification.Add(Changes.Country);
        notification.Add(Changes.City);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void LoadCountries() {
        var handler = dependencies.RegionHandler;
        
        if (handler != null) {
            countries = handler.GetCountries();
        }
    }
    
    public void LoadCities() {
        var handler = dependencies.RegionHandler;
        
        if (handler != null) {
            cities = handler.GetCities();
        }
    }
    
    public void FillCountries() {
        if (countries != null) {
            var count = countries.size();
            var model = new DefaultListModel<String>();
            
            for (var i = 0; i < count; ++i) {
                model.add(i, countries.get(i).Name);
            }
            
            ListCountry.setModel(model);
        }
    }
    
    public void FillCities() {
        if (cities != null) {
            var count = cities.size();
            var model = new DefaultListModel<String>();
            
            for (var i = 0; i < count; ++i) {
                var city = cities.get(i);
                
                model.add(i, city.Name + " - " + city.CountryName);
            }
            
            ListCity.setModel(model);
        }
    }
    
    public void ClearCountries() {
        var model = ListCountry.getModel();
        
        if (model.getSize() > 0) {
            var list = (DefaultListModel<String>)model;
            list.clear();
        }
    }
    
    public void ClearCities() {
        var model = ListCity.getModel();
        
        if (model.getSize() > 0) {
            var list = (DefaultListModel<String>)model;
            list.clear();
        }
    }
    
    public void ClearCityCountry() {
        ComboCityCountry.removeAllItems();
    }
    
    public void FillCityCountry() {
        if (countries != null) {
            var count = countries.size();
            
            for (var i = 0; i < count; ++i) {
                ComboCityCountry.addItem(countries.get(i).Name);
            }
        }
    }
    
    private void SetEnabledFieldCountry(boolean enabled) {
        TextCountryName.setEnabled(enabled);
        ButtonSaveCountry.setEnabled(enabled);
        ButtonCancelCountry.setEnabled(enabled);
    }
    
    private void SetEnabledFieldCity(boolean enabled) {
        TextCityName.setEnabled(enabled);
        ComboCityCountry.setEnabled(enabled);
        ButtonSaveCity.setEnabled(enabled);
        ButtonCancelCity.setEnabled(enabled);
    }
    
    private void SetEnabledEditNewExcludeCountryButtons(boolean enabled) {
        ButtonEditCountry.setEnabled(enabled);
        ButtonExcludeCountry.setEnabled(enabled);
        ListCountry.setEnabled(enabled);
    }
    
    private void SetEnabledEditNewExcludeCityButtons(boolean enabled) {
        ButtonEditCity.setEnabled(enabled);
        ButtonExcludeCity.setEnabled(enabled);
        ListCity.setEnabled(enabled);
    }
    
    private void ShowMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "Houve um problema:\n\n" + message,
                "Região",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void ShouldEnableButtonInListCountry() {
        if (ListCountry.getSelectedIndex() >= 0) {
            ButtonEditCountry.setEnabled(true);
            ButtonExcludeCountry.setEnabled(true);
        }
        else {
            ButtonEditCountry.setEnabled(false);
            ButtonExcludeCountry.setEnabled(false);
        }
    }
    
    private void ShouldEnableButtonInListCity() {
        if (ListCity.getSelectedIndex() >= 0) {
            ButtonEditCity.setEnabled(true);
            ButtonExcludeCity.setEnabled(true);
        }
        else {
            ButtonEditCity.setEnabled(false);
            ButtonExcludeCity.setEnabled(false);
        }
    }
    
    private void SelectCityCountry(int countryId) {
        if (countries != null) {
            var count = countries.size();
            
            for (var i = 0; i < count; ++i) {
                var country = countries.get(i);
                
                if (country.Id == countryId) {
                    ComboCityCountry.setSelectedIndex(i);
                    return;
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ButtonNewCountry = new javax.swing.JButton();
        ButtonEditCountry = new javax.swing.JButton();
        ButtonExcludeCountry = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListCountry = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        TextCountryName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ButtonSaveCountry = new javax.swing.JButton();
        ButtonCancelCountry = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListCity = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        ButtonNewCity = new javax.swing.JButton();
        ButtonEditCity = new javax.swing.JButton();
        ButtonExcludeCity = new javax.swing.JButton();
        ButtonSaveCity = new javax.swing.JButton();
        ButtonCancelCity = new javax.swing.JButton();
        TextCityName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ComboCityCountry = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Região - Cidades e Países");
        setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ButtonNewCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonNewCountry.setText("Novo");
        ButtonNewCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNewCountryActionPerformed(evt);
            }
        });

        ButtonEditCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonEditCountry.setText("Editar");
        ButtonEditCountry.setEnabled(false);
        ButtonEditCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditCountryActionPerformed(evt);
            }
        });

        ButtonExcludeCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonExcludeCountry.setText("Excluir");
        ButtonExcludeCountry.setEnabled(false);
        ButtonExcludeCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonExcludeCountryActionPerformed(evt);
            }
        });

        ListCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ListCountry.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListCountryValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ListCountry);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Países:");

        TextCountryName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextCountryName.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setText("Nome:");

        ButtonSaveCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonSaveCountry.setText("Salvar");
        ButtonSaveCountry.setEnabled(false);
        ButtonSaveCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSaveCountryActionPerformed(evt);
            }
        });

        ButtonCancelCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonCancelCountry.setText("Cancelar");
        ButtonCancelCountry.setEnabled(false);
        ButtonCancelCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelCountryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(TextCountryName)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ButtonNewCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonEditCountry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonExcludeCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ButtonSaveCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonCancelCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonNewCountry)
                    .addComponent(ButtonEditCountry)
                    .addComponent(ButtonExcludeCountry))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextCountryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSaveCountry)
                    .addComponent(ButtonCancelCountry))
                .addGap(19, 19, 19))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ListCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ListCity.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListCityValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(ListCity);

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Cidades:");

        ButtonNewCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonNewCity.setText("Novo");
        ButtonNewCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNewCityActionPerformed(evt);
            }
        });

        ButtonEditCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonEditCity.setText("Editar");
        ButtonEditCity.setEnabled(false);
        ButtonEditCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditCityActionPerformed(evt);
            }
        });

        ButtonExcludeCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonExcludeCity.setText("Excluir");
        ButtonExcludeCity.setEnabled(false);
        ButtonExcludeCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonExcludeCityActionPerformed(evt);
            }
        });

        ButtonSaveCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonSaveCity.setText("Salvar");
        ButtonSaveCity.setEnabled(false);
        ButtonSaveCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSaveCityActionPerformed(evt);
            }
        });

        ButtonCancelCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonCancelCity.setText("Cancelar");
        ButtonCancelCity.setEnabled(false);
        ButtonCancelCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelCityActionPerformed(evt);
            }
        });

        TextCityName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextCityName.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setText("Nome:");

        ComboCityCountry.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboCityCountry.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setText("País:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(ButtonSaveCity, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonCancelCity, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel4)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(ButtonNewCity, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonEditCity, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ButtonExcludeCity, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2)
                        .addComponent(TextCityName)
                        .addComponent(ComboCityCountry, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonExcludeCity)
                    .addComponent(ButtonEditCity)
                    .addComponent(ButtonNewCity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextCityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboCityCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSaveCity)
                    .addComponent(ButtonCancelCity))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonExcludeCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeCountryActionPerformed
        var index = ListCountry.getSelectedIndex();
        
        if (index >= 0) {
            var handler = dependencies.RegionHandler;
            var country = countries.get(index);
            
            if (handler.CanDelete(country)){
                handler.Delete(country);
                
                LoadCountries();
                ClearCountries();
                FillCountries();
                ClearCityCountry();
                FillCityCountry();
                
                ShouldEnableButtonInListCountry();
                
                dependencies.Notifier.Notify(Changes.Country);
            }
            else {
                ShowMessage("Este país não pode ser deletado pois está vinculado à uma cidade.");
            }
        }
    }//GEN-LAST:event_ButtonExcludeCountryActionPerformed
    private void ButtonEditCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditCountryActionPerformed
        countryOperationState = DBOperationState.Update;
        selectedCountry = ListCountry.getSelectedIndex();
        
        SetEnabledEditNewExcludeCountryButtons(false);
        SetEnabledFieldCountry(true);  
    }//GEN-LAST:event_ButtonEditCountryActionPerformed

    private void ButtonNewCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNewCountryActionPerformed
        countryOperationState = DBOperationState.Insert;
        
        SetEnabledEditNewExcludeCountryButtons(false);
        SetEnabledFieldCountry(true);       
    }//GEN-LAST:event_ButtonNewCountryActionPerformed

    private void ButtonSaveCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSaveCountryActionPerformed
        if (TextCountryName.getText().length() == 0) {
            ShowMessage("O nome do país não pode estar vazio.");
            return;
        }
        
        var handler = dependencies.RegionHandler;
        var name = TextCountryName.getText().trim();
        var success = false;
        
        if (countryOperationState == DBOperationState.Insert) {
            if (handler.ExistsCountry(name)) {
                ShowMessage("Este país já está cadastrado.\nInsira um novo nome.");
            }
            else {
                var country = new Country();
                country.Name = name;
                
                handler.Put(country);
                
                LoadCountries();
                ClearCountries();
                FillCountries();
                ClearCityCountry();
                FillCityCountry();
                
                success = true;
            }
        }
        else if (countryOperationState == DBOperationState.Update) {
            
            if (handler.ExistsCountry(name)) {
                ShowMessage("Este país já está cadastrado.\nInsira um novo nome.");
            }
            else {
                var country = countries.get(selectedCountry);
                country.Name = name;
                
                handler.Update(country);
                
                LoadCountries();
                ClearCountries();
                FillCountries();
                ClearCityCountry();
                FillCityCountry();
                
                success = true;
            }
        }
        
        if (success) {
            ListCountry.setEnabled(true);
            ShouldEnableButtonInListCountry();
            TextCountryName.setText(Strings.Empty);
            SetEnabledFieldCountry(false);
            
            dependencies.Notifier.Notify(Changes.Country);
        }
    }//GEN-LAST:event_ButtonSaveCountryActionPerformed

    private void ButtonCancelCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelCountryActionPerformed
        ListCountry.setEnabled(true);
        TextCountryName.setText(Strings.Empty);
        ShouldEnableButtonInListCountry();
        SetEnabledFieldCountry(false);
    }//GEN-LAST:event_ButtonCancelCountryActionPerformed
    private void ButtonNewCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNewCityActionPerformed
        cityOperationState = DBOperationState.Insert;
        TextCityName.setText(Strings.Empty);
        
        SetEnabledEditNewExcludeCityButtons(false);
        SetEnabledFieldCity(true);       
    }//GEN-LAST:event_ButtonNewCityActionPerformed

    private void ButtonEditCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditCityActionPerformed
        cityOperationState = DBOperationState.Update;
        selectedCity = ListCity.getSelectedIndex();
        
        SetEnabledEditNewExcludeCityButtons(false);
        SetEnabledFieldCity(true);  
    }//GEN-LAST:event_ButtonEditCityActionPerformed

    private void ButtonExcludeCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeCityActionPerformed
        var index = ListCity.getSelectedIndex();
        
        if (index >= 0) {
            var handler = dependencies.RegionHandler;
            var city = cities.get(index);
            
            if (handler.CanDelete(city)) {
                handler.Delete(city);
                
                LoadCities();
                ClearCities();
                FillCities();
                
                ShouldEnableButtonInListCity();
                
                dependencies.Notifier.Notify(Changes.City);
            }
            else {
                ShowMessage("Esta cidade não pode ser excluída pois está vinculada à um endereço.");
            }
        }
    }//GEN-LAST:event_ButtonExcludeCityActionPerformed

    private void ButtonSaveCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSaveCityActionPerformed
        if (TextCityName.getText().length() == 0) {
            ShowMessage("O nome da cidade não pode estar vazio.");
            return;
        }
        
        if (ComboCityCountry.getSelectedIndex() < 0) {
            ShowMessage("Um país deve ser selecionado para esta cidade.");
            return;
        }
        
        var handler = dependencies.RegionHandler;
        var name = TextCityName.getText().trim();
        var countryIndex = ComboCityCountry.getSelectedIndex();
        var countryId = countries.get(countryIndex).Id;
        var countryName = countries.get(countryIndex).Name;
        var success = false;
        
        if (cityOperationState == DBOperationState.Insert) {
            if (handler.ExistsCity(name, countryId)) {
                ShowMessage("Esta cidade já está cadastrada para o país " + countryName + ".\nInsira um novo nome.");
            }
            else {
                var city = new City();
                
                city.Name = name;
                city.CountryId = countryId;
                city.CountryName = countryName;
                
                handler.Put(city);
                
                LoadCities();
                ClearCities();
                FillCities();
                
                success = true;
            }
        }
        else if (cityOperationState == DBOperationState.Update) {
            var city = cities.get(selectedCity);
            var update = false;
            
            var cityLower = city.Name.toLowerCase();
            var nameLower = name.toLowerCase();
            
            if (cityLower.compareTo(nameLower) != 0) {
                if (handler.ExistsCity(name, countryId)) {
                    ShowMessage("Esta cidade já está cadastrada para o país " + countryName + ".\nInsira um novo nome.");
                }
                else {
                    update = true;
                }
            }
            else {
                update = true;
            }
            
            if (update) {
                city.Name = name;
                city.CountryId = countries.get(countryIndex).Id;
                city.CountryName = countries.get(countryIndex).Name;
                
                handler.Update(city);
                
                LoadCities();
                ClearCities();
                FillCities();
                
                success = true;
            }
        }
        
        if (success) {
            ListCity.setEnabled(true);
            ShouldEnableButtonInListCity();
            TextCityName.setText(Strings.Empty);
            ComboCityCountry.setSelectedIndex(-1);
            ComboCityCountry.setSelectedItem(Strings.Empty);
            SetEnabledFieldCity(false);
            
            dependencies.Notifier.Notify(Changes.City);
        }
    }//GEN-LAST:event_ButtonSaveCityActionPerformed

    private void ButtonCancelCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelCityActionPerformed
        ListCity.setEnabled(true);
        TextCityName.setText(Strings.Empty);
        ShouldEnableButtonInListCity();
        SetEnabledFieldCity(false);
    }//GEN-LAST:event_ButtonCancelCityActionPerformed

    private void ListCountryValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListCountryValueChanged
        if (ListCountry.isEnabled()) {
            ShouldEnableButtonInListCountry();
        }
    }//GEN-LAST:event_ListCountryValueChanged

    private void ListCityValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListCityValueChanged
        if (ListCity.isEnabled()) {
            ShouldEnableButtonInListCity();
            
            var index = ListCity.getSelectedIndex();
            
            if (index >= 0 && cities.size() > 0) {
                var city = cities.get(index);
                
                TextCityName.setText(city.Name);
                SelectCityCountry(city.CountryId);
            }
        }
    }//GEN-LAST:event_ListCityValueChanged
    
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
    java.util.logging.Logger.getLogger(WindowRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (InstantiationException ex) {
    java.util.logging.Logger.getLogger(WindowRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (IllegalAccessException ex) {
    java.util.logging.Logger.getLogger(WindowRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (javax.swing.UnsupportedLookAndFeelException ex) {
    java.util.logging.Logger.getLogger(WindowRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
}
//</editor-fold>

/* Create and display the form */
java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new WindowRegion().setVisible(true);
    }
});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelCity;
    private javax.swing.JButton ButtonCancelCountry;
    private javax.swing.JButton ButtonEditCity;
    private javax.swing.JButton ButtonEditCountry;
    private javax.swing.JButton ButtonExcludeCity;
    private javax.swing.JButton ButtonExcludeCountry;
    private javax.swing.JButton ButtonNewCity;
    private javax.swing.JButton ButtonNewCountry;
    private javax.swing.JButton ButtonSaveCity;
    private javax.swing.JButton ButtonSaveCountry;
    private javax.swing.JComboBox<String> ComboCityCountry;
    private javax.swing.JList<String> ListCity;
    private javax.swing.JList<String> ListCountry;
    private javax.swing.JTextField TextCityName;
    private javax.swing.JTextField TextCountryName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}