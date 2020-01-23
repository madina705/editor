package com;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View extends Application implements IDataObserver {

    private Controller controller;
    private Scene scene;
    private static final String CANVAS = "drawingBox";
    private static final String OVERVIEW = "overView";

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final double SCALE = 0.4;

    @Override
    public void init() {
        Model model = new Model();
        controller = new Controller(model);
        model.addListener(this);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        ToolBar toolBar = new ToolBar();
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        RadioButton selectRectangle = new RadioButton("Rectangle");
        selectRectangle.setOnAction(event -> controller.selectRectangle());
        RadioButton selectLine = new RadioButton("Line");
        selectLine.setOnAction(event -> controller.selectLine());
        RadioButton selectCommentBox = new RadioButton("Comment Box");
        selectCommentBox.setOnAction(event -> controller.selectCommentBox());
        RadioButton selectName = new RadioButton("Name");
        selectName.setOnAction(event -> controller.selectName());
        RadioButton pickMove = new RadioButton("Move");
        pickMove.setOnAction(event -> controller.selectMove());

        ToggleGroup group = new ToggleGroup();
        pickMove.setToggleGroup(group);
        selectRectangle.setToggleGroup(group);
        selectLine.setToggleGroup(group);
        selectCommentBox.setToggleGroup(group);
        selectName.setToggleGroup(group);
        selectRectangle.setSelected(true);

        TextField textField = new TextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> controller.textFieldTextChanged(newValue));

        hBox.getChildren().addAll(selectRectangle, selectLine, selectCommentBox, selectName, textField, pickMove);
        toolBar.getItems().add(hBox);

        StackPane canvasPane = new StackPane();
        canvasPane.setMinSize(WIDTH * (0.75), HEIGHT * (0.75));
        Canvas drawingBox = new Canvas();
        drawingBox.setId(CANVAS);
        drawingBox.widthProperty().bind(canvasPane.widthProperty());
        drawingBox.heightProperty().bind(canvasPane.heightProperty());
        drawingBox.setOnMousePressed(event -> controller.pressedOnDrawingBox(event));
        drawingBox.setOnMouseDragged(event -> controller.draggedOnDrawingBox(event));
        drawingBox.setOnMouseReleased(event -> controller.dragExitedOnDrawingBox(event));
        canvasPane.getChildren().add(drawingBox);
        canvasPane.widthProperty().addListener((observable, oldValue, newValue) -> controller.canvasPaneSizeChanged(observable));
        canvasPane.heightProperty().addListener((observable, oldValue, newValue) -> controller.canvasPaneSizeChanged(observable));

        StackPane overViewPane = new StackPane();
        overViewPane.setMinSize(WIDTH * (0.25), canvasPane.getMinHeight());
        Canvas overView = new Canvas();
        overView.setId(OVERVIEW);
        overView.widthProperty().bind(canvasPane.widthProperty());
        overView.heightProperty().bind(canvasPane.heightProperty());
        overViewPane.getChildren().add(overView);

        root.setTop(toolBar);
        root.setCenter(canvasPane);
        root.setRight(overViewPane);

        scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle("Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Canvas canvas) {
        SnapshotParameters params = new SnapshotParameters();
        WritableImage image = canvas.snapshot(params, null);

        // Drawing panel
        Canvas drawingBox = (Canvas) scene.lookup("#" + CANVAS);
        GraphicsContext gc = drawingBox.getGraphicsContext2D();
        gc.clearRect(0, 0, drawingBox.getWidth(), drawingBox.getHeight());
        gc.drawImage(image, 0, 0);

        // OverView
        Canvas overView = (Canvas) scene.lookup("#" + OVERVIEW);
        gc = overView.getGraphicsContext2D();
        gc.clearRect(0, 0, overView.getWidth(), overView.getHeight());
        params.setTransform(javafx.scene.transform.Transform.scale(SCALE, SCALE));
        image = canvas.snapshot(params, null);
        gc.drawImage(image, 0, 0);
    }
}
