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
public class FXMLAnchorPaneCadastroCategoriaController implements Initializable {

    
    @FXML
    private Button btnAlterar;

    @FXML
    private Button btExcluir;
    
    @FXML
    private Button btInserir;

    @FXML
    private Label lbCategoriaDescricao;

    @FXML
    private Label lbCategoriaId;

    @FXML
    private TableColumn<Marca, String> tableColumnCategoriaDescricao;

    @FXML
    private TableView<Marca> tableViewCategorias;
    
    private List<Marca> listaCategorias;
    private ObservableList<Marca> observableListCategorias;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO categoriaDAO = new MarcaDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoriaDAO.setConnection(connection);
        carregarTableViewCategoria();
        
        tableViewCategorias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCategorias(newValue));
    }     
    
    public void carregarTableViewCategoria() {
        tableColumnCategoriaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        listaCategorias = categoriaDAO.listar();
        
        observableListCategorias = FXCollections.observableArrayList(listaCategorias);
        tableViewCategorias.setItems(observableListCategorias);
    }
    
    public void selecionarItemTableViewCategorias(Marca categoria) {
        if (categoria != null) {
            lbCategoriaId.setText(String.valueOf(categoria.getId())); 
            lbCategoriaDescricao.setText(categoria.getDescricao());
        } else {
            lbCategoriaId.setText(""); 
            lbCategoriaDescricao.setText("");
        }
        
    }
    
    @FXML
    public void handleBtInserir() throws IOException {
        Marca categoria = new Marca();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroCategoriaDialog(categoria);
        if (btConfirmarClicked) {
            categoriaDAO.inserir(categoria);
            carregarTableViewCategoria();
        } 
    }
    
    @FXML 
    public void handleBtAlterar() throws IOException {
        Marca categoria = tableViewCategorias.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            boolean btConfirmarClicked = showFXMLAnchorPaneCadastroCategoriaDialog(categoria);
            if (btConfirmarClicked) {
                categoriaDAO.alterar(categoria);
                carregarTableViewCategoria();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Categoria na tabela ao lado");
            alert.show();
        }
    }
    
    @FXML
    public void handleBtExcluir() throws IOException {
        Marca categoria = tableViewCategorias.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            categoriaDAO.remover(categoria);
            carregarTableViewCategoria();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Categoria na tabela ao lado");
            alert.show();
        }
    }

    private boolean showFXMLAnchorPaneCadastroCategoriaDialog(Marca categoria) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroCategoriaController.class.getResource("../view/FXMLAnchorPaneCadastroCategoriaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categoria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //enviando o obejto categoria para o controller
        FXMLAnchorPaneCadastroCategoriaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCategoria(categoria);
        
        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();
        
        return controller.isBtConfirmarClicked();
    }
    
}
