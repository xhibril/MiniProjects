package Helpers;
import Controllers.ButtonsController;
import Controllers.MainController;

public class MenuManager {

    //ref to main and buttons con for ui updates
    private ButtonsController buttonsController;
    private MainController mainController;

    // give menu manager access to buttons con methods
    public void setButtonsController(ButtonsController buttonsController) {
        this.buttonsController = buttonsController;
    }
    // give menu manager access to main con methods
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void buttonVisibility(ButtonsController.Menu currentMenu) {
        buttonsController.hideSecondaryButtons();
        buttonsController.hideMainButtons();
        buttonsController.hideAmountInputVBOX();
        buttonsController.hideViewButtons();
        buttonsController.hideDailyScrollContainer();
        buttonsController.hideViewSpendOrEarnContainer();
        buttonsController.hideBackButtonContainer();
        switch (currentMenu) {
            case MAIN -> buttonsController.showMainButtons();
            case addSpendingsOrEarnings -> {
                buttonsController.showSecondaryButtons();
                buttonsController.showBackButtonContainer();
            }
            case inputAmount -> {
                buttonsController.showAmountInputContainer();
                buttonsController.showBackButtonContainer();
            }
            case viewSpendingsOrEarnings -> {
                buttonsController.showViewSpendOrEarnContainer();
                buttonsController.showBackButtonContainer();
            }
            case viewPeriods -> {
               buttonsController.showViewButtons();
                buttonsController.showBackButtonContainer();

            }
            case scrollingMenu -> {
                buttonsController.showDailyScrollContainer();
                buttonsController.showBackButtonContainer();
            }
        }
    }

    public void chartVisibility(MainController.chartMenu currentMenu) {

        switch (currentMenu) {
            case EARNINGSCHART ->{
                mainController.showEarningsChart();
                mainController.showTopLabelsContainer();

                mainController.chart = mainController.getEarningsChart();
                mainController.accessTopLabel().setStyle("-fx-text-fill: #A6FFB0; ");  // light green text
                mainController.accessTopLabel().setText("Earnings");
                mainController.hideSpendingsChart();
            }

            case SPENDINGSCHART ->{
                mainController.showSpendingsChart();
                mainController.showTopLabelsContainer();

                mainController.chart = mainController.getSpendingsChart();
                mainController.accessTopLabel().setStyle("-fx-text-fill: #FF7F7F"); // coral red text
                mainController.accessTopLabel().setText("Spendings");
                mainController.hideEarningsChart();
            }
        }
    }



}
