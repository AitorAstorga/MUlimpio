package popbl4.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import popbl4.modelo.ListaMesas;
import popbl4.modelo.Reserva;
import popbl4.principal.Principal;
import popbl4.reservas.Gestor;
import popbl4.reservas.PanelMesa;

public class Login extends JFrame {
	private JButton btnLogin;
    private JButton btnRegister;
    private JLabel jLabelLogin;
    private JLabel jLabelUsuario;
    private JLabel jLabelContraseña;
    private JPanel jPanel1;
    private JPasswordField txtPwd;
    private JTextField txtUsername;
    
    private ActionListener listenerBotonLogin;
    private ActionListener listenerBotonRegistrar;
    
    public void cerrarLogin() {
    	this.dispose();
    }
	
    public Login() {
        initComponents();
        setTitle("Login Form");
        
        listenerBotonLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Principal.getDatabase().loginDataBase(txtUsername.getText().toString(), txtPwd.getText().toString());
            	
                if (Principal.getDatabase().isConexionEstablecida()) {
                    JOptionPane.showMessageDialog(Login.this, "Estas dentro hacker");
                    cerrarLogin();
                   // MarcarLimpieza window = new MarcarLimpieza();
					//window.setVisible(true);
                    
            		PanelMesa pMesa = new PanelMesa();
            		Reservas pantalla = new Reservas(pMesa, Principal.getModelo(), Principal.getGestor());
            		
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Nahhh, buen intento HACKER");
                }
            }
        };
        listenerBotonRegistrar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		Principal.getDatabase().crearUsuario(txtUsername.getText().toString(), txtPwd.getText().toString());
				} catch (SQLException e1) { e1.printStackTrace(); }
            }
        };
        
        btnLogin.addActionListener(listenerBotonLogin);
        btnRegister.addActionListener(listenerBotonRegistrar);
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jLabelLogin = new JLabel();
        jLabelUsuario = new JLabel();
        jLabelContraseña = new JLabel();
        btnLogin = new JButton();
        btnRegister = new JButton();
        txtUsername = new JTextField();
        txtPwd = new JPasswordField();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jLabelLogin.setText("Login");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jLabelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(180, 180, 180))
        );
        
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );

        jLabelUsuario.setText("Username");

        jLabelContraseña.setText("Password");

        btnLogin.setText("login");
        
        btnRegister.setText("register");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUsuario)
                            .addComponent(jLabelContraseña))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername)
                            .addComponent(txtPwd)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
                        addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsuario)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelContraseña)
                    .addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnLogin)
                .addContainerGap(66, Short.MAX_VALUE).addComponent(btnRegister).addContainerGap(66, Short.MAX_VALUE))
        );

        pack();

    }
}
