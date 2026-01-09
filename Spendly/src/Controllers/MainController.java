package Controllers;
import Helpers.Coordinator;
import Helpers.MenuManager;
import Helpers.NotificationHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class MainController {

    @FXML private ButtonsController buttonsController;
    private MenuManager menuManager;
    private Coordinator coordinator;
    private NotificationHandler notificationHandler;

    // track which chart menu is visible or not
    public enum chartMenu { EARNINGSCHART, SPENDINGSCHART,
    } private chartMenu currentMenu;


    @FXML private HBox topLabelContainer;
    @FXML private Label topLabel;
    @FXML private AreaChart<String, Number> earningsChart;
    @FXML private AreaChart<String, Number> spendingsChart;

    // public "apis" used by menu Manager
    public void showTopLabelsContainer(){topLabelContainer.setVisible(true);}
    public void showEarningsChart(){earningsChart.setVisible(true);}
    public void hideEarningsChart(){earningsChart.setVisible(false);}
    public void showSpendingsChart(){spendingsChart.setVisible(true);}
    public void hideSpendingsChart(){spendingsChart.setVisible(false);}
    public Label accessTopLabel(){return topLabel;}

    public AreaChart<String, Number> chart;
    // getters for other controllers
    public AreaChart<String, Number> getEarningsChart() {
        return earningsChart; }

    public AreaChart<String, Number> getSpendingsChart() {
        return spendingsChart; }

    @FXML public void initialize() {
        buttonsController.setMainController(this); // allow buttons con to talk to main con

        coordinator = new Coordinator(); // create the helper
        buttonsController.setCoordinator(coordinator); // inj into buttons controller

        // create and inject menu manager
        menuManager = new MenuManager();
        menuManager.setMainController(this);
        menuManager.setButtonsController(buttonsController);
        buttonsController.setMenuManager(menuManager);

            // hide charts immediately on startup
            if (earningsChart != null) earningsChart.setVisible(false);
            if (spendingsChart != null) spendingsChart.setVisible(false);
            if (topLabelContainer != null) topLabelContainer.setVisible(false);

        // default menu
        menuManager.buttonVisibility(ButtonsController.Menu.MAIN);

        NotificationHandler.getInstance().setMainController(this);
    }
}