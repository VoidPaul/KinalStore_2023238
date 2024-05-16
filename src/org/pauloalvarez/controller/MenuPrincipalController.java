package org.pauloalvarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuPrincipalController implements Initializable {
    private Main escenarioPrincipal;
    
    @FXML
    private MenuItem btnProveedores;
    @FXML 
    private MenuItem btnEmpleados;
    @FXML 
    private MenuItem btnClientes;
    @FXML 
    private MenuItem btnCompras;
    @FXML
    private MenuItem btnInventario;
    @FXML
    private MenuItem btnSalario;
    @FXML
    private MenuItem btnQuejas;
    @FXML 
    private MenuItem btnProgramador;
    @FXML 
    private MenuItem btnAyuda;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void menuClientesView() {
        escenarioPrincipal.menuClientesView();
    }
    
    @FXML
    public void menuBarHandler(ActionEvent event) {
        if (event.getSource() == btnProveedores) {
            escenarioPrincipal.menuProveedorView();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.menuEmpleadoView();
        } else if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.menuComprasView();
        } else if (event.getSource() == btnInventario) {
            escenarioPrincipal.menuInventarioView();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.menuProgramadorView();
        }
    }
    
}
