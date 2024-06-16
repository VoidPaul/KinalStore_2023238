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
import org.pauloalvarez.model.CargoEmpleado;
import org.pauloalvarez.system.Main;

/**
 *
 * @author Paulo Alvarez
 */
public class MenuTipoEmpleadoController implements Initializable {

    private Main escenarioPrincipal;

    private Alert error = new Alert(AlertType.ERROR);

    private enum operaciones {
        AGREGAR, ACTUALIZAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<CargoEmpleado> listaCargosEmpleado = getCargosEmpleado();

    @FXML
    private Button btnRegresar;
    @FXML
    private Button cambiarSubMenu;
    @FXML
    private Button btnAgregarCE;
    @FXML
    private Button btnEliminarCE;
    @FXML
    private Button btnEditarCE;
    @FXML
    private Button btnReporteCE;
    @FXML
    private TextField txtCodigoCE;
    @FXML
    private TextField txtNombreCE;
    @FXML
    private TextField txtDescripcionCE;
    @FXML
    private TableView tblCargosEmpleado;
    @FXML
    private TableColumn colCodigoCE;
    @FXML
    private TableColumn colNombreCE;
    @FXML
    private TableColumn colDescripcionCE;
    @FXML
    private ImageView imgAgregarCE;
    @FXML
    private ImageView imgEliminarCE;
    @FXML
    private ImageView imgEditarCE;

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
        tblCargosEmpleado.setItems(getCargosEmpleado());
        colCodigoCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }

    public void seleccionarElemento() {
        txtCodigoCE.setText(String.valueOf(((CargoEmpleado) tblCargosEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCE.setText(((CargoEmpleado) tblCargosEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCE.setText(((CargoEmpleado) tblCargosEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }

    public ObservableList<CargoEmpleado> getCargosEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_reporteCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("ID"),
                    resultado.getString("Cargo"),
                    resultado.getString("Descripción"))
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

        listaCargosEmpleado = FXCollections.observableArrayList(lista);

        return listaCargosEmpleado;
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarCE.setText("Guardar");
                imgAgregarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecGuardar.png"));
                btnEliminarCE.setText("Cancelar");
                imgEliminarCE.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                btnEditarCE.setDisable(true);
                btnReporteCE.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCE.setText("Agregar");
                imgAgregarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarCE.setText("Eliminar");
                imgEliminarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarCE.setDisable(false);
                btnReporteCE.setDisable(false);
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
                btnAgregarCE.setText("Agregar");
                btnAgregarCE.setDisable(false);
                imgAgregarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMas.png"));
                btnEliminarCE.setText("Eliminar");
                imgEliminarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnEditarCE.setText("Editar");
                btnEditarCE.setDisable(false);
                imgEditarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnReporteCE.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCargosEmpleado.getSelectionModel().getSelectedItem() != null) {

                    Alert confirmacion = new Alert(AlertType.CONFIRMATION);
                    confirmacion.setTitle(null);
                    confirmacion.setHeaderText("Advertencia");
                    confirmacion.setContentText("¿Quiere Eliminar el Registro?");

                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargosEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargosEmpleado.remove((CargoEmpleado) tblCargosEmpleado.getSelectionModel().getSelectedItem());
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
                if (tblCargosEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCE.setText("Actualizar");
                    imgEditarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecActualizar.png"));
                    btnEliminarCE.setText("Cancelar");
                    imgEliminarCE.setImage(new Image("/org/pauloalvarez/assets/images/basura.png"));
                    btnAgregarCE.setDisable(true);
                    btnReporteCE.setDisable(true);
                    activarControles();
                    txtCodigoCE.setDisable(true);
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
                btnEditarCE.setText("Editar");
                imgEditarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecEditar.png"));
                btnEliminarCE.setText("Eliminar");
                imgEliminarCE.setImage(new Image("/org/pauloalvarez/assets/images/selecPersonaMenos.png"));
                btnAgregarCE.setDisable(false);
                btnReporteCE.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {

    }

    public void guardar() {
        try {
            
            CargoEmpleado registro = new CargoEmpleado();
            registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCE.getText()));
            registro.setNombreCargo(txtNombreCE.getText());
            registro.setDescripcionCargo(txtDescripcionCE.getText());
            
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            listaCargosEmpleado.add(registro);
            
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
            
            CargoEmpleado registro = new CargoEmpleado();
            registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCE.getText()));
            registro.setNombreCargo(txtNombreCE.getText());
            registro.setDescripcionCargo(txtDescripcionCE.getText());
            
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            listaCargosEmpleado.add(registro);
            
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
        txtCodigoCE.setDisable(false);
        txtNombreCE.setDisable(false);
        txtDescripcionCE.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoCE.setDisable(true);
        txtNombreCE.setDisable(true);
        txtDescripcionCE.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoCE.clear();
        txtNombreCE.clear();
        txtDescripcionCE.clear();
    }

    @FXML
    public void cambiarMenu(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == cambiarSubMenu) {
            escenarioPrincipal.menuEmpleadoView();
        }
    }
}
