package br.com.authswing.main;

import br.com.authswing.dao.DAOFactory;
import br.com.authswing.view.FrmLogin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author Douglas Gusson
 */
public class Main extends JWindow {

    private final int duration;

    public Main(int d) {
        duration = d;
    }

    public void showSplash() {
        desenharSplash();
        try {
            Thread.sleep(duration);
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    Connection connection = DAOFactory.getDefaultDAOFactory().getConnection();
                    if (connection.isValid(0)) {
                        new FrmLogin().setVisible(true);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            setVisible(false);
        } catch (Exception e) {
        }
    }

    private void desenharSplash() {

        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        int comprimento = 400;
        int altura = 220;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - comprimento) / 2;
        int y = (screen.height - altura) / 2;
        setBounds(x, y, comprimento, altura);

        ImageIcon imageIcon = new ImageIcon(
                Main.this.getClass().getResource("/br/com/authswing/img/load.gif"));
        JLabel lbGif = new JLabel(imageIcon);

        JLabel lbTitulo = new JLabel("Auth Swing", JLabel.CENTER);
        lbTitulo.setFont(new Font("Sans-Serif", Font.BOLD, 14));

        JLabel load = new JLabel("Carregando módulos do sistema...", JLabel.CENTER);
        load.setFont(new Font("Sans-Serif", Font.BOLD, 10));

        content.add(lbTitulo, BorderLayout.NORTH);
        content.add(lbGif, BorderLayout.CENTER);
        content.add(load, BorderLayout.SOUTH);

        Color borda = new Color(201, 201, 201);
        content.setBorder(BorderFactory.createLineBorder(borda, 12));

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
        Main m = new Main(1500);
        m.showSplash();
    }

}
