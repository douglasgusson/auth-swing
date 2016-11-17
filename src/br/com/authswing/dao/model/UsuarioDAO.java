
package br.com.authswing.dao.model;

import br.com.authswing.domain.Usuario;
import java.util.List;

/**
 *
 * @author douglas
 */
public interface UsuarioDAO {

    public List<Usuario> listarTodos();
    public Usuario encontrar(Usuario usuario);
    public void inserir(Usuario usuario);
    public void alterar(Usuario usuario);
    
}
