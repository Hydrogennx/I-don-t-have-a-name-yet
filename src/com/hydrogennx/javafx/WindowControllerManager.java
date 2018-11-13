package com.hydrogennx.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

/**
 * A menu creator that handles the creation and deletion of JavaFX GUIs.
 * The controller classes cannot do this themselves, as you need an instance of a class to do so, which is the very thing you're trying to do.
 * The main application can, but shouldn't, be in charge of this, because it should not have to be aware of the ActionPhase or TurnPhase classes.
 * So, new class.
 */
public class WindowControllerManager extends StackPane {

    private HashMap<String, Node> screens = new HashMap<>();
    private Node currentScreen;

    public WindowController loadScreen(String id, String resourceId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceId));

            Parent mainWindow = loader.load();
            WindowController windowController = loader.getController();
            windowController.setManager(this);
            screens.put(id, mainWindow);

            return windowController;

        } catch (IOException e) {
            System.err.println("Unable to load screen.");
            e.printStackTrace();

            return null;

        }
    }

    public void unloadScreen(String id) {
        screens.remove(id);
    }

    public boolean setScreen(String id) {
        Node nextScreen = screens.get(id);

        if (nextScreen == null) {
            return false;
        }

        getChildren().remove(currentScreen);
        getChildren().add(nextScreen);
        currentScreen = nextScreen;
        return true;
    }

    public Node getScreen(String id) {

        return screens.get(id);

    }
}
