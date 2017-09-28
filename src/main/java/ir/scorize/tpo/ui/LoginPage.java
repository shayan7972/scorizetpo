package ir.scorize.tpo.ui;

import ir.scorize.tpo.DataManager;
import ir.scorize.tpo.net.NetworkManager;
import ir.scorize.tpo.net.serializers.LoginCredentials;
import ir.scorize.tpo.observers.LoginObserver;
import ir.scorize.tpo.observers.TokenObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by mjafar on 8/4/17.
 */
public class LoginPage {
    private JButton btnLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextArea lblHelp;
    private JPanel loginPanel;
    private JButton btnRegister;

    private NetworkManager mNetworkManager;
    private DataManager mDataManager;

    public LoginPage(NetworkManager networkManager, DataManager dataManager) {
        super();
        btnLogin.addActionListener(mLoginListener);
        btnRegister.addActionListener(mRegisterListener);
        lblHelp.setBorder(UIManager.getBorder("Label.border"));

        mNetworkManager = networkManager;
        mDataManager = dataManager;

        addLoginObserver(new LoginObserver() {
            @Override
            public void loginStateChanged(boolean success) {
                if (!success) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            setFieldsEnabled(true);
                            // TODO: show a message
                        }
                    });
                }
            }
        });
    }

    public JFrame getForm(String title) {
        JFrame frame = new JFrame(title);
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        return frame;
    }

    public void setData(LoginCredentials data) {
        txtUsername.setText(data.getUsername());
        txtPassword.setText(data.getPassword());
    }

    public void getData(LoginCredentials data) {
        data.setUsername(txtUsername.getText());
        data.setPassword(String.valueOf(txtPassword.getPassword()));
    }

    public boolean isModified(LoginCredentials data) {
        if (txtUsername.getText() != null ? !txtUsername.getText().equals(data.getUsername()) : data.getUsername() != null)
            return true;
        if (txtPassword.getPassword() != null ? !String.valueOf(txtPassword.getPassword()).equals(data.getPassword()) : data.getPassword() != null)
            return true;
        return false;
    }

    private void setFieldsEnabled(boolean enabled) {
        txtPassword.setEnabled(enabled);
        txtUsername.setEnabled(enabled);
        btnLogin.setEnabled(enabled);
        btnRegister.setEnabled(enabled);
    }

    private ActionListener mLoginListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            setFieldsEnabled(false);
            LoginCredentials data = new LoginCredentials();
            getData(data);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String token = mNetworkManager.login(data);
//                    if (token != null) {
//                        notifyTokenObservers(token);
//                        notifyLoginObservers(true);
//                    } else {
//                        notifyLoginObservers(false);
//                    }
//                }
//            }).start();
            notifyTokenObservers("TOKENN");
            notifyLoginObservers(true);
        }
    };

    private ActionListener mRegisterListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                BrowserHelper.openWebpage(new URL("https://scorize.com/profile/register/"));
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
    };

    private LinkedList<TokenObserver> mTokenObserversList = new LinkedList<>();

    public void addTokenObserver(TokenObserver observer) {
        mTokenObserversList.add(observer);
    }

    public void removeTokenObserver(TokenObserver observer) {
        mTokenObserversList.remove(observer);
    }

    private void notifyTokenObservers(String token) {
        for (TokenObserver observer : mTokenObserversList) {
            observer.newToken(token);
        }
    }

    private LinkedList<LoginObserver> mLoginObserversList = new LinkedList<>();

    public void addLoginObserver(LoginObserver observer) {
        mLoginObserversList.add(observer);
    }

    public void removeLoginObserver(LoginObserver observer) {
        mLoginObserversList.remove(observer);
    }

    private void notifyLoginObservers(boolean success) {
        for (LoginObserver observer : mLoginObserversList) {
            observer.loginStateChanged(success);
        }
    }

}
