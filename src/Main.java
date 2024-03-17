import view.GUIlogin;

public class Main {
    public static void main(String[] args) {
        // Simply instantiate GUIlogin and display the login screen
        GUIlogin guiLogin = new GUIlogin();
        guiLogin.loginScreen1.setVisible(true);
        guiLogin.loginScreen1.setLocationRelativeTo(null); // Center the login screen
    }
}
