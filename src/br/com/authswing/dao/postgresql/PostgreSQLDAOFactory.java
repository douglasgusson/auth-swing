package br.com.authswing.dao.postgresql;


import br.com.authswing.dao.DAOException;
import br.com.authswing.dao.DAOFactory;
import br.com.authswing.dao.model.UsuarioDAO;
import br.com.authswing.domain.Database;
import br.com.authswing.view.FrmConfiguraBanco;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author douglas
 */
public class PostgreSQLDAOFactory extends DAOFactory {

    private static final Database DATABASE = getDatabase();
    private static final String URL_BANCO = "jdbc:postgresql://" + DATABASE.toString();
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USUARIO = DATABASE.getUsuario();
    private static final String SENHA = DATABASE.getSenha();

    private static Connection connection;

    @Override
    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
            return connection;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Conector para PostgreSQL não foi encontrado.",
                    "Erro de conexão",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            throw new DAOException("Não foi possível estabelecer conexão com o banco de dados.", ex);
        }
        return null;
    }

    /**
     * @return O objeto Database.
     */
    public static Database getDatabase() {
        try {
            StringBuilder xml = new StringBuilder();
            Scanner scanner = new Scanner(new FileReader("config-banco.xml"));

            while (scanner.hasNext()) {
                xml.append(scanner.next());
            }

            XStream xStream = new XStream(new DomDriver());
            Database banco = (Database) xStream.fromXML(xml.toString());

            return banco;

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível estabelecer conexão com o banco.\n\n"
                    + "ERRO: Arquivo config-banco.xml não encontrado.",
                    "Erro de conexão",
                    JOptionPane.ERROR_MESSAGE);
            FrmConfiguraBanco configuraBanco = new FrmConfiguraBanco(null, true);
            configuraBanco.setVisible(true);
        }
        return null;
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new PgUsuarioDAO();
    }

}
