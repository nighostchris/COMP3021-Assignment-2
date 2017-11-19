package pa1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Duration;

import units.*;
import terrain.*;



public class GameApplication extends Application 
{
	// ============================
	// Section: JavaFX GUI Elements
	// ============================

	// Note: Please play the game in full-screen.
	// Resolution, Tiles and Offset for Rendering Unit ID on bottom-right.
	private static final int RESOLUTION_TOTAL_WIDTH = 800;
	private static final int RESOLUTION_TOTAL_HEIGHT = 600;
	private static final int RESOLUTION_GAMEPLAY_WIDTH = 640;
	private static final int RESOLUTION_GAMEPLAY_HEIGHT = 480;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	private static final int UNIT_TEXT_WIDTH_OFFSET = 20;
	private static final int UNIT_TEXT_HEIGHT_OFFSET = 28;
	
	// Scene and Stage
	private static final int SCENE_NUM = 4;
	private static final int SCENE_WELCOME = 0;
	private static final int SCENE_STARTGAME = 1;
	private static final int SCENE_GAMEPLAY = 2;
	private static final int SCENE_GAMEOVER = 3;
	private static final String[] SCENE_TITLES = {"Welcome", "Start Game", "Game Play", "Game Over"};
	private Scene[] scenes = new Scene[SCENE_NUM];
	private Stage stage;
	
	// Part 1: paneWelcome
	private Label lbMenuTitle;
	private Button btNewGame, btBackgroundMusic, btQuit;

	// Part 2: paneGameStart
	private Canvas canvasGameStart;
	private Button btLoadTerrainMap, btLoadPlayersAndUnits, btStartGame, btQuitToMenu;
	private Label lbMapPosition, lbTileInfo, lbUnitDetails;
	private ListView<String> listViewUnit;
	private ObservableList<String> listViewUnitItems = FXCollections.observableArrayList();
	private String listViewSelectedUnit = "";

	// Part 3: paneGamePlay
	private static final int NUM_LAYERS = 4;
	private Canvas[] canvasGamePlayLayers = new Canvas[NUM_LAYERS];
	private static final int TERRAIN_LAYER = 0;
	private static final int UNIT_LAYER = 1;
	private static final int TEXT_LAYER = 2;
	private static final int RANGE_INDICATOR_LAYER = 3;
	private static final int TOP_LAYER = NUM_LAYERS - 1;
	private Button btGamePlayQuitToMenu;
	private Label lbCurrentTurn, lbGamePlayInfo;
	private ObservableList<VBox> listViewGamePlayUnitInfoItems = FXCollections.observableArrayList();

	// Part 4: paneGameOver
	private Label lbGameOver;
	private Button btExitToMenu, btGameOverQuitGame;
	
	
	
	//==========================================
	// Section: Control and Rendering Attributes
	//==========================================
	
	// MediaPlayer and Background Music
	private MediaPlayer backgroundMusic;
	private boolean isBackgroundMusicEnabled = false;

	// GameEngine and GameMap
	public static GameEngine gameEngine = new GameEngine();
	public static GameMap gameMap = new GameMap();
	
	// Animation Thread Handles.
	private ArrayList<Thread> animThreads = new ArrayList<>();
	
	// GamePlay Global Variables.
	private Unit gamePlaySelectedUnit = null;
	private boolean isSelectedUnitAttackPhase = false;
	private static final Color UNIT_READY_TEXT_COLOR = Color.WHITE;
	private static final Color UNIT_DONE_TEXT_COLOR = Color.SILVER;
	private static final Color MOVEMENT_RANGE_INDICATOR_COLOR = Color.WHITE;
	private static final Color ATTACK_RANGE_INDICATOR_COLOR = Color.RED;
	
	
	
	// ===============================
	// Section: Content Panes Creation
	// ===============================
	// Hint: This section is similar to Lab 10.
	
	// TODO: Create a pane for scene "Welcome".
	// You need to have BorderPane, VBox, Label and 3 Buttons.
	private Pane paneWelcome() 
	{
		
	}

	// TODO: Create a pane for scene "GameStart".
	// You need to have a BorderPane with one VBox on the left and one VBox on the center.
	// In VBox on the left, 4 Buttons, 1 Label, 1 ListView<String> are required.
	// In VBox on the center, 1 Canvas is required.
	// Suggestion: Set the size of ListView to width:150 height:200.
	// Suggestion: Set the size of Canvas based on RESOLUTION_GAMEPLAY_WIDTH/HEIGHT.
	private Pane paneStartGame() 
	{
		
	}
 
	// TODO: Create a pane for scene "GamePlay".
	// You need to have a BorderPane, StackPane, multiple Canvases, Label of Current Turn, Label of GamePlay Info and a Button.
	// Label of Current Turn is on top of the other Label, it shows the current turn (e.g. Red/Blue)
	// Label of GamePlayInfo shows the property of the selectedUnit.
	// The content of the Label will be updated later.
	// Set the content of the Label to an empty string for now.
	// -----
	// The Canvases will be stacked on top of each other. They will be used for rendering the GamePlay.
	// With separate layers, we can focus on only redrawing the things that changed. Don't need to redraw the whole GamePlay graphics.
	// StackPane is used to stack the Canvases on top of each other with stackPane.getChildren().add(canvas);
	// StackPane displays like a Stack, the canvas added last will be displayed on top.
	private Pane paneGamePlay() 
	{
		
	}

	// TODO: Create a pane for scene "GameOver".
	// You need to have a BorderPane, VBox, 1 Label and 2 Buttons.
	// Initiate the Label with empty string (i.e. "") for this moment.
	// It is used to display who is the winner later.
	private Pane paneGameOver() 
	{
		
	}
	
	
	
	// ======================================================
	// Section: Event Handlers (Naming convention: handle___)
	// ======================================================
	// Hint: This section is similar to Lab 11.
	
	private void handleBackgroundMusic()
	{
		if (!isBackgroundMusicEnabled) 
		{
			backgroundMusic.play();
			isBackgroundMusicEnabled = true;
			btBackgroundMusic.setText("Disable Background Music");
		} 
		
		else 
		{
			backgroundMusic.stop();
			isBackgroundMusicEnabled = false;
			btBackgroundMusic.setText("Enable Background Music");
		}
	}
	
	private void handleNewGame() 
	{
		putSceneOnStage(SCENE_STARTGAME);
	}
	
	// TODO: Show an Alert asking the User to confirm Exiting with YES/NO Buttons.
	// Call Platform.exit() to exit the game.
	private void handleExitGame()
	{
		
	}
	
	// TODO: Load Terrain Map from chosen textfile.
	// Use a FileChooser for the User to select the textfile.
	// Render Terrain Map onto canvasGameStart.
	// Catch and display IOExceptions with showErrorDialog(). These IOExceptions no longer stop the game.
	// -----
	// If this method is called multiple times, unload the old Terrain Map then load the new one.
	// If Players and Units are loaded, reset the starting location of all Units, and also reset listViewUnitItems.
	// Also render the new Terrain Map.
	private void handleLoadMap() 
	{
		
	}
	
	// TODO: Load Players and Units from chosen textfile.
	// Use a FileChooser for the User to select the textfile.
	// Update the loaded Players and Units in listViewUnitItems. 
	// Catch and display IOExceptions with showErrorDialog(). These IOExceptions no longer stop the game.
	// -----
	// If this method is called multiple times, unload the old set of Players and Units then load the new set.
	// If any Units were placed onto the Terrain Map, remember to reset their starting locations and remove them from canvasGameStart rendering.
	private void handleLoadPlayersAndUnits() 
	{
		
	}
	
	private void handleGameStartButton() 
	{
		if (!gameMap.isLoaded()) 
		{
			showErrorDialog("Game Map is not loaded.");
			return;
		}

		if (!gameEngine.isLoaded()) 
		{
			showErrorDialog("Players and Units are not loaded.");
			return;
		}

		if (listViewUnitItems.size() > 0) 
		{
			showErrorDialog("Please place all Units on the Terrain Map.");
			return;
		}
		
		clearLayer(canvasGameStart);
		lbMapPosition.setText("");
		
		updateCurrentTurnLabel();
		updateListViewGamePlayUnitInfo();
		renderInitGamePlayCanvas();
		
		putSceneOnStage(SCENE_GAMEPLAY);
	}
	
	private void handleCanvasGameStartMouseMovement(double canvasX, double canvasY)
	{
		if (gameMap.isLoaded())
		{
			lbMapPosition.setText(String.format("Map Position: %d %d", gameMap.canvasToTerrainMapX(canvasX), gameMap.canvasToTerrainMapY(canvasY)));
		}
	}
	
	// TODO: Place loaded Units from the listView onto the Terrain Map.
	private void handleCanvasGameStartMouseClick(double canvasX, double canvasY) 
	{
		if (gameMap.isLoaded()) 
		{
			if (!listViewSelectedUnit.equals("")) 
			{
				char unitId = listViewSelectedUnit.charAt(0); // Get the ID of selectedUnit.
				
				Unit selectedUnit = null;
				// ================================================
				// TODO: 1) Find the selectedUnit from the Players.
				
				// ================================================

				if (selectedUnit != null)
				{
					// terrainMapX and terrainMapY contain the coordinates of where the User clicked on the Terrain Map.
					int terrainMapX = gameMap.canvasToTerrainMapX(canvasX);
					int terrainMapY = gameMap.canvasToTerrainMapY(canvasY);
					
					// TODO 2): Check to make sure that the Terrain Map location is not blocked (by other Unit or by MOVEMENT_COST -1).
					if () 
					{
						listViewUnit.getSelectionModel().clearSelection();
						listViewUnitItems.remove(listViewSelectedUnit);

						// TODO 3): Set the Starting Location of the Unit. Also render it and its Unit Text onto canvasGameStart.
						
					} 
					
					else 
					{
						// TODO 4): Otherwise, showErrorDialog() that "Target Location is blocked."
						
						return;
					}
				}
				// ================================================================

				listViewSelectedUnit = "";
			} 
		}
	}
	
	private void handleCanvasGamePlayMouseClick(double canvasX, double canvasY) 
	{
		int terrainX = gameMap.canvasToTerrainMapX(canvasX);
		int terrainY = gameMap.canvasToTerrainMapY(canvasY);
		
		if (gamePlaySelectedUnit == null) 
		{
			processGamePlayWithoutUnitSelected(terrainX, terrainY);
		} 
				
		else 
		{
			processGamePlayWithUnitSelected(terrainX, terrainY);
		}
	}
	
	
	
	// =======================================================================
	// Section: Event Handler Initializers (Naming convention: init___Handler)
	// =======================================================================
	// Note: This section is similar to Lab 11.

	private void initEventHandlers() 
	{
		initBackgroundMusicHandler();
		initWelcomeSceneHandler();
		initStartGameSceneHandler();
		initGamePlaySceneHandler();
		initGameOverHandler();
	}
	
	private void initBackgroundMusicHandler() 
	{
		//backgroundMusic = new MediaPlayer(new Media(new File("src/audio/landius.mp3").toURI().toString()));
		
		// From local path to URL:
		// Reference: https://stackoverflow.com/questions/34859603/mediaplayer-in-jar
		backgroundMusic = new MediaPlayer(new Media(getClass().getResource("/audio/landius.mp3").toExternalForm()));
		
		//backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // cycleCount is how many times to loop. However, internal bug with javafx, won't loop forever.
		
		// Instead, create a new thread to manually Loop from the beginning every time the music ends.
		backgroundMusic.setOnEndOfMedia(new Runnable()
		{
			public void run()
			{
				backgroundMusic.seek(Duration.ZERO); // Loop from the beginning.
			}
		});
	}
	
	// TODO: Connect the handle___() methods in scene "Welcome".
	// There are 3 Buttons in scene "Welcome".
	private void initWelcomeSceneHandler() 
	{

	}

	private void initStartGameSceneHandler() 
	{
		listViewUnit.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) 
			{
				if (new_val != null) 
				{
					listViewSelectedUnit = new_val;
				}
			}
		});
		
		// ==============================
		// TODO: Connect the handle___() methods to "Load Terrain Map", "Load Players and Units", and "Start Game" Buttons.
		// Also connect the handle___() method to canvasGameStart, which places Units onto the Terrain Map when the User Mouse-Clicks.
		// "Quit to Menu" Button handle___() method as well as canvasGameStart.setOnMouseMoved() has already been setup as a code reference.

		
		canvasGameStart.setOnMouseMoved(e -> handleCanvasGameStartMouseMovement(e.getX(), e.getY()));
			
		btQuitToMenu.setOnAction(e ->
		{
			gameEngine.unloadPlayersAndUnits();
			gameMap.unloadTerrainMap();
			clearLayer(canvasGameStart);
			lbMapPosition.setText("");
			putSceneOnStage(SCENE_WELCOME);
		});
		// ===================================================
	}
	
	// TODO: Connect the handle___() methods to scene "GamePlay".
	// Use the Top Layer of canvasGamePlayLayers to setup onMouseClicked() with its handle___() method.
	// Remember to call stopGamePlay() when clicked on "Quit To Menu" Button before going back to scene "Welcome".
	private void initGamePlaySceneHandler()
	{

	}
	
	// TODO: Connect the handle___() methods to scene "GameOver".
	// There are 2 Buttons, "Exit to Menu" and "Quit Game".
	private void initGameOverHandler()
	{

	}

	
	
	// ====================================
	// Section: Text and UI-Related Methods
	// ====================================
	
	// Shows an Error Message as a GUI pop-up.
	private void showErrorDialog(String errorMessage) 
	{
		Alert alert = new Alert(AlertType.ERROR, errorMessage);
		alert.showAndWait();
	}
	
	// Update the Current Turn Label to show which Player's turn is it right now.
	private void updateCurrentTurnLabel() 
	{
		lbCurrentTurn.setText(gameEngine.getCurrentPlayer().getName() + "'s Turn");
	}
	
	// Update the Game Over Label to show the Winner (or a Draw).
	private void updateGameOverLabel() 
	{
		for (Player player:gameEngine.getPlayers()) 
		{
			if (player.hasUnitsRemaining()) 
			{
				lbGameOver.setText("Game Over: " + player.getName() + " Wins!");
				return;
			}
		}
		
		lbGameOver.setText("Game Over: All Players Eliminated! Draw!");
	}
	
	// Updates listViewUnitItems in GameStart.
	private void updateListViewUnitItems() 
	{
		listViewUnitItems.clear();
		for (Player player:gameEngine.getPlayers()) 
		{
			for (Unit unit:player.getUnits()) 
			{
				if (unit instanceof Archer) 
				{
					listViewUnitItems.add(unit.getId() + ": " + player.getName() + "'s " + "Archer");
				} 
				
				else if (unit instanceof Cavalry) 
				{
					listViewUnitItems.add(unit.getId() + ": " + player.getName() + "'s " + "Cavalry");
				} 
				
				else if (unit instanceof Infantry) 
				{
					listViewUnitItems.add(unit.getId() + ": " + player.getName() + "'s " + "Infantry");
				} 
				
				else if (unit instanceof Pikeman) 
				{
					listViewUnitItems.add(unit.getId() + ": " + player.getName() + "'s " + "Pikeman");
				}
			}
		}
	}	

	// Updates listViewGamePlayUnitInfoItems in GamePlay.
	// Also shows the Heal Button for each Ready Unit of the current Player.
	private void updateListViewGamePlayUnitInfo() 
	{
		listViewGamePlayUnitInfoItems.clear();
		Player currentPlayer = gameEngine.getCurrentPlayer();
		
		for (Unit unit:currentPlayer.getUnits()) 
		{
			if (unit.isReady() && unit.isAlive()) 
			{
				Button btHeal = new Button("Heal");
				btHeal.setOnAction((event) -> 
				{
					// Only process the Heal button when not in-between the Selected Unit's Movement/Attack Phase.
					if (gamePlaySelectedUnit == null)
					{
						unit.heal(); 
						unit.endTurn();
						renderUnitText(canvasGamePlayLayers[TEXT_LAYER], unit);
						updateListViewGamePlayUnitInfo();
						checkPlayerTurn();
					}
				});
				
				VBox newItem = new VBox(new Label(unit.getId() + ""), new Label("Health: " + unit.getHealth()), btHeal);
				listViewGamePlayUnitInfoItems.add(newItem);
			}
		}
	}
	
	
	
	// ==================================
	// Section: Rendering-Related Methods
	// ==================================
	
	// Renders "image" at position "(terrainMapX, terrainMapY)" on canvas "layer".
	private void renderImageTile(Canvas layer, Image image, int terrainMapX, int terrainMapY)
	{
		layer.getGraphicsContext2D().drawImage(image, gameMap.terrainMapToCanvasX(terrainMapX), gameMap.terrainMapToCanvasY(terrainMapY));
	}
	
	// Clears the rendering at position "(terrainMapX, terrainMapY)" on canvas "layer". 
	// Cleared rendering becomes transparent, so layers at the bottom can be seen.
	private void clearTile(Canvas layer, int terrainMapX, int terrainMapY)
	{
		layer.getGraphicsContext2D().clearRect(gameMap.terrainMapToCanvasX(terrainMapX), gameMap.terrainMapToCanvasY(terrainMapY), TILE_WIDTH, TILE_HEIGHT);
	}
	
	// Clears the whole layer to become transparent.
	private void clearLayer(Canvas layer)
	{
		layer.getGraphicsContext2D().clearRect(0, 0, layer.getWidth(), layer.getHeight());
	}
	
	// TODO: Render the Terrain Image of "terrain" onto "layer" at position "(terrainMapX, terrainMapY)".
	// You may call renderImageTile().
	private void renderTerrainTile(Canvas layer, Terrain terrain, int terrainMapX, int terrainMapY)
	{

	}
	
	// TODO: Render the whole Terrain Map onto the given canvas layer.
	// You may call renderTerrainTile().
	private void renderTerrainMap(Canvas layer)
	{

	}
	
	// TODO: Render the Unit onto the given canvas layer. Hint: Unit keeps track of its location.
	// You may call renderImageTile().
	private void renderUnit(Canvas layer, Unit unit)
	{

	}
	
	// TODO: Render the Unit ID of the given Unit onto the given canvas layer.
	// Use UNIT_READY_TEXT_COLOR and UNIT_DONE_TEXT_COLOR for Ready and Done Unit respectively.
	// Use setStroke(color), then strokeText() to draw the text.
	// Add UNIT_TEXT_WIDTH_OFFSET and UNIT_TEXT_HEIGHT_OFFSET to render the text at bottom-right corner of the Unit.
	private void renderUnitText(Canvas layer, Unit unit)
	{

	}
	
	// TODO: Render the Movement Range and Attack Range Indicator Tiles.
	// rangeMap is centered on the Unit's Position. Convert to terrainMap coordinates with attackMapToTerrainMap() and movementMapToTerrainMap() in Unit.
	// No need to check if terrainMap coordinates out-of-range, already handled by GameMap.
	// rangeMap is true if that Terrain Tile is within range, false otherwise.
	// -----
	// Use setFill() to set the Indicator Tile fill color, and fillRect() to draw the Indicator Tile.
	// Use setStroke() to set the Indicator Tile solid outline color, and strokeRect() to draw the Indicator Tile solid outline.
	// Remember to call fillRect() before strokeRect(), so that the solid outline is drawn on top of the filled Indicator Tile.
	// Hint: For transparency effect, use Color.color(red, green, blue, transparency). [0, 1.0], zero is fully transparent, 1.0 is fully opaque/solid.
	// Hint: renderColor has getRed(), getGreen(), getBlue() methods.
	private void renderRangeIndicator(Canvas layer, Unit unit, boolean[][] rangeMap, Color renderColor)
	{

	}
	
	// TODO: Render the starting Terrain Map, Units, Unit ID at the beginning of GamePlay.
	// Also start the Water Tile Animations.
	// Remember to render to the correct layer in canvasGamePlayLayers.
	private void renderInitGamePlayCanvas() 
	{

	}
	
	// TODO: Create a new Thread for each Water Tile to manage the Water Animations.
	// You may use renderImageTile().
	// Remember to store each Thread in animThreads, so that we have a way to shutdown the Threads at the end of the game.
	// Although it is not necessary, you may create your own helper methods/classes for this.
	// Hint: Refer to Lab 12. Especially, remember to use Platform.runLater(). Otherwise, JavaFX will crash.
	// -----
	// Hint: A workaround for Compile Error "Local variable defined in an enclosing scope must be final or effectively final".
	// Create a final copy of the local variable in one scope-level outside.
	// Example:
	// for (int x = 0; x < gameMap.getWidth(); x++)
	// {
	// 		final int TERRAIN_MAP_X = x;
	//
	//		Thread waterAnimThread = new Thread(new Runnable()
	//		{
	//			private final int LOCATION_X = TERRAIN_MAP_X; // Using "LOCATION_X = x;" here will trigger the Compile Error mentioned above.
	//
	//			@Override
	//			public void run()
	//			{
	//				// Animation code here, refer to Lab 12.
	//			}
	//		});
	//		waterAnimThread.start();
	// }
	private void animateWaterTiles(Canvas layer)
	{

	}
	
	
	
	// =============================================================================
	// Section: Gameplay-related Methods (Mostly calls to GameEngine and/or GameMap)
	// =============================================================================
	
	private void processGamePlayWithoutUnitSelected(int terrainMapX, int terrainMapY) 
	{
		if (gameMap.getTerrainAtLocation(terrainMapX, terrainMapY).isOccupied())
		{
			Unit clickedUnit = gameMap.getTerrainAtLocation(terrainMapX, terrainMapY).getOccupyingUnit();
			lbGamePlayInfo.setText(clickedUnit.toString());
			
			// Check if this Unit can be selected for Movement. If yes, then checkPathfinding and renderMovementRangeIndicator.
			if (gameEngine.getCurrentPlayer().hasUnitWithId(clickedUnit.getId()) && clickedUnit.isReady())
			{
				gamePlaySelectedUnit = clickedUnit;
				gameMap.checkPathfinding(gamePlaySelectedUnit); 
				renderRangeIndicator(canvasGamePlayLayers[RANGE_INDICATOR_LAYER], gamePlaySelectedUnit, gamePlaySelectedUnit.getMovementMap(), MOVEMENT_RANGE_INDICATOR_COLOR);
			}
		}
	}

	private void processGamePlayWithUnitSelected(int terrainMapX, int terrainMapY) 
	{
		clearLayer(canvasGamePlayLayers[RANGE_INDICATOR_LAYER]); // Always clear previous Range Indicators first.
		
		// Attack Phase.
		if (isSelectedUnitAttackPhase)
		{
			int attackMapX = gamePlaySelectedUnit.terrainMapToAttackMapX(terrainMapX);
			int attackMapY = gamePlaySelectedUnit.terrainMapToAttackMapY(terrainMapY);
			
			// If user clicked an invalid Attack Target, skip this part and assume just End Turn without Attacking.
			if (gamePlaySelectedUnit.getAttackMap()[attackMapY][attackMapX] == true)
			{
				Terrain targetTile = gameMap.getTerrainAtLocation(terrainMapX, terrainMapY);
				
				if (targetTile.isOccupied())
				{
					Unit targetUnit = targetTile.getOccupyingUnit();
					
					if (gameEngine.isEnemyUnit(targetUnit))
					{
						gamePlaySelectedUnit.attackUnit(targetUnit);
						
						if (!gamePlaySelectedUnit.isAlive())
						{
							clearTile(canvasGamePlayLayers[UNIT_LAYER], gamePlaySelectedUnit.getLocationX(), gamePlaySelectedUnit.getLocationY());
							clearTile(canvasGamePlayLayers[TEXT_LAYER], gamePlaySelectedUnit.getLocationX(), gamePlaySelectedUnit.getLocationY());
						}
						
						if (!targetUnit.isAlive())
						{
							clearTile(canvasGamePlayLayers[UNIT_LAYER], targetUnit.getLocationX(), targetUnit.getLocationY());
							clearTile(canvasGamePlayLayers[TEXT_LAYER], targetUnit.getLocationX(), targetUnit.getLocationY());
						}
					}
				}
			}
			
			if (gamePlaySelectedUnit.isAlive())
			{
				gamePlaySelectedUnit.endTurn();
				renderUnitText(canvasGamePlayLayers[TEXT_LAYER], gamePlaySelectedUnit);
			}
			
			isSelectedUnitAttackPhase = false;
			updateListViewGamePlayUnitInfo();
			lbGamePlayInfo.setText(gamePlaySelectedUnit.toString());
			gamePlaySelectedUnit = null;
			
			checkPlayerTurn();
		}

		// Movement Phase.
		else
		{			
			int movementMapX = gamePlaySelectedUnit.terrainMapToMovementMapX(terrainMapX);
			int movementMapY = gamePlaySelectedUnit.terrainMapToMovementMapY(terrainMapY);
			
			if (gamePlaySelectedUnit.getMovementMap()[movementMapY][movementMapX] == true)
			{
				int unitPreviousLocationX = gamePlaySelectedUnit.getLocationX();
				int unitPreviousLocationY = gamePlaySelectedUnit.getLocationY();
				
				gamePlaySelectedUnit.move(terrainMapX, terrainMapY);
				
				clearTile(canvasGamePlayLayers[UNIT_LAYER], unitPreviousLocationX, unitPreviousLocationY);
				clearTile(canvasGamePlayLayers[TEXT_LAYER], unitPreviousLocationX, unitPreviousLocationY);
				renderUnit(canvasGamePlayLayers[UNIT_LAYER], gamePlaySelectedUnit);	
				renderUnitText(canvasGamePlayLayers[TEXT_LAYER], gamePlaySelectedUnit);
				
				if (gameEngine.isEnemyUnitInRange(gamePlaySelectedUnit))
				{
					isSelectedUnitAttackPhase = true;
					renderRangeIndicator(canvasGamePlayLayers[RANGE_INDICATOR_LAYER], gamePlaySelectedUnit, gamePlaySelectedUnit.getAttackMap(), ATTACK_RANGE_INDICATOR_COLOR);
				}
				else
				{
					gamePlaySelectedUnit.endTurn();
					updateListViewGamePlayUnitInfo();
					renderUnitText(canvasGamePlayLayers[TEXT_LAYER], gamePlaySelectedUnit);
					lbGamePlayInfo.setText(gamePlaySelectedUnit.toString());
					gamePlaySelectedUnit = null;
	
					checkPlayerTurn();
				}
			}
			
			else
			{
				lbGamePlayInfo.setText(gamePlaySelectedUnit.toString());
				gamePlaySelectedUnit = null; // Deselect the Unit if clicked on a Tile that can't Move to.
			}
		}
	}
	
	private void checkPlayerTurn()
	{
		if (gameEngine.isGameOver()) 
		{
			// Find out who is the Winner.
			updateGameOverLabel();
			
			// Stop GamePlay and cleanup.
			stopGamePlay();
			
			// Move to the Game Over screen.
			putSceneOnStage(SCENE_GAMEOVER);
			
			return;
		}
		
		if (!gameEngine.getCurrentPlayer().hasReadyUnits()) 
		{
			gameEngine.nextPlayerTurn();
			updateCurrentTurnLabel();
			updateListViewGamePlayUnitInfo();
			
			for (Unit unit:gameEngine.getCurrentPlayer().getUnits())
			{
				if (unit.isAlive() && unit.isReady())
				{
					renderUnitText(canvasGamePlayLayers[TEXT_LAYER], unit);
				}
			}
			
			return;
		}
	}
	
	private void stopGamePlay()
	{
		//System.out.println("Stopping Gameplay...");
		
		// Stop all Animation Threads.
		for (Thread animThread:animThreads)
		{
			animThread.interrupt();
		}
		animThreads.clear();
		
		// Clear all the Canvases.
		canvasGameStart.getGraphicsContext2D().clearRect(0, 0, canvasGameStart.getWidth(), canvasGameStart.getHeight());
		for (int i = 0; i < NUM_LAYERS; i++)
		{
			canvasGamePlayLayers[i].getGraphicsContext2D().clearRect(0, 0, canvasGamePlayLayers[i].getWidth(), canvasGamePlayLayers[i].getHeight());
		}
		
		// Clear GamePlay Info label.
		lbGamePlayInfo.setText("");
		
		// Clear GameEngine and GameMap.
		gameEngine.unloadPlayersAndUnits();
		gameMap.unloadTerrainMap();
	}
	
	
	
	//===========================================
	// Section: JavaFX Application Initialization
	//===========================================
	// Note: launch(args) will call init() and start(). Platform.exit() will call stop().
	
	private void initScenes() 
	{
		scenes[SCENE_WELCOME] = new Scene(paneWelcome(), 400, 500);
		scenes[SCENE_STARTGAME] = new Scene(paneStartGame(), RESOLUTION_TOTAL_WIDTH, RESOLUTION_TOTAL_HEIGHT);
		scenes[SCENE_GAMEPLAY] = new Scene(paneGamePlay(), RESOLUTION_TOTAL_WIDTH, RESOLUTION_TOTAL_HEIGHT);
		scenes[SCENE_GAMEOVER] = new Scene(paneGameOver(), RESOLUTION_TOTAL_WIDTH, RESOLUTION_TOTAL_HEIGHT);

		for (int i = 0; i < SCENE_NUM; i++)
		{
			scenes[i].getStylesheets().add("menu_and_css/styles.css"); // share stylesheet for all scenes
		}
	}

	private void putSceneOnStage(int sceneID) 
	{
		// ensure the sceneID is valid
		if (sceneID < 0 || sceneID >= SCENE_NUM)
		{
			return;
		}

		stage.hide();
		stage.setTitle(SCENE_TITLES[sceneID]);
		stage.setScene(scenes[sceneID]);
		stage.show();
	}

	@Override
	public void init()
	{
		initScenes();
		initEventHandlers();
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		stage = primaryStage;
		putSceneOnStage(SCENE_WELCOME);
	}

	@Override
	public void stop()
	{
		if (isBackgroundMusicEnabled)
		{
			backgroundMusic.stop();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
