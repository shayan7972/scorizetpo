package ir.scorize.tpo;

import ir.scorize.tpo.net.NetworkManager;
import ir.scorize.tpo.observers.LoginObserver;
import ir.scorize.tpo.observers.TokenObserver;
import ir.scorize.tpo.ui.*;

import javax.swing.*;

/**
 * Created by mjafar on 8/4/17.
 */
public class Starter {

    private DataManager dataManager;
    private NetworkManager networkManager;
    private final JFrame loginPage;
    private final LoginPage loginPageController;
    private final TestEnvironment testEnvironment;

    final TokenObserver mTokenObserver = new TokenObserver() {
        @Override
        public void newToken(String token) {
            dataManager.setToken(token);
            dataManager.save();
        }
    };

    final LoginObserver mLoginObserver = new LoginObserver() {
        @Override
        public void loginStateChanged(boolean success) {
            if (success) {
                loginPage.setVisible(false);
                // TODO: open new page
                testEnvironment.setVisible(true);
//                testEnvironment.setContentPart(new ReadingDirections(), 0);
                testEnvironment.setContentPart(new SpeakingDirections(), 0);
            }
        }
    };

    private Starter() {
        dataManager = new DataManager();
        networkManager = new NetworkManager();

        String token = dataManager.getToken();

        loginPageController = new LoginPage(networkManager, dataManager);
        loginPage = loginPageController.getForm("Login to scorize");
        loginPageController.addTokenObserver(mTokenObserver);
        loginPageController.addLoginObserver(mLoginObserver);
        testEnvironment = new TestEnvironment(0);    // TODO: get exam number from somewhere
        java.awt.EventQueue.invokeLater(() -> {
            if (!dataManager.hasToken()) {
                loginPage.setVisible(true);
                loginPage.pack();
            } else {
                mLoginObserver.loginStateChanged(true);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new Starter();
    }
}
