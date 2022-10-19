/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.dao.MarcaDAO;
import br.com.lavacao.model.database.Database;
import br.com.lavacao.model.database.DatabaseFactory;
import br.com.lavacao.model.domain.Marca;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroMarcaController implements Initializable {

    
    @FXML
    private Button btnAlterar;

    @FXML
    private Button btExcluir;
    
    @FXML
    private Button btInserir;

    @FXML
    private Label lbMarcaDescricao;

    @FXML
    private Label lbMarcaId;

    @FXML
    private TableColumn<Marca, String> tableColumnCategoriaDescricao;

    @FXML
    private TableView<Marca> tableViewMarcas;
    
    private List<Marca> listaMarcas;
    private ObservableList<Marca> observableListMarcas;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        carregarTableViewMarcas();
        
        tableViewMarcas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCategorias(newValue));
    }     
    
    public void carregarTableViewMarcas() {
        tableColumnCategoriaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        listaMarcas = marcaDAO.listar();
        
        observableListMarcas = FXCollections.observableArrayList(listaMarcas);
        tableViewMarcas.setItems(observableListMarcas);
    }
    
    public void selecionarItemTableViewCategorias(Marca marca) {
        if (marca != null) {
            lbMarcaId.setText(String.valueOf(marca.getId())); 
            lbMarcaDescricao.setText(marca.getNome());
        } else {
            lbMarcaId.setText(""); 
            lbMarcaDescricao.setText("");
        }
        
    }
    
    @FXML
    public void handleBtInserir() throws IOException {
        Marca marca = new Marca();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroCategoriaDialog(marca);
        if (btConfirmarClicked) {
            marcaDAO.inserir(marca);
            carregarTableViewMarcas();
        } 
    }
    
    @FXML 
    public void handleBtAlterar() throws IOException {
        Marca marca = tableViewMarcas.getSelectionModel().getSelectedItem();
        if (marca != null) {
            boolean btConfirmarClicked = showFXMLAnchorPaneCadastroCategoriaDialog(marca);
            if (btConfirmarClicked) {
                marcaDAO.alterar(marca);
                carregarTableViewMarcas();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Categoria na tabela ao lado");
            alert.show();
        }
    }
    
    @FXML
    public void handleBtExcluir() throws IOException {
        Marca marca = tableViewMarcas.getSelectionModel().getSelectedItem();
        if (marca != null) {
            marcaDAO.remover(marca);
            carregarTableViewMarcas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Categoria na tabela ao lado");
            alert.show();
        }
    }

    private boolean showFXMLAnchorPaneCadastroCategoriaDialog(Marca marca) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroMarcaController.class.getResource("../view/FXMLAnchorPaneCadastroCategoriaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categoria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //enviando o obejto categoria para o controller
        FXMLAnchorPaneCadastroCategoriaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMarca(marca);
        
        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();
        
        return controller.isBtConfirmarClicked();
    }
    
}
