/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodAndWeight;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	try {
    		String porzioni = this.txtPorzioni.getText();
    		int porz = Integer.parseInt(porzioni);
    		
    		this.model.creaGrafo(porz);
    		this.boxFood.getItems().addAll(model.vertici());
    		
    		this.txtResult.setText("Grafo creato!\nNumero vertici: " + model.vertici().size() +"\nNumero archi: " + model.numeroArchi());
    		
    		} catch (NumberFormatException e) {
    			txtResult.appendText("Errore: il valore inserito non è un intero.");
    			return;
    	} catch (NullPointerException e) {
    		txtResult.appendText("Errore: numero porzioni non inserito.");
			return;
    	}
    	
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();

    	try {
    		Food cibo = this.boxFood.getValue();
    		
    		List<FoodAndWeight> res = model.migliori(cibo);
    		
    		int i=0;
    		
    		this.txtResult.appendText("(massimo) 5 cibi con calorie congiunte massime a partire da " + cibo + "\n");
    		
    		for (FoodAndWeight f:res) {
    			if (i<5) {
    				this.txtResult.appendText(f.getFood() + " peso: " + f.getWeight() + "\n");
    				i++;
    			}
    		}
    		
    		} catch (NullPointerException e) {
    		txtResult.appendText("Errore: numero porzioni non inserito.");
			return;
    	} catch (NumberFormatException e) {
			txtResult.appendText("Errore: il valore inserito non è un intero.");
			return;
	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear(); 
    	
    	try {
    	
    	Food cibo = this.boxFood.getValue();
    	int numeroStazioni = Integer.parseInt(this.txtK.getText());
    	
    	if (numeroStazioni<1 || numeroStazioni>10) {
    		txtResult.setText("Errore: le stazioni devono essere al massimo 10.");
			return;
    	}
    	
    	model.simula(numeroStazioni, cibo);
    	   	   	
    	txtResult.appendText("Tempo necessario: " + model.getTempo() + " minuti\nCibi preparati: " + model.cibiPreparati());
    	} catch (NullPointerException e) {
    		txtResult.appendText("Errore: numero porzioni non inserito.");
			return;
    	} catch (NumberFormatException e) {
			txtResult.appendText("Errore: il valore inserito non è un intero.");
			return;
	} 
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
