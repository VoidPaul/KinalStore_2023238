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
import org.pauloalvarez.model.CargoEmpleado;
import org.pauloalvarez.model.Empleado;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuEmpleadosController implements Initializable {
    private Main escenarioPrincipal;
    
    private Alert error = new Alert(AlertType.ERROR);
    
    private enum operaciones {
        AGREGAR, ACTUALIZAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleado> listaEmpleados = getEmpleados();
    private ObservableList<CargoEmpleado> listaCargoEmpleados = getCargosEmpleados();
    
    @FXML
    private Button btnRegresar;
    @FXML
    private Button cambiarSubMenu;
    @FXML
    private Button btnAgregarE;
    @FXML
    private Button btnEliminarE;
    @FXML
    private Button btnEditarE;
    @FXML
    private Button btnReporteE;
    @FXML
    private TextField txtCodigoE;
    @FXML
    private TextField txtNombresE;
    @FXML
    private TextField txtApellidosE;
    @FXML
    private TextField txtSueldoE;
    @FXML
    private TextField txtDireccionE;
    @FXML
    private TextField txtTurnoE;
    @FXML
    private ComboBox cbxCargoE;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colCodigoE;
    @FXML
    private TableColumn colNombresE;
    @FXML
    private TableColumn colApellidosE;
    @FXML
    private TableColumn colSueldoE;
    @FXML
    private TableColumn colDireccionE;
    @FXML
    private TableColumn colTurnoE;
    @FXML
    private TableColumn colCargoE;
    @FXML
    private ImageView imgAgregarE;
    @FXML
    private ImageView imgEliminarE;
    @FXML
    private ImageView imgEditarE;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cbxCargoE.setItems(getCargosEmpleados());
    }
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodigoE.setCellFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colNombresE.setCellFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        colApellidosE.setCellFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));
        colSueldoE.setCellFactory(new PropertyValueFactory<Empleado, String>("sueldo"));
        colDireccionE.setCellFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colTurnoE.setCellFactory(new PropertyValueFactory<Empleado, String>("turno"));
        colCargoE.setCellFactory(new PropertyValueFactory<Empleado, Integer>("cargoEmpleado"));
    }
    
    public void seleccionarElemento() {
        txtCodigoE.setText(String.valueOf(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem())));
        txtNombresE.setText(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidosE.setText(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
        txtSueldoE.setText(String.valueOf(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccionE.setText(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurnoE.setText(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        cbxCargoE.getSelectionModel().select(buscarCargoEmpleado(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getCargoEmpleado()));
    }
    
    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarCargoEmpleado(?)}");
            procedimiento.setInt(1, codigoCargoEmpleado);
            
            ResultSet registro = procedimiento.executeQuery();
            
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("ID"),
                    registro.getString("Cargo"),
                    registro.getString("Descripción")
                );
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
        
        return resultado;
    }
    
    public ObservableList<Empleado> getEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<>();
        
        try {
            PreparedStatement procedimineto = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteEmpleado()}");
            ResultSet resultado = procedimineto.executeQuery();
            
            while (resultado.next()) {
                listaEmpleados.add(new Empleado(resultado.getInt("ID"),
                    resultado.getString("Nombres"),
                    resultado.getString("Apellidos"),
                    resultado.getDouble("Sueldo"),
                    resultado.getString("direccion"),
                    resultado.getString("turno"),
                    resultado.getInt("Puesto")
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
        
        listaEmpleados = FXCollections.observableArrayList(lista);
        
        return listaEmpleados;
    }
    
    public ObservableList<CargoEmpleado> getCargosEmpleados() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while (resultado.next()) {
                listaCargoEmpleados.add(new CargoEmpleado(resultado.getInt("ID"),
                    resultado.getString("Cargo"),
                    resultado.getString("Descripción")
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
        
        listaCargoEmpleados = FXCollections.observableArrayList(lista);
        return listaCargoEmpleados;
    }
    
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarE.setText("Guardar");
                imgAgregarE.setImage(new Image("/org/pauloalvarez/assets/images/selecGuardar.png"));
                btnEliminarE.setText("Cancelar");
                imgEliminarE.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                btnEditarE.setDisable(true);
                btnReporteE.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarE.setText("Agregar");
                imgAgregarE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarE.setText("Eliminar");
                imgEliminarE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarE.setDisable(false);
                btnReporteE.setDisable(false);
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
                btnAgregarE.setText("Agregar");
                btnAgregarE.setDisable(false);
                imgAgregarE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarE.setText("Eliminar");
                imgEliminarE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarE.setText("Editar");
                btnEditarE.setDisable(false);
                imgEditarE.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnReporteE.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {

                    Alert confirmacion = new Alert(AlertType.CONFIRMATION);
                    confirmacion.setTitle(null);
                    confirmacion.setHeaderText("Advertencia");
                    confirmacion.setContentText("¿Quiere Eliminar el Registro?");

                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove((Empleado) tblEmpleados.getSelectionModel().getSelectedItem());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditarE.setText("Actualizar");
                    imgEditarE.setImage(new Image("/org/pauloalvarez/assets/images/selecActualizar.png"));
                    btnEliminarE.setText("Cancelar");
                    imgEliminarE.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                    btnAgregarE.setDisable(true);
                    btnReporteE.setDisable(true);
                    activarControles();
                    txtCodigoE.setDisable(true);
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
                btnEditarE.setText("Editar");
                imgEditarE.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnEliminarE.setText("Eliminar");
                imgEliminarE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnAgregarE.setDisable(false);
                btnReporteE.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void reporte() {
        
    }
    
    public void guardar() {
        try {
            
            Empleado registro = new Empleado();
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoE.getText()));
            registro.setNombresEmpleado(txtNombresE.getText());
            registro.setApellidosEmpleado(txtApellidosE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
            registro.setDireccion(txtDireccionE.getText());
            registro.setTurno(txtTurnoE.getText());
            registro.setCargoEmpleado(((Empleado) cbxCargoE.getSelectionModel().getSelectedItem()).getCargoEmpleado());
            
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarEmpleado(?, ?, ?, ?, ?, ?, ?}");
            procedimiento.setInt(1,registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCargoEmpleado());
            procedimiento.execute();
            listaEmpleados.add(registro);
            
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
    
    public void actualizar() {
        try {
            
            Empleado registro = new Empleado();
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoE.getText()));
            registro.setNombresEmpleado(txtNombresE.getText());
            registro.setApellidosEmpleado(txtApellidosE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
            registro.setDireccion(txtDireccionE.getText());
            registro.setTurno(txtTurnoE.getText());
            registro.setCargoEmpleado(((Empleado) cbxCargoE.getSelectionModel().getSelectedItem()).getCargoEmpleado());
            
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarEmpleado(?, ?, ?, ?, ?, ?, ?}");
            procedimiento.setInt(1,registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCargoEmpleado());
            procedimiento.execute();
            listaEmpleados.add(registro);
            
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
        txtCodigoE.setDisable(false);
        txtNombresE.setDisable(false);
        txtApellidosE.setDisable(false);
        txtSueldoE.setDisable(false);
        txtDireccionE.setDisable(false);
        txtTurnoE.setDisable(false);
        cbxCargoE.setDisable(false);
    }
    
    public void desactivarControles() {
        txtCodigoE.setDisable(true);
        txtNombresE.setDisable(true);
        txtApellidosE.setDisable(true);
        txtSueldoE.setDisable(true);
        txtDireccionE.setDisable(true);
        txtTurnoE.setDisable(true);
        cbxCargoE.setDisable(true);
    }
    
    public void limpiarControles() {
        txtCodigoE.clear();
        txtNombresE.clear();
        txtApellidosE.clear();
        txtSueldoE.clear();
        txtDireccionE.clear();
        txtTurnoE.clear();
        cbxCargoE.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    public void cambiarMenu(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == cambiarSubMenu) {
            escenarioPrincipal.menuTipoEmpleado();
        }
    }
}
