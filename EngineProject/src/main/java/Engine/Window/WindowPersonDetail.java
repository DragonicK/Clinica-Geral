package Engine.Window;

import Engine.City;
import Engine.Person;
import Engine.Address;
import Engine.Contact;
import Engine.Common.Strings;
import Engine.Communication.DependencyRepository;
import Engine.Database.DBOperationState;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationPersonDetail;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WindowPersonDetail extends javax.swing.JDialog {
    private NotificationPersonDetail notification;
    private DependencyRepository dependencies;
    private DBOperationState personOperation;
    private List<City> cities;
    // Id de pessoa para atualização.
    private int personId;
    // Lista de endereços.
    private List<Address> addresses;
    private List<Address> removedAddresses;
    // Lista de telefones.
    private List<Contact> contacts;
    private List<Contact> removedContacts;
    private int selectedContact;
    private DBOperationState contactOperation;
    // Último cpf antes da edição.
    private String lastDocument;
    
    public WindowPersonDetail() {
        initComponents();
        this.setModal(true);
        this.setResizable(false);
        
        selectedContact = -1;
        
        addresses = new ArrayList<Address>();
        removedAddresses = new ArrayList<Address>();
        contacts = new ArrayList<Contact>();
        removedContacts = new ArrayList<Contact>();
    }
    
    public void UpdateWindow() {
        LoadCities();
    }
    
    private void ShowMessage(String message, String title) {
        JOptionPane.showMessageDialog(this,
                title + ":\n\n" + message,
                "Pessoa",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void ShowMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "Houve um problema:\n\n" + message,
                "Pessoa",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void Clear() {
        personId = 0;
        selectedContact= 0;
        addresses.clear();
        removedAddresses.clear();
        contacts.clear();
        removedContacts.clear();
        
        TextEmail.setText(Strings.Empty);
        TextName.setText(Strings.Empty);
        TextDocument.setText(Strings.Empty);
        TextBirthday.setText(Strings.Empty);
        
        ButtonEditAddress.setText("Editar");
        ButtonNewAddress.setText("Novo");
        
        ButtonNewAddress.setEnabled(true);
        ButtonEditAddress.setEnabled(false);
        ButtonExcludeAddress.setEnabled(false);
        
        ButtonRegister.setEnabled(true);
        ScrollAddress.setEnabled(false);
        
        TextAddress.setEnabled(false);
        ComboCity.setEnabled(false);
        
        ListPhone.setEnabled(true);
        TextPhone.setText(Strings.Empty);
        SelectFirstElementInListPhones();
        ShouldEnableButtonFromListPhone();
        SetEnabledFieldPhone(false);
        
        FillPhones();
        UpdateScrollAddressMaximum();
        ShouldEnableButtonFromListPhone();
    }
    
    // As dependências são injetadas.
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        // A janela somente é atualizada,
        // se alguma alteração em País ou Cidade ocorrer.
        notification = new NotificationPersonDetail(this);
        notification.Add(Changes.Country);
        notification.Add(Changes.City);
        
        dependencies.Notifier.Add(notification);
    }
    
    // Atividade atual do formulário, inserir ou atualizar.
    public void SetOperationState(DBOperationState operation) {
        personOperation = operation;
        
        if (personOperation == DBOperationState.Insert) {
            ButtonRegister.setText("Cadastrar");
            this.setTitle("Cadastrar Nova Pessoa");
        }
        else if (personOperation == DBOperationState.Update) {
            ButtonRegister.setText("Atualizar");
            this.setTitle("Editar Pessoa");
        }
    }
        
    public void LoadCities() {
        var handler = dependencies.RegionHandler;
        
        cities = handler.GetCities();
        
        ClearCityBox();
        FillCityBox();
    }
    
    public void SetPerson(Person person) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        var handler = dependencies.PersonHandler;
        
        personId = person.Id;
        TextName.setText(person.Name);
        TextDocument.setText(person.Document);
        TextEmail.setText(person.Email);
        TextBirthday.setText(formatter.format(person.Birthday));
        
        addresses = handler.GetAddresses(person);
        contacts = handler.GetPhones(person);
        
        lastDocument = person.Document;
        
        UpdateScrollAddressMaximum();
        UpdateAddressControls(ScrollAddress.getMaximum());
        FillPhones();
    }
    
    private void ClearCityBox() {
        ComboCity.removeAllItems();
    }
    
    private void FillCityBox() {
        if (cities != null) {
            var count = cities.size();
            
            for (var i = 0; i < count; ++i) {
                var city = cities.get(i);
                ComboCity.addItem(city.Name + " - " + city.CountryName);
            }
        }
    }
    
    private void FillPhones() {
        ListPhone.removeAll();
        
        if (contacts != null) {
            var count = contacts.size();
            var model = new DefaultListModel<String>();
            
            for (var i = 0; i < count; ++i) {
                model.add(i, contacts.get(i).Phone);
            }
            
            ListPhone.setModel(model);
            
            if (count > 0) {
                ListPhone.setSelectedIndex(0);
            }
        }
    }
    
    // Address #################################
    
    private int GetScrollCurrentValue() {
        var value = ScrollAddress.getValue();
        var maximum = ScrollAddress.getMaximum();
        
        if (value > maximum) {
            value = maximum;
        }
        
        return value;
    }
    
    private void UpdateScrollAddressMaximum() {
        if (addresses != null) {
            var size = addresses.size();
            
            if (size > 0) {
                ScrollAddress.setMaximum(size);
                LabelAddressCount.setText("1/" + size);
                
                if (ScrollAddress.getValue() == 0) {
                    ScrollAddress.setValue(1);
                }
                
                ScrollAddress.setMinimum(1);
                ButtonEditAddress.setEnabled(true);
                ButtonExcludeAddress.setEnabled(true);
            }
            else {
                LabelAddressCount.setText("0/0");
                ScrollAddress.setMaximum(0);
                ScrollAddress.setValue(0);
                ScrollAddress.setMinimum(0);
                ButtonEditAddress.setEnabled(false);
                ButtonExcludeAddress.setEnabled(false);
            }
        }
    }
    
    private void UpdateAddressControls(int index) {
        if (index > 0) {
            LabelAddressCount.setText(index + "/" + addresses.size());
            
            index--;
            
            var address = addresses.get(index);
            TextAddress.setText(address.Address);
            
            if (address.CityId > 0) {
                SelectCityInCombo(address.CityId);
            }
            else {
                ComboCity.setSelectedIndex(-1);
            }
        }
        else {
            LabelAddressCount.setText("0/" + addresses.size());
            
            TextAddress.setText(Strings.Empty);
            ComboCity.setSelectedIndex(-1);
        }
    }
    
    private void SelectCityInCombo(int cityId) {
        if (cities != null) {
            var count = cities.size();
            
            for (var i = 0; i < count; ++i) {
                var city = cities.get(i);
                
                if (city.Id == cityId) {
                    ComboCity.setSelectedIndex(i);
                }
            }
        }
    }
    
    private boolean CanValidateAddressControl() {
        var cityIndex = ComboCity.getSelectedIndex();
        
        if (cityIndex < 0) {
            ShowMessage("Uma cidade deve ser informada.\nO novo endereço não foi salvo.");
            return false;
        }
        
        if (TextAddress.getText().trim().length() == 0) {
            ShowMessage("O endereço deve ser informado.\nO novo endereço não foi salvo.");
            return false;
        }
        
        return true;
    }
    
    private void CreateNewAddress() {
        if (!CanValidateAddressControl() ) {
            return;
        }
        
        var city = cities.get(ComboCity.getSelectedIndex());
        var address = new Address();
        
        address.Address = TextAddress.getText().trim();
        address.CityId = city.Id;
        address.PersonId = personId;
        address.OperationState = DBOperationState.Insert;
        
        addresses.add(address);
    }
    
    private boolean CanEditAddress() {
        if (!CanValidateAddressControl() ) {
            return false;
        }
        
        var city = cities.get(ComboCity.getSelectedIndex());
        var address = addresses.get(ScrollAddress.getValue() - 1);
        
        address.Address = TextAddress.getText().trim();
        address.CityId = city.Id;
        address.PersonId = personId;
        
        // Se há o registro na db. Permite a alteração.
        if (address.Id > 0) {
            address.OperationState = DBOperationState.Update;
        }
        // Do contrário, insere um novo.
        else {
            address.OperationState = DBOperationState.Insert;
        }
        
        return true;
    }
    
    private void SetAddressEnabledToEdit(boolean enabled) {
        ButtonRegister.setEnabled(!enabled);
        ScrollAddress.setEnabled(!enabled);
        ButtonExcludeAddress.setEnabled(!enabled);
        
        TextAddress.setEnabled(enabled);
        ComboCity.setEnabled(enabled);
    }
    
    // Phone ##################################
    
    private void SelectFirstElementInListPhones() {
        if (ListPhone.getModel().getSize() > 0){
            ListPhone.setSelectedIndex(0);
        }
    }
    
    private void SetEnabledFieldPhone(boolean enabled) {
        TextPhone.setEnabled(enabled);
        ButtonSavePhone.setEnabled(enabled);
        ButtonCancelPhone.setEnabled(enabled);
    }
    
    private void SetEnabledEditNewExcludePhoneButtons(boolean enabled) {
        ButtonEditPhone.setEnabled(enabled);
        ButtonExcludePhone.setEnabled(enabled);
        ListPhone.setEnabled(enabled);
    }
    
    private void ShouldEnableButtonFromListPhone() {
        if (ListPhone.getSelectedIndex() >= 0) {
            ButtonEditPhone.setEnabled(true);
            ButtonExcludePhone.setEnabled(true);
        }
        else {
            ButtonEditPhone.setEnabled(false);
            ButtonExcludePhone.setEnabled(false);
        }
    }
    
    // Register
    
    private boolean CanValidatePersonControl() {
        if (TextName.getText().trim().length() == 0) {
            ShowMessage("O nome não pode estar em branco.");
            return false;
        }
        
        var document = TextDocument.getText();
        
        if (document.equals("   .   .   -  ")) {
            ShowMessage("O CPF não pode estar em branco.");
            return false;
        }
        
        if (Strings.HasLetters(document)) {
            ShowMessage("O CPF deve conter somente números.");
            return false;
        }
        
        if (TextEmail.getText().trim().length() == 0) {
            ShowMessage("O email não pode estar em branco.");
            return false;
        }
        
        var birthday = TextBirthday.getText();
        
        if (birthday.equals("  /  /    ")) {
            ShowMessage("A data de nascimento não pode estar em branco.");
            return false;
        }
        
        if (Strings.HasLetters(birthday)) {
            ShowMessage("A data de nascimento deve conter somente números.");
            return false;
        }
        
        return true;
    }
    
    private Person CreatePerson() {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        var date = TextBirthday.getText();
        var person  = new Person();
        
        try {
            person.Id = personId;
            person.Email = TextEmail.getText();
            person.Name = TextName.getText();
            person.Document = TextDocument.getText();
            person.Birthday = formatter.parse(date);
            
            person.Addresses = new ArrayList<Address>();
            person.Addresses.addAll(addresses);
            person.Addresses.addAll(removedAddresses);
            
            person.Contacts = new ArrayList<Contact>();
            person.Contacts.addAll(contacts);
            person.Contacts.addAll(removedContacts);
        }
        catch (ParseException ex) {
            return null;
        }
        
        return person;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        TextName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TextBirthday = new javax.swing.JFormattedTextField();
        TextDocument = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        ButtonNewAddress = new javax.swing.JButton();
        ButtonEditAddress = new javax.swing.JButton();
        ButtonExcludeAddress = new javax.swing.JButton();
        ComboCity = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        TextAddress = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ScrollAddress = new javax.swing.JScrollBar();
        LabelAddressCount = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListPhone = new javax.swing.JList<>();
        ButtonSavePhone = new javax.swing.JButton();
        ButtonEditPhone = new javax.swing.JButton();
        ButtonExcludePhone = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        TextPhone = new javax.swing.JFormattedTextField();
        ButtonNewPhone = new javax.swing.JButton();
        ButtonCancelPhone = new javax.swing.JButton();
        ButtonRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(370, 430));
        setPreferredSize(new java.awt.Dimension(370, 430));
        setResizable(false);
        setSize(new java.awt.Dimension(370, 430));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 263));

        TextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Nome:");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("CPF:");

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Email:");

        TextEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Data Nascimento:");

        try {
            TextBirthday.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TextBirthday.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        try {
            TextDocument.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextName, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informações", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ButtonNewAddress.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonNewAddress.setText("Novo");
        ButtonNewAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNewAddressActionPerformed(evt);
            }
        });

        ButtonEditAddress.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonEditAddress.setText("Editar");
        ButtonEditAddress.setEnabled(false);
        ButtonEditAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditAddressActionPerformed(evt);
            }
        });

        ButtonExcludeAddress.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonExcludeAddress.setText("Excluir");
        ButtonExcludeAddress.setEnabled(false);
        ButtonExcludeAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonExcludeAddressActionPerformed(evt);
            }
        });

        ComboCity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboCity.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Cidade:");

        TextAddress.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextAddress.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Endereço:");

        ScrollAddress.setMaximum(0);
        ScrollAddress.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        ScrollAddress.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                ScrollAddressAdjustmentValueChanged(evt);
            }
        });

        LabelAddressCount.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        LabelAddressCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelAddressCount.setText("0/0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboCity, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextAddress)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ButtonNewAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonEditAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonExcludeAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ScrollAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAddressCount, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelAddressCount)
                .addGap(2, 2, 2)
                .addComponent(ScrollAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonNewAddress)
                    .addComponent(ButtonEditAddress)
                    .addComponent(ButtonExcludeAddress))
                .addGap(46, 46, 46))
        );

        jTabbedPane1.addTab("Endereços", jPanel2);

        ListPhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ListPhone.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListPhoneValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ListPhone);

        ButtonSavePhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonSavePhone.setText("Salvar");
        ButtonSavePhone.setEnabled(false);
        ButtonSavePhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSavePhoneActionPerformed(evt);
            }
        });

        ButtonEditPhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonEditPhone.setText("Editar");
        ButtonEditPhone.setEnabled(false);
        ButtonEditPhone.setMargin(new java.awt.Insets(2, 12, 2, 16));
        ButtonEditPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditPhoneActionPerformed(evt);
            }
        });

        ButtonExcludePhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonExcludePhone.setText("Excluir");
        ButtonExcludePhone.setEnabled(false);
        ButtonExcludePhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonExcludePhoneActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Número de Telefone:");

        try {
            TextPhone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TextPhone.setEnabled(false);
        TextPhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        ButtonNewPhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonNewPhone.setText("Novo");
        ButtonNewPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNewPhoneActionPerformed(evt);
            }
        });

        ButtonCancelPhone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonCancelPhone.setText("Cancelar");
        ButtonCancelPhone.setEnabled(false);
        ButtonCancelPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelPhoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ButtonSavePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ButtonCancelPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(TextPhone)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(ButtonNewPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ButtonEditPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ButtonExcludePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(16, 16, 16))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonEditPhone)
                    .addComponent(ButtonNewPhone)
                    .addComponent(ButtonExcludePhone))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSavePhone)
                    .addComponent(ButtonCancelPhone))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Telefones", jPanel3);

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ButtonRegister)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Address ###################################
    
    private void ButtonNewAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNewAddressActionPerformed
        var text = ButtonNewAddress.getText();
        
        if (text.equals("Novo")) {
            ButtonNewAddress.setText("Salvar");
            ButtonEditAddress.setEnabled(false);
            SetAddressEnabledToEdit(true);
            
            TextAddress.setText(Strings.Empty);
            ComboCity.setSelectedIndex(-1);
        }
        else {
            ButtonNewAddress.setText("Novo");
            ButtonEditAddress.setEnabled(true);
            SetAddressEnabledToEdit(false);
            
            CreateNewAddress();
            UpdateScrollAddressMaximum();
            UpdateAddressControls(ScrollAddress.getMaximum());
        }
    }//GEN-LAST:event_ButtonNewAddressActionPerformed

    private void ButtonEditAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditAddressActionPerformed
        var text = ButtonEditAddress.getText();
        
        if (text.equals("Editar")) {
            ButtonEditAddress.setText("Salvar");
            ButtonNewAddress.setEnabled(false);
            SetAddressEnabledToEdit(true);
        }
        else {
            ButtonEditAddress.setText("Editar");
            ButtonNewAddress.setEnabled(true);
            SetAddressEnabledToEdit(false);
            
            if (CanEditAddress()) {
                UpdateScrollAddressMaximum();
            }
            
            UpdateAddressControls(GetScrollCurrentValue());
        }
    }//GEN-LAST:event_ButtonEditAddressActionPerformed
    private void ButtonExcludeAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeAddressActionPerformed
        var index = ScrollAddress.getValue();
        
        if (index > 0) {
            index--;
            
            var address = addresses.get(index);
            addresses.remove(address);
            
            // Somente adiciona para a lista de removidos se o endereço já foi adicionado à db.
            if (address.Id > 0) {
                address.OperationState = DBOperationState.Delete;
                removedAddresses.add(address);
            }
            
            UpdateScrollAddressMaximum();
            UpdateAddressControls(GetScrollCurrentValue());
        }
    }//GEN-LAST:event_ButtonExcludeAddressActionPerformed
    private void ScrollAddressAdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_ScrollAddressAdjustmentValueChanged
        if (ScrollAddress.isEnabled()) {
            UpdateAddressControls(GetScrollCurrentValue());
        }
    }//GEN-LAST:event_ScrollAddressAdjustmentValueChanged
    // Phone ##################################
    
    private void ButtonNewPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNewPhoneActionPerformed
        contactOperation = DBOperationState.Insert;
        
        SetEnabledFieldPhone(true);
        SetEnabledEditNewExcludePhoneButtons(false);
    }//GEN-LAST:event_ButtonNewPhoneActionPerformed

    private void ButtonEditPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditPhoneActionPerformed
        contactOperation = DBOperationState.Update;
        selectedContact = ListPhone.getSelectedIndex();
        
        var contact = contacts.get(selectedContact); 
        TextPhone.setText(contact.Phone);
        
        SetEnabledFieldPhone(true);
        SetEnabledEditNewExcludePhoneButtons(false);
    }//GEN-LAST:event_ButtonEditPhoneActionPerformed

    private void ButtonExcludePhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludePhoneActionPerformed
        var index = ListPhone.getSelectedIndex();
        
        if (index >= 0) {
            var contact = contacts.get(index);
            contacts.remove(contact);
            
            if (contact.Id > 0) {
                contact.OperationState = DBOperationState.Delete;
                removedContacts.add(contact);
            }
            
            FillPhones();
        }
        
        ShouldEnableButtonFromListPhone();
    }//GEN-LAST:event_ButtonExcludePhoneActionPerformed
    private void ButtonSavePhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSavePhoneActionPerformed
        var phoneNumber = TextPhone.getText();
        var contact = new Contact();
        var success = false;
        
        if (phoneNumber.equals("(  )      -    ")) {
            ShowMessage("O número de telefone deve ser informado.");
            return;
        }
        
        if (Strings.HasLetters(phoneNumber)) {
            ShowMessage("Somente deve conter números no telefone.");
            return;
        }
        
        if (contactOperation == DBOperationState.Insert) {
            contact.OperationState = DBOperationState.Insert;
            contact.Phone = phoneNumber;
            contact.PersonId = personId;
            
            contacts.add(contact);
            
            success = true;
        }
        
        if (contactOperation == DBOperationState.Update) {
            contact = contacts.get(selectedContact);
            contact.OperationState = DBOperationState.Update;
            contact.Phone = phoneNumber;
            contact.PersonId = personId;
            
            success = true;
        }
        
        if (success) {
            contactOperation = DBOperationState.None;
            
            FillPhones();
            ListPhone.setEnabled(true);
            ShouldEnableButtonFromListPhone();
            TextPhone.setText(Strings.Empty);
            SetEnabledFieldPhone(false);
        }
    }//GEN-LAST:event_ButtonSavePhoneActionPerformed

    private void ButtonCancelPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelPhoneActionPerformed
        contactOperation = DBOperationState.None;
        
        ListPhone.setEnabled(true);
        TextPhone.setText(Strings.Empty);
        SelectFirstElementInListPhones();
        ShouldEnableButtonFromListPhone();
        SetEnabledFieldPhone(false);
    }//GEN-LAST:event_ButtonCancelPhoneActionPerformed

    private void ListPhoneValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListPhoneValueChanged
        if (ListPhone.isEnabled()) {
            ShouldEnableButtonFromListPhone();
        }
    }//GEN-LAST:event_ListPhoneValueChanged
    
    // Register ####################
    
    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        if (CanValidatePersonControl()) {
            var person = CreatePerson();
            
            if (person == null) {
                ShowMessage("Houve um erro na validação da data de nascimento.\nVerifique a data de nascimento.");
                return;
            }
            
            var handler = dependencies.PersonHandler;
            
            if (personOperation == DBOperationState.Update) {
                // Se houve alteração no documento.
                if (!lastDocument.equals(person.Document)) {
                    // Procura se já existe.
                    if (handler.DocumentExists(person.Document)) {
                        ShowMessage("Este CPF já está cadastrado para outra pessoa.");
                        return;
                    }
                }
                
                handler.Update(person);
                handler.UpdateAddresses(person.Addresses);
                handler.UpdatePhones(person.Contacts);
                
                ShowMessage("A pessoa foi atualizada!", "Sucesso");
                
                // Notifica todos os controles que usam pessoa.
                var notifier = dependencies.Notifier;
                notifier.Notify(Changes.Person);
            }
            
            if (personOperation == DBOperationState.Insert) {
                if (handler.DocumentExists(person.Document)) {
                    ShowMessage("Este CPF já está cadastrado para outra pessoa.");
                    return;
                }
                
                handler.Put(person);
                handler.UpdateAddresses(person.Addresses);
                handler.UpdatePhones(person.Contacts);
                
                // Limpa os dados para um novo usuário.
                Clear();
                
                ShowMessage("A pessoa foi cadastrada!", "Sucesso");
                
                // Notifica todos os controles que usam pessoa.
                var notifier = dependencies.Notifier;
                notifier.Notify(Changes.Person);
            }
            
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
            java.util.logging.Logger.getLogger(WindowPersonDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowPersonDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowPersonDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowPersonDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowPersonDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelPhone;
    private javax.swing.JButton ButtonEditAddress;
    private javax.swing.JButton ButtonEditPhone;
    private javax.swing.JButton ButtonExcludeAddress;
    private javax.swing.JButton ButtonExcludePhone;
    private javax.swing.JButton ButtonNewAddress;
    private javax.swing.JButton ButtonNewPhone;
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JButton ButtonSavePhone;
    private javax.swing.JComboBox<String> ComboCity;
    private javax.swing.JLabel LabelAddressCount;
    private javax.swing.JList<String> ListPhone;
    private javax.swing.JScrollBar ScrollAddress;
    private javax.swing.JTextField TextAddress;
    private javax.swing.JFormattedTextField TextBirthday;
    private javax.swing.JFormattedTextField TextDocument;
    private javax.swing.JTextField TextEmail;
    private javax.swing.JTextField TextName;
    private javax.swing.JFormattedTextField TextPhone;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}