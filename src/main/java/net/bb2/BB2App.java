/*
 * #%L
 * Blood Brothers 2 Converters.
 * %%
 * Copyright (C) 2015 Blood Brothers 2 Wiki Members
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.bb2;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class BB2App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 350, 225);
		stage.setScene(scene);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);

		scene.setRoot(grid);

		final TextField bb2Version = new TextField();
		bb2Version.setPromptText("Blood Brothers 2 app version");
		bb2Version.setPrefColumnCount(10);
		bb2Version.getText();
		GridPane.setConstraints(bb2Version, 0, 0);
		GridPane.setColumnSpan(bb2Version, 2);
		grid.getChildren().add(bb2Version);

		final TextField outputDirectory = new TextField();
		outputDirectory.setPromptText("Output directory");
		GridPane.setConstraints(outputDirectory, 0, 1);
		grid.getChildren().add(outputDirectory);

		final Button directoryButton = new Button();
		GridPane.setConstraints(directoryButton, 1, 1);
		grid.getChildren().add(directoryButton);
		directoryButton.setText("Select output dir...");
		directoryButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser dirChooser = new DirectoryChooser();
				File selectedDirectory = dirChooser.showDialog(stage);

				if (selectedDirectory != null) {
					outputDirectory.setText(selectedDirectory.getAbsolutePath());
				}
			}
		});

		final Label status = new Label();
		GridPane.setConstraints(status, 0, 5);
		GridPane.setColumnSpan(status, 2);
		grid.getChildren().add(status);

		final Label instructions = new Label();
		instructions.setText("For all known version numbers, see:");
		GridPane.setConstraints(instructions, 0, 2);
		GridPane.setColumnSpan(instructions, 2);
		grid.getChildren().add(instructions);

		final Hyperlink hpl = new Hyperlink("http://www.bloodbrothers2.info");
		GridPane.setConstraints(hpl, 0, 3);
		GridPane.setColumnSpan(hpl, 2);
		grid.getChildren().add(hpl);
		hpl.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(
						new URI("http://www.bloodbrothers2.info/viewpeeklist.php"));
				}
				catch (Exception exc) {
					exc.printStackTrace();
					status.setText("Failed to open site: " + exc.getMessage());
				}
			}
		});

		Button submit = new Button("Generate");
		GridPane.setConstraints(submit, 0, 6);
		grid.getChildren().add(submit);

		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 1, 6);
		grid.getChildren().add(clear);

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if ((bb2Version.getText() == null || bb2Version.getText().isEmpty())) {
					status.setText("You have not entered a version number.");
				}
				else if (outputDirectory.getText() == null ||
					outputDirectory.getText().isEmpty())
				{
					status.setText("You have not selected an output directory.");
				}
				else {
					// FIXME currently doesn't update the status page
					status.setText("Generating spreadsheet. Please wait...");

					Main.setStage(stage);
					try {
						Main.main(bb2Version.getText(), outputDirectory.getText());
					}
					catch (Exception exc) {
						status.setText("Spreadsheet creation failed: " + exc.getMessage());
						exc.printStackTrace();
					}
				}
			}
		});

		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				bb2Version.clear();
				outputDirectory.clear();
			}
		});

		stage.setTitle("BB2 Wiki Helper");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(final String... args) throws Exception {
		launch(args);
	}
}
