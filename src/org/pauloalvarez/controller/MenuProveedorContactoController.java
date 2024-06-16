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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.pauloalvarez.database.Conexion;
import org.pauloalvarez.model.EmailProveedor;
import org.pauloalvarez.model.Proveedor;
import org.pauloalvarez.model.TelProveedor;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuProveedorContactoController implements Initializable {

    private Main escenarioPrincipal;

    private Alert error = new Alert(AlertType.ERROR);

    private enum operaciones {
        AGREGAR, ACTUALIZAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor> listaEmails = getEmailProveedores();
    private ObservableList<TelProveedor> listaTelefonos = getTelefonoProveedores();
    private ObservableList<Proveedor> listaProveedores;

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
    private ComboBox cbxCodigoProveedorEmail;
    @FXML
    private ComboBox cbxCodigoProveedorTelefono;
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
    private TableColumn colCodigoProveedorEmail;
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
        cbxCodigoProveedorTelefono.setItems(getProveedores());
        cbxCodigoProveedorEmail.setItems(getProveedores());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void cargarDatos() {
        // Tabla Teléfonos
        tblTelefonoProveedor.setItems(getTelefonoProveedores());
        colCodigoTelefonoP.setCellValueFactory(new PropertyValueFactory<TelProveedor, Integer>("codigoTelefonoProveedor"));
        colNumeroPrincipalP.setCellValueFactory(new PropertyValueFactory<TelProveedor, String>("numeroPrincipal"));
        colNumeroSecundarioP.setCellValueFactory(new PropertyValueFactory<TelProveedor, String>("numeroSecundario"));
        colObservacionesTelefonoP.setCellValueFactory(new PropertyValueFactory<TelProveedor, String>("observaciones"));
        colCodigoProveedorTelefono.setCellValueFactory(new PropertyValueFactory<TelProveedor, Integer>("codigoProveedor"));

        // Tabla Emails
        tblEmailProveedor.setItems(getEmailProveedores());
        colCodigoEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescripcionEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodigoProveedorEmail.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
            // Llenar TextFields de Teléfonos
            txtCodigoTelefonoP.setText(String.valueOf(((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelProveedor()));
            txtNumeroPrincipalP.setText(((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
            txtNumeroSecundarioP.setText(((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroSecundario());
            txtObservacionesTelefonoP.setText(((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getObservaciones());
            cbxCodigoProveedorTelefono.getSelectionModel().select(buscarCodigoProveedorT(((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        } else if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
            // Llenar TextFields de E-Mails
            txtCodigoEmailP.setText(String.valueOf(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
            txtEmailP.setText(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
            txtDescripcionEmailP.setText(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion());
            cbxCodigoProveedorEmail.getSelectionModel().select(buscarCodigoProveedorE(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        }
    }

    public TelProveedor buscarCodigoProveedorT(int codigoProveedor) {
        TelProveedor resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarCodigoProveedorT(?)}");
            procedimiento.setInt(1, codigoProveedor);

            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new TelProveedor(registro.getInt("ID"),
                    registro.getString("No. Principal"),
                    registro.getString("No. Secundario"),
                    registro.getString("Observaciones"),
                    registro.getInt("Proveedor"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Base de Datos");
            error.setContentText("La base de datos retornó este error: " + ex.getMessage());
            error.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Aplicación");
            error.setContentText("La aplicación retornó este error: " + ex.getMessage());
            error.showAndWait();
        }

        return resultado;
    }

    public EmailProveedor buscarCodigoProveedorE(int codigoProveedor) {
        EmailProveedor resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarCodigoProveedorE(?)");
            procedimiento.setInt(1, codigoProveedor);

            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new EmailProveedor(registro.getInt("ID"),
                    registro.getString("E-Mail"),
                    registro.getString("Descripción"),
                    registro.getInt("Proveedor")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Base de Datos");
            error.setContentText("La base de datos retornó este error: " + ex.getMessage());
            error.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Aplicación");
            error.setContentText("La aplicación retornó este error: " + ex.getMessage());
            error.showAndWait();
        }

        return resultado;
    }

    public ObservableList<TelProveedor> getTelefonoProveedores() {
        ArrayList<TelProveedor> arrListaTelefonos = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteTelProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                listaTelefonos.add(new TelProveedor(resultado.getInt("ID"),
                    resultado.getString("No. Principal"),
                    resultado.getString("No. Secundario"),
                    resultado.getString("Observaciones"),
                    resultado.getInt("Proveedor")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Base de Datos");
            error.setContentText("La base de datos retornó este error: " + ex.getMessage());
            error.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Aplicación");
            error.setContentText("La aplicación retornó este error: " + ex.getMessage());
            error.showAndWait();
        }

        listaTelefonos = FXCollections.observableArrayList(arrListaTelefonos);

        return listaTelefonos;
    }

    public ObservableList<EmailProveedor> getEmailProveedores() {
        ArrayList<EmailProveedor> arrListaEmails = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                listaEmails.add(new EmailProveedor(resultado.getInt("ID"),
                    resultado.getString("E-Mail"),
                    resultado.getString("Descripción"),
                    resultado.getInt("Proveedor")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Base de Datos");
            error.setContentText("La base de datos retornó este error: " + ex.getMessage());
            error.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Aplicación");
            error.setContentText("La aplicación retornó este error: " + ex.getMessage());
            error.showAndWait();
        }

        listaEmails = FXCollections.observableArrayList(arrListaEmails);

        return listaEmails;
    }
    
    public ObservableList<Proveedor> getProveedores() {
        ArrayList<Proveedor> arrListaProveedores = new ArrayList<>();
        
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteProveedor()");
            ResultSet resultado = procedimiento.executeQuery();
            
            while (resultado.next()) {
                arrListaProveedores.add(new Proveedor(resultado.getInt("ID"),
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

            error.setTitle(null);
            error.setHeaderText("Error con la Base de Datos");
            error.setContentText("La base de datos retornó este error: " + ex.getMessage());
            error.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

            error.setTitle(null);
            error.setHeaderText("Error con la Aplicación");
            error.setContentText("La aplicación retornó este error: " + ex.getMessage());
            error.showAndWait();
        }
        
        return listaProveedores = FXCollections.observableList(arrListaProveedores);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarCP.setText("Guardar");
                imgAgregarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecGuardar.png"));
                btnEliminarCP.setText("Cancelar");
                imgEliminarCP.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                btnEditarCP.setDisable(true);
                btnReporteCP.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarTelefono();
                guardarEmail();
                desactivarControles();
                limpiarControles();
                btnAgregarCP.setText("Agregar");
                imgAgregarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarCP.setText("Eliminar");
                imgEliminarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarCP.setDisable(false);
                btnReporteCP.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCP.setText("Agregar");
                btnAgregarCP.setDisable(false);
                imgAgregarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarCP.setText("Eliminar");
                imgEliminarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarCP.setText("Editar");
                btnEditarCP.setDisable(false);
                imgEditarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnReporteCP.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null || tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {

                    Alert confirmacion = new Alert(AlertType.CONFIRMATION);
                    confirmacion.setTitle(null);
                    confirmacion.setHeaderText("Advertencia");
                    confirmacion.setContentText("¿Quiere Eliminar el Registro?");

                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarTelProveedor(?)");
                                procedimiento.setInt(1, ((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelProveedor());
                                procedimiento.execute();
                                listaTelefonos.remove((TelProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem());
                            } else if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarEmailProveedor(?)");
                                procedimiento.setInt(1, ((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                                procedimiento.execute();
                                listaEmails.remove((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem());
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
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null || tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCP.setText("Actualizar");
                    imgEditarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecActualizar.png"));
                    btnEliminarCP.setText("Cancelar");
                    imgEliminarCP.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                    btnAgregarCP.setDisable(true);
                    btnReporteCP.setDisable(true);
                    activarControles();
                    txtCodigoTelefonoP.setDisable(true);
                    txtCodigoEmailP.setDisable(true);
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
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null && tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    actualizarTelefono();
                    actualizarEmail();
                } else if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    actualizarTelefono();
                } else if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    actualizarEmail();
                }

                desactivarControles();
                limpiarControles();
                btnEditarCP.setText("Editar");
                imgEditarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnEliminarCP.setText("Eliminar");
                imgEliminarCP.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnAgregarCP.setDisable(false);
                btnReporteCP.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {

    }

    public void guardarTelefono() {
        try {
            TelProveedor registro = new TelProveedor();
            registro.setCodigoTelProveedor(Integer.parseInt(txtCodigoTelefonoP.getText()));
            registro.setNumeroPrincipal(txtNumeroPrincipalP.getText());
            registro.setNumeroSecundario(txtNumeroSecundarioP.getText());
            registro.setObservaciones(txtObservacionesTelefonoP.getText());
            registro.setCodigoProveedor(((TelProveedor) cbxCodigoProveedorTelefono.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarTelProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTelProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonos.add(registro);

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
    }

    public void guardarEmail() {
        try {
            EmailProveedor registro = new EmailProveedor();
            registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailP.getText()));
            registro.setEmailProveedor(txtEmailP.getText());
            registro.setDescripcion(txtDescripcionEmailP.getText());
            registro.setCodigoProveedor(((EmailProveedor) cbxCodigoProveedorEmail.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listaEmails.add(registro);

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
    }

    public void actualizarTelefono() {
        try {
            TelProveedor registro = new TelProveedor();
            registro.setCodigoTelProveedor(Integer.parseInt(txtCodigoTelefonoP.getText()));
            registro.setNumeroPrincipal(txtNumeroPrincipalP.getText());
            registro.setNumeroSecundario(txtNumeroSecundarioP.getText());
            registro.setObservaciones(txtObservacionesTelefonoP.getText());
            registro.setCodigoProveedor(((TelProveedor) cbxCodigoProveedorTelefono.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarTelProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTelProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonos.add(registro);

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
    }

    public void actualizarEmail() {
        try {
            EmailProveedor registro = new EmailProveedor();
            registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailP.getText()));
            registro.setEmailProveedor(txtEmailP.getText());
            registro.setDescripcion(txtDescripcionEmailP.getText());
            registro.setCodigoProveedor(((EmailProveedor) cbxCodigoProveedorEmail.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listaEmails.add(registro);

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
    }

    public void activarControles() {
        txtCodigoTelefonoP.setDisable(false);
        txtNumeroPrincipalP.setDisable(false);
        txtNumeroSecundarioP.setDisable(false);
        txtObservacionesTelefonoP.setDisable(false);
        cbxCodigoProveedorTelefono.setDisable(false);

        txtCodigoEmailP.setDisable(false);
        txtEmailP.setDisable(false);
        txtDescripcionEmailP.setDisable(false);
        cbxCodigoProveedorEmail.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoTelefonoP.setDisable(true);
        txtNumeroPrincipalP.setDisable(true);
        txtNumeroSecundarioP.setDisable(true);
        txtObservacionesTelefonoP.setDisable(true);
        cbxCodigoProveedorTelefono.setDisable(true);

        txtCodigoEmailP.setDisable(true);
        txtEmailP.setDisable(true);
        txtDescripcionEmailP.setDisable(true);
        cbxCodigoProveedorEmail.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoTelefonoP.clear();
        txtNumeroPrincipalP.clear();
        txtNumeroSecundarioP.clear();
        txtObservacionesTelefonoP.clear();
        cbxCodigoProveedorTelefono.getSelectionModel().getSelectedItem();

        txtCodigoEmailP.clear();
        txtEmailP.clear();
        txtDescripcionEmailP.clear();
        cbxCodigoProveedorEmail.getSelectionModel().getSelectedItem();
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
