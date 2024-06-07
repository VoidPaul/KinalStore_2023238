package org.pauloalvarez.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.pauloalvarez.model.EmailProveedor;
import org.pauloalvarez.model.TelProveedor;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuProveedorContactoController implements Initializable {

    private Main escenarioPrincipal;
    
    private enum operaciones {
        AGREGAR, ACTUALIZAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor>  listaEmails = getEmailProveedores();
    private ObservableList<TelProveedor> listaTelefonos = getTelefonoProveedores();

    @FXML
    private Button btnRegresar;
    @FXML
    private Button cambiarSubMenu;
    @FXML
    private Button btnAgregarCP;
    @FXML
    private Button btnEliminarCP;
    @FXML
    private Button btnEditarCP;
    @FXML
    private Button btnReporteCP;
    @FXML
    private TextField txtCodigoTelefonoP;
    @FXML
    private TextField txtNumeroPrincipalP;
    @FXML
    private TextField txtNumeroSecundarioP;
    @FXML
    private TextField txtObservacionesTelefonoP;
    @FXML
    private TextField txtCodigoEmailP;
    @FXML
    private TextField txtEmailP;
    @FXML
    private TextField txtDescripcionEmailP;
    @FXML
    private ComboBox cbxCodigoProveedorE;
    @FXML
    private ComboBox cbxCodigoProveedorT;
    @FXML
    private TableView tblTelefonoProveedor;
    @FXML
    private TableView tblEmailProveedor;
    @FXML
    private TableColumn colCodigoTelefonoP;
    @FXML
    private TableColumn colNumeroPrincipalP;
    @FXML
    private TableColumn colNumeroSecundarioP;
    @FXML
    private TableColumn colObservacionesTelefonoP;
    @FXML
    private TableColumn colCodigoProveedorTelefono;
    @FXML
    private TableColumn colCodigoEmailP;
    @FXML
    private TableColumn colEmailP;
    @FXML
    private TableColumn colDescripcionEmailP;
    @FXML
    private TableColumn colCodigoProveedorE;
    @FXML
    private ImageView imgAgregarCP;
    @FXML
    private ImageView imgEliminarCP;
    @FXML
    private ImageView imgEditarCP;
    @FXML
    private ImageView imgReporteCP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void cargarDatos() {
        
    }
    
    public void seleccionarElemento() {
        
    }
    
    public ObservableList<TelProveedor> getTelefonoProveedores() {
        ArrayList<TelProveedor> listaTelefonos = new ArrayList<>();
        
        try {
            
        } catch (SQLException ex) {
            
        } catch (Exception ex) {
            
        }
    }
    
    public ObservableList<EmailProveedor> getEmailProveedores() {
        ArrayList<EmailProveedor> listaEmails = new ArrayList<>();
        
        try {
            
        } catch (SQLException ex) {
            
        } catch (Exception ex) {
            
        }
    }
    
    public void agregar() {
        
    }
    
    public void eliminar() {
        
    }
    
    public void editar() {
        
    }
    
    public void reporte() {
        
    }
    
    public void guardarTelefono() {
        
    }
    
    public void guardarEmail() {
        
    }
    
    public void actualizarTelefono() {
        
    }
    
    public void actualizarEmail() {
        
    }
    
    public void activarControles() {
        
    }
    
    public void desactivarControles() {
        
    }
    
    public void limpiarControles() {
        
    }

    @FXML
    public void cambiarMenu(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == cambiarSubMenu) {
            escenarioPrincipal.menuProveedorView();
        }
    }
}
