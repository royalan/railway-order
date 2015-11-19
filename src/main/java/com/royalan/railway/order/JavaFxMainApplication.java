package com.royalan.railway.order;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxMainApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
		//用讀進來FXML的作為Scene的root node
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
	 
		//顯示Stage
		primaryStage.setTitle("台鐵訂票小幫手");
		primaryStage.show();
	}

}
