package main.java;
import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.Input;
import java.io.FileNotFoundException;
import java.util.*;

import java.io.File;

class ShadowDefend extends AbstractGame {

    private Map map;
    private List<Point> polyline;

    private buyPanel buyPanel;
    private statusPanel statusPanel;

    private String status;
    private boolean waveStarted;
    private int currWaveCount;
    private int wavesFinished;
    private boolean itemSelected;
    private Tower selectedTower;
    private final Vector2 HORIZONTAL = new Vector2(1, 0);
    private final Vector2 VERTICAL = new Vector2(0, 1);
    private final int STARTING_LIVES = 25;
    private final int STARTING_FUNDS = 500;
    private final int STARTING_LEVEL = 1;
    private final double BASE_AMOUNT = 150;
    private final double MULT_FACTOR = 100;

    private int level;
    private Vector2 planeDir;

    private List<Wave> waveEvents;
    private List<Tower> activeTowers;
    private int totalWaves;

    public int playerFunds;

    public int lives;
    public static double timescaleMultiplier;
    public static Image buyPanelBackground;
    public static Image statusPanelBackground;
    public static Point buyPanelCenter;
    public static Point statusPanelCenter;

    public static double getTimescale() {
        return timescaleMultiplier;
    }


    /**
     * Reads in a file containing spawn and wave delay events
     * @param filename
     * @return list of spawn and delay wave events provided in the file
     */
    public List<Wave> readWaveEvents(String filename) {
        waveEvents = new ArrayList<>();
        try {
            File files = new File(filename);
            Scanner myReader = new Scanner(files);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataArr = data.split(",");
                int waveNo = Integer.parseInt(dataArr[0]);
                totalWaves = waveNo;
                // Creation of a new spawn event
                if (dataArr[1].equals("spawn")) {
                    int slicerCount = Integer.parseInt(dataArr[2]);
                    String slicerType = dataArr[3];
                    int delay = Integer.parseInt(dataArr[4]);
                    spawnWave spawnWave = new spawnWave(waveNo, slicerCount, slicerType, delay, polyline);
                    waveEvents.add(spawnWave);
                }
                // Creates a new delay event
                else {
                    int delay = Integer.parseInt(dataArr[2]);
                    delayWave delayWave = new delayWave(delay / timescaleMultiplier, waveNo);
                    waveEvents.add(delayWave);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file");
            e.printStackTrace();
        }
        return waveEvents;
    }

    /**
     * Switches the direction of the plane
     */
    public void switchPlaneDirVec() {
        if (planeDir.equals(HORIZONTAL)) {
            planeDir = VERTICAL;
        } else {
            planeDir = HORIZONTAL;
        }
    }

    /**
     * Returns whether a Tower can be released at the provided point
     * The sprite must not be on either of the panels, must not be on a blocked tile, and must not overlap
     * with another tower
     * @param point
     * @return  whether a Sprite (game object) can be released at the provided point
     */
    public boolean ValidReleasePoint(Point point) {
        return !buyPanel.inBoundingBoxRange(point) && !statusPanel.inBoundingBoxRange(point)
                && pointInMap(point) &&
                !map.getMap().hasProperty((int) point.x, (int) point.y, "blocked") &&
                !inTowerRange(point);
    }

    /**
     * Returns whether a point exists within the map
     * @param point
     * @return whether a point exists within the map
     */
    public boolean pointInMap(Point point) {
        return (point.x >= 0 && point.x<=Window.getWidth() && point.y >=0 && point.y<=Window.getHeight());
    }

    /**
     * Returns whether the user's hover point is intersecting with another tower
     * @param point
     * @return whether the user's hover point is intersecting
     */
    public boolean inTowerRange(Point point) {
        for (Tower tower: activeTowers) {
            if (tower.inBoundingBoxRange(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Performs the placement of a new tower clicked by the user
     * @param input
     */

    public void placeTowers(Input input) {
        //checks what happens if left clicked
        if (input.wasPressed(MouseButtons.LEFT)) {
            Point clickPoint = input.getMousePosition();
            //checks through each of the purchase items to see if user clicked them
            for (purchaseItem item : buyPanel.getPurchaseItems()) {
                if (item.getTower().inBoundingBoxRange(clickPoint)) {
                    if (item.getTower().getCost() <= playerFunds) {
                        itemSelected = true;
                        selectedTower = item.getTower().copy();
                    }
                }
            }
        }
        //checks whether item has been selected by the user, and if it has, updates where it will be placed
        if (itemSelected) {
            Point hoverPoint = input.getMousePosition();
            //Checks whether the item is hovering at a valid position (image rendered at user's mouse)
            if (ValidReleasePoint(hoverPoint)) {
                selectedTower.update(hoverPoint);
                if (input.wasReleased(MouseButtons.LEFT)) {
                    // Adds new general class attacker
                    Tower droppedTower = selectedTower.copy();
                    droppedTower.centerRectAt(hoverPoint);
                    // Checks whether the tower is an airplane and updates accordingly
                    if (droppedTower instanceof Airplane) {
                        Airplane airplane = (Airplane) droppedTower;
                        airplane.setDir(planeDir);
                        airplane.setSpawnPoint();
                        switchPlaneDirVec();
                    }
                    activeTowers.add(droppedTower);
                    // Return to default state
                    selectedTower = null;
                    itemSelected = false;

                    // Updates the player's funds
                    playerFunds -= droppedTower.getCost();
                }
            }
            // Cancels the selection process
            if (input.wasPressed(MouseButtons.RIGHT)) {
                selectedTower = null;
                itemSelected = false;
            }
        }
    }

    /**
     * Updates all of the waves currently in progress
     */
    public void updateWaves() {
        // Tracks the waves
        List<Wave> eventsToRemove = new ArrayList<>();
        for (int i = 0; i < waveEvents.size(); i++) {
            if (waveEvents.get(i).hasFinished()) {
                eventsToRemove.add(waveEvents.get(i));
                // Verifies whether its the last event in the wave
                if ((i + 1 == waveEvents.size() || waveEvents.get(i).getWaveNo() < waveEvents.get(i + 1).getWaveNo())) {
                    playerFunds += BASE_AMOUNT + waveEvents.get(i).getWaveNo() * MULT_FACTOR;
                    wavesFinished++;
                }
                // Checks whether a wave is in progress
            } else if (waveEvents.get(i).isHappening()) {
                // Checks whether a new wave is ready to begin
                if (waveEvents.get(i).getDuration() <= waveEvents.get(i).getCurrTime()) {
                    // Starts the next wave if enough time has passed
                    if (i + 1 != waveEvents.size() && !waveEvents.get(i + 1).isHappening()) {
                        waveEvents.get(i + 1).Start();
                        currWaveCount = waveEvents.get(i + 1).getWaveNo();
                    }
                }
                // Updates the present wave
                if (waveEvents.get(i) instanceof spawnWave) {
                    spawnWave wave = (spawnWave) waveEvents.get(i);
                    lives -= wave.getPenaltiesIncurred();
                    playerFunds += wave.getRewards();
                }
                waveEvents.get(i).Update();
            }
        }
        // Removes completed waves
        waveEvents.removeAll(eventsToRemove);
    }

    /**
     * Updates the towers that are currently in action
     */
    public void updateTowers() {
        List<Airplane> finishedAirplanes = new ArrayList<>();
        for (int i = 0; i < activeTowers.size(); i++) {
            Tower tower = activeTowers.get(i);
            if (tower instanceof Tank) {
                Tank tank = (Tank) tower;
                tank.identifyTarget(getActiveSlicers());
                tank.HitTarget();
            }
            else if (tower instanceof Airplane) {
                Airplane airplane = (Airplane) tower;
                airplane.dropExplosives();
                airplane.damageTargets(getActiveSlicers());
                if (airplane.isFinished()) {
                    finishedAirplanes.add(airplane);
                }
            }
            tower.updateAllProjectiles();
            tower.update();
        }
        activeTowers.removeAll(finishedAirplanes);
    }

    /**
     * Returns a list of all active slicers
     * @return a list of all active slicers
     */
    public List<Slicer> getActiveSlicers() {
        List<Slicer> activeSlicers = new ArrayList<>();
        for (int i = 0; i < waveEvents.size(); i++) {
            if (waveEvents.get(i) instanceof spawnWave) {
                spawnWave wave = (spawnWave) waveEvents.get(i);
                if (waveEvents.get(i).isHappening()) {
                    activeSlicers.addAll(wave.getActiveSlicers());
                }
            }
        }
        return activeSlicers;
    }

    /**
     * Updates the timescale multiplier from the user input
     * @param input
     */
    public void updateTimescale(Input input) {
        if (input.wasPressed(Keys.K) || input.wasPressed(Keys.L)) {
            if (input.wasPressed(Keys.L)) {
                timescaleMultiplier++;
            } else {
                if (timescaleMultiplier > 1) {
                    timescaleMultiplier--;
                }
            }
        }
    }

    /**
     * Initialises the game at the provided level (needed to change maps)
     * @param level
     */

    public void initialise(int level) {
        map = new Map(level);
        polyline = map.getPolylinePoints();
        currWaveCount = 1;
        timescaleMultiplier = 1;
        playerFunds = STARTING_FUNDS;
        waveEvents = readWaveEvents("res/levels/waves.txt");
        activeTowers = new ArrayList<>();
        lives = STARTING_LIVES;
        status = "Awaiting Start";
        planeDir = HORIZONTAL;
        buyPanelBackground = new Image("res/images/buypanel.png");
        statusPanelBackground = new Image("res/images/statuspanel.png");
        buyPanelCenter = new Point(buyPanelBackground.getWidth() / 2, buyPanelBackground.getHeight() / 2);
        statusPanelCenter = new Point(statusPanelBackground.getWidth() / 2,
                Window.getHeight() - statusPanelBackground.getHeight() / 2);
        buyPanel = new buyPanel(buyPanelBackground, buyPanelCenter);
        statusPanel = new statusPanel(statusPanelBackground, statusPanelCenter);
        wavesFinished = 0;
        waveStarted = false;
    }

    /**
     * Updates the status of the game (rendered on the status panel)
     */
    public void updateStatus() {
        if (lives > 0 && wavesFinished == totalWaves && getActiveSlicers().size()==0) {
            status = "Winner!";
            level++;
            initialise(level);
        }
        else if (lives <= 0) {
            Window.close();
        }
        else if (itemSelected) {
            status = "Placing";
        }
        else if (waveStarted) {
            status = "Wave in Progress";
        }
        else {
            status = "Awaiting Start";
        }
    }

    /**
     * Update method called every frame
     * @param input
     */
    @Override
    protected void update(Input input) {
        updateStatus();
        // Basic rendering of the game
        map.render();
        buyPanel.render(playerFunds);
        statusPanel.render(currWaveCount, status, lives);

        updateTimescale(input);

        if (input.wasPressed(Keys.S) && !waveStarted) {
            waveEvents.get(0).Start();
            waveStarted = true;
        }
        // Updates the current waves in progress and adds new waves if timing is appropriate
        updateWaves();

        // Updates the towers added by the player
        placeTowers(input);

        // Updates the towers currently active
        // Updates their movements
        updateTowers();
    }

    public ShadowDefend() {
        level = STARTING_LEVEL;
        initialise(level);
    }

    public static void main(String[] args) {
        new ShadowDefend().run();
    }
}
