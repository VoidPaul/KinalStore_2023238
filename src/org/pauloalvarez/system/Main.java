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
        this.escenarioPrincipal.setTitle("Kinal Store");
        this.escenarioPrincipal.getIcons().add(new Image("/org/pauloalvarez/assets/images/KinalStoreLogoRedondo.png"));
        menuPrincipalView();
        escenarioPrincipal.show();
    }
    
     public Initializable cambiarEscena(String fxmlName) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        escena = new Scene(loader.load(file));
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("PrincipalView.fxml");
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuProveedorView() {
        try {
            MenuProveedoresController menuProveedor = (MenuProveedoresController) cambiarEscena("ProveedorView.fxml");
            menuProveedor.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuProveedorContacto() {
        try {
            MenuProveedorContactoController menuProveedorContacto = (MenuProveedorContactoController) cambiarEscena("ProveedorContactoView.fxml");
            menuProveedorContacto.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuEmpleadoView() {
        try {
            MenuEmpleadosController menuEmpleado = (MenuEmpleadosController) cambiarEscena("EmpleadoView.fxml");
            menuEmpleado.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuTipoEmpleado() {
        try {
            MenuTipoEmpleadoController menuTipoEmpleado = (MenuTipoEmpleadoController) cambiarEscena("TipoEmpleadoView.fxml");
            menuTipoEmpleado.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuCliente = (MenuClientesController) cambiarEscena("ClienteView.fxml");
            menuCliente.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuComprasView() {
        try {
            MenuComprasController menuCompras = (MenuComprasController) cambiarEscena("ComprasView.fxml");
            menuCompras.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuFacturaView() {
        try {
            MenuFacturasController menuFactura = (MenuFacturasController) cambiarEscena("FacturaView.fxml");
            menuFactura.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuInventarioView() {
        try {
            MenuInventarioController menuInventario = (MenuInventarioController) cambiarEscena("InventarioView.fxml");
            menuInventario.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProducto = (MenuTipoProductoController) cambiarEscena("TipoEmpleadoView.fxml");
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
            MenuProgramadorController menuProgramador = (MenuProgramadorController) cambiarEscena("ProgramadorView.fxml");
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
