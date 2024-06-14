package org.pauloalvarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.pauloalvarez.database.Conexion;
import org.pauloalvarez.model.Cliente;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;
    
    private Alert error = new Alert(AlertType.ERROR);

    private enum operaciones {
        AGREGAR, ACTUALIZAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cliente> listaClientes = getClientes();

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAgregarC;
    @FXML
    private Button btnEditarC;
    @FXML
    private Button btnEliminarC;
    @FXML
    private Button btnReporteC;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNITC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtEmailC;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNITC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private TableColumn colEmailC;
    @FXML
    private ImageView imgAgregarC;
    @FXML
    private ImageView imgEditarC;
    @FXML
    private ImageView imgEliminarC;

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
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
        colNITC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("NITCliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombresCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidosCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefonoCliente"));
        colEmailC.setCellValueFactory(new PropertyValueFactory<Cliente, String>("emailCliente"));
    }

    public void seleccionarElemento() {
        txtCodigoC.setText(String.valueOf(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNITC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getNITCliente());
        txtNombreC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidoC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtDireccionC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtEmailC.setText(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getEmailCliente());
    }

    public ObservableList<Cliente> getClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteCliente()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Cliente(resultado.getInt("ID"),
                        resultado.getString("NIT"),
                        resultado.getString("Nombres"),
                        resultado.getString("Apellidos"),
                        resultado.getString("Dirección"),
                        resultado.getString("Teléfono"),
                        resultado.getString("E-mail")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            
            error.setTitle(null);
            error.setHeaderText("Error con la Base de Datos");
            error.setContentText("La base de datos retornó este error: " + String.valueOf(ex.getMessage()));
            error.showAndWait();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            error.setTitle(null);
            error.setHeaderText("Error con la Aplicación");
            error.setContentText("La aplicación retornó este error: " + String.valueOf(ex.getMessage()));
            error.showAndWait();
            
        }

        listaClientes = FXCollections.observableArrayList(lista);

        return listaClientes;
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarC.setText("Guardar");
                imgAgregarC.setImage(new Image("/org/pauloalvarez/assets/images/selecGuardar.png"));
                btnEliminarC.setText("Cancelar");
                imgEliminarC.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                btnEditarC.setDisable(true);
                btnReporteC.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarC.setText("Agregar");
                imgAgregarC.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarC.setText("Eliminar");
                imgEliminarC.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarC.setDisable(false);
                btnReporteC.setDisable(false);
                cargarDatos();
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarC.setText("Agregar");
                btnAgregarC.setDisable(false);
                imgAgregarC.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarC.setText("Eliminar");
                imgEliminarC.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarC.setText("Editar");
                btnEditarC.setDisable(false);
                imgEditarC.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnReporteC.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {

                    Alert confirmacion = new Alert(AlertType.CONFIRMATION);
                    confirmacion.setTitle(null);
                    confirmacion.setHeaderText("Advertencia");
                    confirmacion.setContentText("¿Quiere Eliminar el Registro?");

                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                            procedimiento.setInt(1, ((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove((Cliente) tblClientes.getSelectionModel().getSelectedItem());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                        Alert informacion = new Alert(AlertType.INFORMATION);
                        informacion.setTitle(null);
                        informacion.setHeaderText("Eliminación Fallida");
                        informacion.setContentText("Debe Seleccionar lo que quiere Editar.");
                        informacion.showAndWait();
                }
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditarC.setText("Actualizar");
                    imgEditarC.setImage(new Image("/org/pauloalvarez/assets/images/selecActualizar.png"));
                    btnEliminarC.setText("Cancelar");
                    imgEliminarC.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                    btnAgregarC.setDisable(true);
                    btnReporteC.setDisable(true);
                    txtCodigoC.setEditable(false);
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    Alert informacion = new Alert(AlertType.INFORMATION);
                    
                    informacion.setTitle(null);
                    informacion.setHeaderText("Edición Fallida");
                    informacion.setContentText("Debe Seleccionar lo que quiere Editar.");
                    informacion.showAndWait();
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarC.setText("Editar");
                imgEditarC.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnEliminarC.setText("Eliminar");
                imgEliminarC.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnAgregarC.setDisable(false);
                btnReporteC.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {
        // Agregaré una función distinta a este botón.
    }

    public void guardar() {
        try {

            Cliente registro = new Cliente();
            registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
            registro.setNITCliente(txtNITC.getText());
            registro.setNombresCliente(txtNombreC.getText());
            registro.setApellidosCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setEmailCliente(txtEmailC.getText());

            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getEmailCliente());
            procedimiento.execute();
            listaClientes.add(registro);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar() {
        try {

            Cliente registro = (Cliente) tblClientes.getSelectionModel().getSelectedItem();
            registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
            registro.setNITCliente(txtNITC.getText());
            registro.setNombresCliente(txtNombreC.getText());
            registro.setApellidosCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setEmailCliente(txtEmailC.getText());

            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getEmailCliente());
            procedimiento.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void activarControles() {
        txtCodigoC.setDisable(false);
        txtNITC.setDisable(false);
        txtNombreC.setDisable(false);
        txtApellidoC.setDisable(false);
        txtDireccionC.setDisable(false);
        txtTelefonoC.setDisable(false);
        txtEmailC.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoC.setDisable(true);
        txtNITC.setDisable(true);
        txtNombreC.setDisable(true);
        txtApellidoC.setDisable(true);
        txtDireccionC.setDisable(true);
        txtTelefonoC.setDisable(true);
        txtEmailC.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtNITC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtTelefonoC.clear();
        txtEmailC.clear();
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
