/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Avversario;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String goalS = this.txtGoals.getText();
    	double goal;
    	try {
    		goal = Double.parseDouble(goalS);
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire solo valori numerici!");
    		return;
    	}
    	this.model.creaGrafo(goal);
    	this.txtResult.appendText("Grafo creato\n");
    	this.txtResult.appendText("#Vertici: "+ this.model.getVerticesSize()+"\n");
    	this.txtResult.appendText("#Archi: "+ this.model.getEdgeSize()+"\n");

    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	this.txtResult.clear();
    	String goalS = this.txtGoals.getText();
    	double goal;
    	if(this.model.getGrafo()==null) {
    		this.txtResult.appendText("Creare il grafo!");
    		return;
    	}
    	try {
    		goal = Double.parseDouble(goalS);
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire solo valori numerici!");
    		return;
    	}
    	String kS = this.txtK.getText();
    	int k=0;
    	try {
    		k = Integer.parseInt(kS);
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire solo valori numerici!");
    		return;
    	}
    	
    	this.txtResult.appendText("DREAM TEAM: \n");
    	
    	for(Player p : this.model.getDreamTeam(k)) {
    		this.txtResult.appendText(p.toString()+"\n");
    	}
    	
    	this.txtResult.appendText("GRADO DI TITOLARITA': "+ this.model.getBestDegree());
    	
    	
    	

    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	this.txtResult.clear();
    	String goalS = this.txtGoals.getText();
    	double goal;
    	if(this.model.getGrafo()==null) {
    		this.txtResult.appendText("Creare il grafo!");
    		return;
    	}
    	try {
    		goal = Double.parseDouble(goalS);
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire solo valori numerici!");
    		return;
    	}
    	
    	this.txtResult.appendText("TOP PLAYER: "+ this.model.FindTopPlayer().getTop().toString()+"\n\n");
    	this.txtResult.appendText("AVVERSARI BATTURI: \n");
    	
    	for(Avversario avv : this.model.FindTopPlayer().getBattuti()) {
    		this.txtResult.appendText(avv.getAvversario().toString()+" | "+ avv.getDelta()+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
