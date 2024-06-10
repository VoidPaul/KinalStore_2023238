package org.pauloalvarez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.pauloalvarez.controller.MenuClientesController;
import org.pauloalvarez.controller.MenuComprasController;
import org.pauloalvarez.controller.MenuEmpleadosController;
import org.pauloalvarez.controller.MenuFacturasController;
import org.pauloalvarez.controller.MenuInventarioController;
import org.pauloalvarez.controller.MenuPrincipalController;
import org.pauloalvarez.controller.MenuProgramadorController;
import org.pauloalvarez.controller.MenuProveedorContactoController;
import org.pauloalvarez.controller.MenuProveedoresController;
import org.pauloalvarez.controller.MenuTipoEmpleadoController;
import org.pauloalvarez.controller.MenuTipoProductoController;

/**
 *
 * @author Paulo Alvarez
 */
public class Main extends Application {

    private Scene escena;
    private Stage escenarioPrincipal;
    private final String URLVIEW = "/org/pauloalvarez/view/";
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/org/pauloalvarez/view/PrincipalView.fxml"));

        //escena = new Scene(root);
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.getIcons().add(new Image("/org/pauloalvarez/assets/images/KinalStoreLogoRedondo.png"));
        menuPrincipalView();
        escenarioPrincipal.show();
    }
    
     public Initializable cambiarEscena(String fxmlName, String nombreEscena) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        escena = new Scene(loader.load(file));
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.setTitle(nombreEscena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("PrincipalView.fxml", "Kinal Store");
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuProveedorView() {
        try {
            MenuProveedoresController menuProveedor = (MenuProveedoresController) cambiarEscena("ProveedorView.fxml", "Proveedores - Kinal Store");
            menuProveedor.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuProveedorContacto() {
        try {
            MenuProveedorContactoController menuProveedorContacto = (MenuProveedorContactoController) cambiarEscena("ProveedorContactoView.fxml", "Contacto(s) - Kinal Store");
            menuProveedorContacto.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuEmpleadoView() {
        try {
            MenuEmpleadosController menuEmpleado = (MenuEmpleadosController) cambiarEscena("EmpleadoView.fxml", "Empleados - Kinal Store");
            menuEmpleado.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuTipoEmpleado() {
        try {
            MenuTipoEmpleadoController menuTipoEmpleado = (MenuTipoEmpleadoController) cambiarEscena("TipoEmpleadoView.fxml", "Puestos - Kinal Store");
            menuTipoEmpleado.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuCliente = (MenuClientesController) cambiarEscena("ClienteView.fxml", "Clientes - Kinal Store");
            menuCliente.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuComprasView() {
        try {
            MenuComprasController menuCompras = (MenuComprasController) cambiarEscena("ComprasView.fxml", "Compras - Kinal Store");
            menuCompras.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuFacturaView() {
        try {
            MenuFacturasController menuFactura = (MenuFacturasController) cambiarEscena("FacturaView.fxml", "Facturas - Kinal Store");
            menuFactura.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuInventarioView() {
        try {
            MenuInventarioController menuInventario = (MenuInventarioController) cambiarEscena("InventarioView.fxml", "Inventario - Kinal Store");
            menuInventario.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProducto = (MenuTipoProductoController) cambiarEscena("TipoProductoView.fxml", "Categorías - Kinal Store");
            menuTipoProducto.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuAyudaView() {
        try {
            
        } catch (Exception ex) {
            
        }
    }
    
    public void menuProgramadorView() {
        try {
            MenuProgramadorController menuProgramador = (MenuProgramadorController) cambiarEscena("ProgramadorView.fxml", "Kinal Store");
            menuProgramador.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
