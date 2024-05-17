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
import javax.swing.JOptionPane;
import org.pauloalvarez.database.Conexion;
import org.pauloalvarez.model.Cliente;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {AGREGAR, EDITAR, ACTUALIZAR, CANCELAR, ELIMINAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cliente> listaClientes = getClientes();

    @FXML
    private Button btnRegresar;
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
    private Button btnAgregarC;
    @FXML
    private Button btnEditarC;
    @FXML
    private Button btnEliminarC;
    @FXML
    private Button btnReporteC;
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
        txtCodigoC.setText(String.valueOf(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getClienteID()));
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
        } catch (Exception ex) {
            ex.printStackTrace();
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
                break;
        }
    }

    public void guardar() {
        Cliente registro = new Cliente();
        registro.setClienteID(Integer.parseInt(txtCodigoC.getText()));
        registro.setNITCliente(txtNITC.getText());
        registro.setNombresCliente(txtNombreC.getText());
        registro.setApellidosCliente(txtApellidoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setEmailCliente(txtEmailC.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getClienteID());
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

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
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
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Quiere Eliminar el Registro?",
                            "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                            procedimiento.setInt(1, ((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                            procedimiento.execute();
                            listaClientes.remove((Cliente) tblClientes.getSelectionModel().getSelectedItem());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe de Seleccionar lo que quiere Eliminar");
                    }
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
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar lo que quiere Editar.");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarC.setText("Editar");
                imgEditarC.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnEliminarC.setText("Reporte");
                imgEliminarC.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnAgregarC.setDisable(false);
                btnReporteC.setDisable(false);
                limpiarControles();
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarCliente(?, ?, ?, ?, ?, ?, ?)}");
            Cliente registro = (Cliente) tblClientes.getSelectionModel().getSelectedItem();
            registro.setClienteID(Integer.parseInt( txtCodigoC.getText()));
            registro.setNITCliente(txtNITC.getText());
            registro.setNombresCliente(txtNombreC.getText());
            registro.setApellidosCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setEmailCliente(txtEmailC.getText());
            procedimiento.setInt(1, registro.getClienteID());
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
    
    public void reporte() {
        cargarDatos();
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

    public void activarControles() {
        txtCodigoC.setDisable(false);
        txtNITC.setDisable(false);
        txtNombreC.setDisable(false);
        txtApellidoC.setDisable(false);
        txtDireccionC.setDisable(false);
        txtTelefonoC.setDisable(false);
        txtEmailC.setDisable(false);
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

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
