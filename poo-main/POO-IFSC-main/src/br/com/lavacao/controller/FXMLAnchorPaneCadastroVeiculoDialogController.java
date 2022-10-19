/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.lavacao.controller;

import br.com.lavacao.model.domain.Cor;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Veiculo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroVeiculoDialogController implements Initializable {

        @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfCor;

    @FXML
    private TextField tfMarca;

    @FXML
    private TextField tfModelo;

    @FXML
    private TextField tfObservacoes;

    @FXML
    private TextField tfPlaca;
    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Veiculo veiculo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }       

    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }

    public void setBtConfirmarClicked(boolean btConfirmarClicked) {
        this.btConfirmarClicked = btConfirmarClicked;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.tfModelo.setText(this.veiculo.getModelo().getNome());
        this.tfPlaca.setText(this.veiculo.getPlaca());
        this.tfMarca.setText(this.veiculo.getModelo().getMarca().getNome());
        this.tfObservacoes.setText(this.veiculo.getObservacao());
    }
    

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            Marca marca = new Marca();
            Modelo modelo = new Modelo();
            Cor cor = new Cor();
            veiculo.setModelo(modelo);
            veiculo.setPlaca(tfPlaca.getText());
            //veiculo.setCor(cor.getNome(tfCor.getText()));
            veiculo.setObservacao(tfObservacoes.getText());
            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
//        if (this.tfModelo.getText() == null || this.tfModelo.getText().length() == 0) {
//            errorMessage += "Nome inválido.\n";
//        }
//        
//        if (this.tfCpf.getText() == null || this.tfCpf.getText().length() == 0) {
//            errorMessage += "CPF inválido.\n";
//        }
//        
//        if (this.tfTelefone.getText() == null || this.tfTelefone.getText().length() == 0) {
//            errorMessage += "Telefone inválido.\n";
//        }
//        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //exibindo uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Corrija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
}
