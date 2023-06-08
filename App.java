import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class App extends Application {
	private Folder rootFolder;	// rootFolder which is the rootItem in treeView
	private Folder tempFolder;	// temporary Folder used in search
	private String info = "";	// containing contents of folders that will be shown in textArea 

	public static void main(String[] args) throws IOException {
		launch(args);

	}

	@Override
	public void start(Stage arg) throws Exception {
		TreeView<String> tree = new TreeView<String>();		// treeView contains the folders
		tree.setTranslateX(20);
		tree.setTranslateY(20);
		tree.setPrefSize(230, 400);

		TextArea infoArea = new TextArea();					// textArea contains the content of folders
		infoArea.setTranslateX(270);
		infoArea.setTranslateY(20);
		infoArea.setPrefSize(420, 400);
		infoArea.setEditable(false);

		Button selectButton = new Button("Select Folder");	// button for choosing a folder
		selectButton.setTranslateX(150);
		selectButton.setTranslateY(430);
		selectButton.setPrefSize(100, 30);

		DirectoryChooser chooser = new DirectoryChooser();	
		chooser.setTitle("Select Folder");

		Group root = new Group();
		root.getChildren().add(selectButton);
		root.getChildren().add(tree);
		root.getChildren().add(infoArea);

		Scene scene = new Scene(root, 710, 470);
		scene.setFill(Color.web("#A9A9A9", 0.2));
		arg.setTitle("Test");
		arg.setScene(scene);
		arg.show();

		selectButton.setOnAction(e -> {	// if the user click select folder button
			infoArea.clear();
			java.io.File selectedFile = chooser.showDialog(arg);	// the selected folder

			if (selectedFile != null) {
				java.io.File rootFile = new java.io.File(selectedFile.getPath());
				rootFolder = new Folder(rootFile.getName(), rootFile.getPath());
				TreeItem<String> rootItem = new TreeItem<String>(rootFolder.getName(), getImage());	// creating the rootItem in treeView
				tree.setRoot(rootItem);
				traverse(rootFile.listFiles(), rootFolder, rootItem);	// fill the folder and subFolders in the structure
				System.out.println(rootFolder.getSize() + " KB"); // single line call for calculating the size of folders
			}

		});

		tree.setOnMouseClicked(e -> { // if treeView is clicked
			boolean flag = true;
			String path = "";
			if (tree.getSelectionModel().getSelectedItem() != null) {	// find the path of selected folder in the treeView
				TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
				while (flag) {
					if (item.getParent() == null)
						flag = false;

					else {
						path = "\\".charAt(0) + item.getValue() + path;
						item = item.getParent();
					}

				}
				path = rootFolder.getPath() + path;
				findComponent(rootFolder, path);	// search for the folder of the found path
				infoArea.clear();
				display(tempFolder);				// show the contents of folders in textArea
				infoArea.setText(info);
				info = "";
				tempFolder = null;
			}

		});

	}

	public ImageView getImage() {
		return new ImageView(new Image(getClass().getResourceAsStream("images/image.png")));
	}

	public void findComponent(Folder folder, String folderPath) {
		if (folder.getPath().equals(folderPath))
			tempFolder = folder;

		for (FileSystemComponent component : folder.getComponents())
			if (!component.isFile())
				findComponent((Folder) component, folderPath);

	}

	public void traverse(java.io.File files[], Folder folder, TreeItem<String> treeItem) {
		for (java.io.File f : files) {
			if (f.isFile()) {
				File file = new File(f.getName(), f.length());
				folder.addComponent(file);
			} else {
				Folder subFolder = new Folder(f.getName(), f.getPath());
				folder.addComponent(subFolder);
				java.io.File file = new java.io.File(subFolder.getPath());
				TreeItem<String> item = new TreeItem<String>(file.getName(), getImage());
				treeItem.getChildren().add(item);
				traverse(f.listFiles(), subFolder, item);
			}
		}
	}

	public void display(Folder folder) {
		if (folder.equals(tempFolder))
			info += folder.getName() + " (" + folder.getSize() + " KB) \n   ";

		for (FileSystemComponent component : folder.getComponents()) {
			if (component.isFile()) {
				info += "  -" + component.getName() + " (" + component.getSize() + " KB)\n   ";
			} else {
				info += component.getName() + " (" + component.getSize() + " KB) \n   ";
				display(((Folder) component));
			}
		}

	}

}
