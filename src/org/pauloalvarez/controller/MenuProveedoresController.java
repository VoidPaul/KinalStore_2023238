package org.pauloalvarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.pauloalvarez.database.Conexion;
import org.pauloalvarez.model.Proveedor;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuProveedoresController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ACTUALIZAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedor> listaProveedores = getProveedores();

    @FXML
    private Button btnRegresar;
    @FXML
    private Button cambiarSubMenu;
    @FXML
    private Button btnAgregarP;
    @FXML
    private Button btnEliminarP;
    @FXML
    private Button btnEditarP;
    @FXML
    private Button btnReporteP;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtNITP;
    @FXML
    private TextField txtNombresP;
    @FXML
    private TextField txtApellidosP;
    @FXML
    private TextField txtDireccionP;
    @FXML
    private TextField txtRazonSocialP;
    @FXML
    private TextField txtContactoPrincipalP;
    @FXML
    private TextField txtPaginaWebP;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colNITP;
    @FXML
    private TableColumn colNombresP;
    @FXML
    private TableColumn colApellidosP;
    @FXML
    private TableColumn colDireccionP;
    @FXML
    private TableColumn colRazonSocialP;
    @FXML
    private TableColumn colContactoPrincipalP;
    @FXML
    private TableColumn colPaginaWebP;
    @FXML
    private ImageView imgAgregarP;
    @FXML
    private ImageView imgEliminarP;
    @FXML
    private ImageView imgEditarP;

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
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedor, Integer>("codigoProveedor"));
        colNITP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("NITProveedor"));
        colNombresP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("nombresProveedor"));
        colApellidosP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("apellidosProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("direccion"));
        colRazonSocialP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("razonSocial"));
        colContactoPrincipalP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("contactoPrincipal"));
        colPaginaWebP.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        txtCodigoP.setText(String.valueOf(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNITP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
        txtNombresP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getNombresProveedor());
        txtApellidosP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor());
        txtDireccionP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocialP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoPrincipalP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWebP.setText(((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }

    public ObservableList<Proveedor> getProveedores() {
        ArrayList<Proveedor> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Proveedor(resultado.getInt("ID"),
                        resultado.getString("NIT"),
                        resultado.getString("Nombres"),
                        resultado.getString("Apellidos"),
                        resultado.getString("Dirección"),
                        resultado.getString("Razón Social"),
                        resultado.getString("Contacto Principal"),
                        resultado.getString("Página Web")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        listaProveedores = FXCollections.observableArrayList(lista);

        return listaProveedores;
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarP.setText("Guardar");
                imgAgregarP.setImage(new Image("/org/pauloalvarez/assets/images/selecGuardar.png"));
                btnEliminarP.setText("Cancelar");
                imgEliminarP.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                btnEditarP.setDisable(true);
                btnReporteP.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                imgAgregarP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarP.setText("Eliminar");
                imgEliminarP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarP.setDisable(false);
                btnReporteP.setDisable(false);
                cargarDatos();
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnAgregarP.setDisable(false);
                imgAgregarP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarP.setText("Eliminar");
                imgEliminarP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarP.setText("Editar");
                btnEditarP.setDisable(false);
                imgEditarP.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnReporteP.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    
                } else {
                    
                }
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("Actualizar");
                    imgEditarP.setImage(new Image("/org/pauloalvarez/assets/images/selecActualizar.png"));
                    btnEliminarP.setText("Cancelar");
                    imgEliminarP.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                    btnAgregarP.setDisable(true);
                    btnReporteP.setDisable(true);
                    txtCodigoP.setEditable(false);
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("Editar");
                imgEditarP.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnEliminarP.setText("Reporte");
                imgEliminarP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnAgregarP.setDisable(false);
                btnReporteP.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {
        //Sin función de momento
    }

    public void guardar() {
        try {
            
            Proveedor registro = new Proveedor();
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
            registro.setNITProveedor(txtNITP.getText());
            registro.setNombresProveedor(txtNombresP.getText());
            registro.setApellidosProveedor(txtApellidosP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocialP.getText());
            registro.setContactoPrincipal(txtContactoPrincipalP.getText());
            registro.setPaginaWeb(txtPaginaWebP.getText());
            
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            
            Proveedor registro = new Proveedor();
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
            registro.setNITProveedor(txtNITP.getText());
            registro.setNombresProveedor(txtNombresP.getText());
            registro.setApellidosProveedor(txtApellidosP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocialP.getText());
            registro.setContactoPrincipal(txtContactoPrincipalP.getText());
            registro.setPaginaWeb(txtPaginaWebP.getText());
            
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void activarControles() {
        txtCodigoP.setDisable(false);
        txtNITP.setDisable(false);
        txtNombresP.setDisable(false);
        txtApellidosP.setDisable(false);
        txtDireccionP.setDisable(false);
        txtRazonSocialP.setDisable(false);
        txtContactoPrincipalP.setDisable(false);
        txtPaginaWebP.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoP.setDisable(true);
        txtNITP.setDisable(true);
        txtNombresP.setDisable(true);
        txtApellidosP.setDisable(true);
        txtDireccionP.setDisable(true);
        txtRazonSocialP.setDisable(true);
        txtContactoPrincipalP.setDisable(true);
        txtPaginaWebP.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoP.clear();
        txtNITP.clear();
        txtNombresP.clear();
        txtApellidosP.clear();
        txtDireccionP.clear();
        txtRazonSocialP.clear();
        txtContactoPrincipalP.clear();
        txtPaginaWebP.clear();
    }

    @FXML
    public void cambiarMenu(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == cambiarSubMenu) {
            escenarioPrincipal.menuProveedorContacto();
        }
    }
}
