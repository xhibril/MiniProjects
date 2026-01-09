package Controllers;
import Helpers.Coordinator;
import Helpers.InputAmount;
import Helpers.MenuManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
public class ButtonsController {

    private static final String SPENDINGS_FILE = "Spendings.txt";
    private static final String EARNINGS_FILE = "Earnings.txt";

    private MainController mainController;
    public void setMainController(MainController main){
        this.mainController = main;
    }

    // link this controller to Coordinator
    private Coordinator communicator;
    public void setCoordinator(Coordinator comm) {
        this.communicator = comm;
    }

    private MenuManager menuManager; // injected by main
    public void setMenuManager(MenuManager menu) {
        this.menuManager = menu;
    }

    // handle user input for spendings / earnings
    InputAmount inputAmount = new InputAmount();

   public enum Menu {
        MAIN,
        addSpendingsOrEarnings,
        inputAmount,
        viewSpendingsOrEarnings,
        viewPeriods,
        scrollingMenu
    } private Menu currentMenu = Menu.MAIN;


    @FXML private VBox mainButtons;
    @FXML private VBox secondaryButtons;
    @FXML private TextField amountInput;
    @FXML private VBox amountInputContainer;
    @FXML private VBox viewButtons;
    @FXML private VBox viewCategoryContainer;
    @FXML private HBox backButtonContainer;
    @FXML private ScrollPane dailyScrollPane;
    @FXML private VBox dailyScrollContainer;
    @FXML private Button addBtn;
    @FXML private Button viewBtn;
    @FXML private Button earningsBtn;
    @FXML private Button spendingsBtn;
    @FXML private Button dailyViewBtn;
    @FXML private Button monthlyViewBtn;
    @FXML private Button yearlyViewBtn;
    @FXML private Button spendingsViewBtn;
    @FXML private Button earningsViewBtn;
    @FXML private Button backButton;

    // public "apis" for menu manager
    public void showMainButtons(){mainButtons.setVisible(true);}
    public void hideMainButtons(){mainButtons.setVisible(false);}
    public void showSecondaryButtons(){secondaryButtons.setVisible(true);}
    public void hideSecondaryButtons(){secondaryButtons.setVisible(false);}
    public void showAmountInputContainer(){amountInputContainer.setVisible(true);}
    public void hideAmountInputVBOX(){amountInputContainer.setVisible(false);}
    public void showViewButtons(){viewButtons.setVisible(true);}
    public void hideViewButtons(){viewButtons.setVisible(false);}
    public void showViewSpendOrEarnContainer(){viewCategoryContainer.setVisible(true);}
    public void hideViewSpendOrEarnContainer(){viewCategoryContainer.setVisible(false);}
    public void showBackButtonContainer(){backButtonContainer.setVisible(true);}
    public void hideBackButtonContainer(){backButtonContainer.setVisible(false);}
    public void showDailyScrollContainer(){dailyScrollContainer.setVisible(true);}
    public void hideDailyScrollContainer(){dailyScrollContainer.setVisible(false);}

    // "api" used by input amount helper
    public TextField getAmountInputField(){return amountInput;}

    @FXML
    public void initialize() {

        // connect helper classes and controllers
        inputAmount.setButtonsController(this);

        setAddBtn();
        setupAddEarningsButton();
        setupAddSpendingsButton();
        setupViewMenuButton();
        setDailyViewBtn();
        setMonthlyViewBtn();
        setYearlyViewBtn();
        setSpendingsViewBtn();
        setEarningsViewBtn();
        setBackButton();
    }

    // back button // navigation between menus
    private void setBackButton() {
        backButton.setOnAction(e -> {
            switch (currentMenu) {
                case addSpendingsOrEarnings -> currentMenu = Menu.MAIN;
                case inputAmount -> currentMenu = Menu.addSpendingsOrEarnings;
                case viewSpendingsOrEarnings -> currentMenu = Menu.MAIN;
                case viewPeriods -> currentMenu = Menu.viewSpendingsOrEarnings;
                case scrollingMenu -> currentMenu = Menu.viewPeriods;
            }
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // add spendings or earnings
    private void setAddBtn() {
        addBtn.setOnAction(e -> {
            currentMenu = Menu.addSpendingsOrEarnings;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // add spendings
    private void setupAddSpendingsButton() {
        spendingsBtn.setOnAction(e -> {
            amountInput.setOnAction(i -> inputAmount.getInput(SPENDINGS_FILE));
            currentMenu = Menu.inputAmount;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // add earnings
    private void setupAddEarningsButton() {
        earningsBtn.setOnAction(e -> {
            amountInput.setOnAction(i -> inputAmount.getInput(EARNINGS_FILE));
            currentMenu = Menu.inputAmount;
            menuManager.buttonVisibility(Menu.inputAmount);
        });
    }

    // view button for spendings / earnings
    private void setupViewMenuButton() {
        viewBtn.setOnAction(v -> {
            currentMenu = Menu.viewSpendingsOrEarnings;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // view button for spendings
    private void setSpendingsViewBtn() {
        spendingsViewBtn.setOnAction(v -> {
            communicator.setTransTypeSpendings();
            communicator.setChart(mainController.getSpendingsChart());
            menuManager.chartVisibility(MainController.chartMenu.SPENDINGSCHART);
            currentMenu = Menu.viewPeriods;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // view button for earnings
    private void setEarningsViewBtn() {
        earningsViewBtn.setOnAction(v -> {
            communicator.setTransTypeEarnings();
            communicator.setChart(mainController.getEarningsChart());
            menuManager.chartVisibility(MainController.chartMenu.EARNINGSCHART);
            currentMenu = Menu.viewPeriods;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // view daily spendings / earnings
    private void setDailyViewBtn() {
        dailyViewBtn.setOnAction(b -> {
            communicator.buttonTypeYears();
            generateYearButtonsForDaily();
            currentMenu = Menu.scrollingMenu;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // view monthly spendings / earnings
    private void setMonthlyViewBtn() {
        monthlyViewBtn.setOnAction(b -> {
            communicator.buttonTypeYears();
            generateYearButtonsForMonthly();
            currentMenu = Menu.scrollingMenu;
            menuManager.buttonVisibility(currentMenu);
        });
    }

    // view yearly spendings / earnings
    private void setYearlyViewBtn() {
        yearlyViewBtn.setOnAction(b -> {
            // clears leftover buttons from daily / monthly view (if there was any)
            if(box != null) {
                box.getChildren().clear();
            }

            communicator.setPeriodYearly();
            communicator.applyUserSelectionToChart();
            currentMenu = Menu.scrollingMenu;
            menuManager.buttonVisibility(currentMenu);
        });
    }

   // DYNAMIC MENU GENERATION

    // box used to hold generated buttons
     VBox box;

    // year buttons for daily (generation + pick by user)
    void generateYearButtonsForDaily(){
        box = createVBox();

        for (String btnClicked : getCurrentData()) {
            Button btn = new Button(btnClicked);
            btn.setOnAction(event -> {
                communicator.setSelectedYear(btnClicked);
                communicator.buttonTypeMonths();
                generateMonthButtonsForDaily();
            });
            box.getChildren().add(btn);
        }
        dailyScrollPane.setContent(box);
        dailyScrollPane.setFitToWidth(true);
    }

    // month buttons for daily (generation + pick by user)
    void generateMonthButtonsForDaily(){
        box = createVBox();

        for (String btnClicked : getCurrentData()) {
            Button btn = new Button(btnClicked);
            btn.setOnAction(event -> {
                communicator.setSelectedMonth(btnClicked);
                communicator.setPeriodDaily();
                communicator.applyUserSelectionToChart();
            });
            box.getChildren().add(btn);
        }
        dailyScrollPane.setContent(box);
        dailyScrollPane.setFitToWidth(true);
    }

    // year buttons for monthly (generation + pick by user)
    void generateYearButtonsForMonthly(){
        box = createVBox();

        for (String btnClicked : getCurrentData()) {
            Button btn = new Button(btnClicked);
            btn.setOnAction(event -> {
                communicator.setSelectedYear(btnClicked);
                communicator.setPeriodMonthly();
                communicator.applyUserSelectionToChart();
            });
            box.getChildren().add(btn);
        }
        dailyScrollPane.setContent(box);
        dailyScrollPane.setFitToWidth(true);

    }


    private ArrayList<String> getCurrentData(){
        return communicator.getData();
    }

    private VBox createVBox(){
        box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}






