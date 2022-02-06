import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static java.awt.Color.WHITE;

public class Gui extends JFrame {
    private JXBrowser jxBrowser;
    private ResourceBundle messages;
    private JComboBox<Language> comboBox;
    private Container mainContainer;
    private CardLayout cl;

    private int selectedPanel;
    private ArrayList<Integer> previousPanelList = new ArrayList<Integer>();
    private int previousPanel;
    // because swing is retarded a copy needs to be made of locationB
    // inside of the GUI because we can't call other methods from the methods in here.
    private ArrayList<String> times;
    private LocalTime travelTime;
    private double distance = 0.0;
    private ArrayList<String> stopsTrajectory = new ArrayList<String>();
    private String locationA;
    private String locationB;
    private FunctionsToUiProvider functionsToUiProvider;
    private ArrayList<String> departureListCombobox = new ArrayList<String>();
    private ArrayList<String> arrivalListCombobox = new ArrayList<String>();


    private boolean isLogin = false;
    private String username;
    private int selectedLanguageOptionComboBox = 0;


    private Border emptyBorder = BorderFactory.createEmptyBorder();
    //colours
    private Color blue = new Color(32, 142, 188);
    private Color blueGreen = new Color(95, 177, 182);
    private Color green = new Color(140, 203, 177);
    private final Color lightGray = new Color(238, 238, 238);

    //for navigate gui
    private int selectedDepartureIndex = 0;
    private int selectedArrivalIndex = 0;
    private String vehicleIdentifier;

    public Gui() throws IOException {
        this.setSize(1200, 800);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon logo = new ImageIcon("src/resources/logo_small.png");
        this.setIconImage(logo.getImage());


        mainContainer = this.getContentPane();
        cl = new CardLayout();

        vehicleIdentifier = "train";
        times = new ArrayList<String>();

        String standardTime = "00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        travelTime = LocalTime.parse(standardTime, formatter);

        mainContainer.setLayout(cl);

        //panel
        //start panel 0 to load json files.
        selectedPanel = 0;

        this.functionsToUiProvider = new FunctionsToUiProvider();
        this.messages = functionsToUiProvider.messages;
        this.jxBrowser = functionsToUiProvider.jxBrowser;
        this.setTitle(messages.getString("Title"));


        //if the file for trajectorys doesn't exist than it gives a error.
        try {
            departureListCombobox = functionsToUiProvider.routeData.getPossibleDepartureStation(null);
            arrivalListCombobox = functionsToUiProvider.routeData.getPossibleArrivalStation(null);
        } catch (Exception e) {
        }

        previousPanel = selectedPanel;
        updatePanel();
    }

    public void updatePanel() {

        try {
            if (previousPanel != selectedPanel) {
                previousPanelList.add(previousPanel);
            }
        } catch (Exception e) {
            previousPanelList.add(selectedPanel);
        }

        previousPanel = selectedPanel;

        switch (selectedPanel) {
            case 0: {
                mainContainer.add(startUpAndErrorPanel(), "0");
                break;
            }

            case 1: {
                mainContainer.add(navigateGui(), "1");
                break;
            }
            case 2: {
                mainContainer.add(chosenTrajectoryInfoPanel(), "2");
                break;
            }
            case 3: {
                mainContainer.add(trackPanelGui(), "3");
                break;
            }

            case 4: {
                mainContainer.add(loginScreenGui(), "4");
                break;
            }

            case 5: {
                mainContainer.add(travelHistoryPanel(), "5");
                break;
            }

            case 7: {
                mainContainer.add(favoritePanelGui(), "7");
                break;
            }

            default: {
                System.out.println("exception error");
                break;
            }
        }
        cl.show(mainContainer, Integer.toString(selectedPanel));
    }

    public JPanel startUpAndErrorPanel() {
        JPanel panel = new JPanel(new GridLayout(30, 1));

        boolean profileExist = functionsToUiProvider.ifProfileJsonFileExists();
        boolean trajectoryExist = functionsToUiProvider.ifTrajectoryDataJsonFileExists();

        if (profileExist && trajectoryExist) {
            selectedPanel = 1;
            updatePanel();
            return panel;
        }

        if (profileExist == false) {
            panel.add(new JLabel("The file for the profiles don't exist"));
        }

        if (trajectoryExist == false) {
            panel.add(new JLabel("The file for the trajectorys don't exist"));
        }

        return panel;
    }

    private JPanel navigateGui() {
        JPanel navigatePanel = new JPanel();

        navigatePanel.setLayout(new BorderLayout());


        ////////////////////////////////
        ///         top       /////////
        ///////////////////////////////
        navigatePanel.add(loginFavoriteAndHistoryTopPanel(true), BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();


        //////////////////////
        ///     center      //
        //////////////////////
        JPanel center = new JPanel();


        center.setLayout(new GridLayout(3, 1));
        navigatePanel.add(center, BorderLayout.CENTER);

        JComboBox<String> departureComboBox = new JComboBox<>();
        for (String value : departureListCombobox) {
            departureComboBox.addItem(value);
        }
        JComboBox<String> arrivalComboBox = new JComboBox<>();
        for (String value : arrivalListCombobox) {
            arrivalComboBox.addItem(value);
        }
        try {
            arrivalComboBox.setSelectedIndex(selectedArrivalIndex);
            departureComboBox.setSelectedIndex(selectedDepartureIndex);
        } catch (Exception e) {
        }

        arrivalComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arrivalComboBox.getSelectedIndex() == 0) { // if the first selection is selected than we just fill in null so the list is complete
                    // bug fixing
                    departureListCombobox = functionsToUiProvider.routeDatagetPossibleDepartureStation(null);
                } else {
                    departureListCombobox = functionsToUiProvider.routeData.getPossibleDepartureStation(arrivalComboBox.getSelectedItem().toString());
                }

                selectedArrivalIndex = arrivalComboBox.getSelectedIndex();
                selectedDepartureIndex = departureComboBox.getSelectedIndex();
                functionsToUiProvider.routeData.setLocationB(arrivalComboBox.getSelectedItem().toString());


                updatePanel();
            }
        });


        departureComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (departureComboBox.getSelectedIndex() == 0) { // if the first selection is selected than we just fill in null so the list is complete
                    arrivalListCombobox = functionsToUiProvider.routeData.getPossibleArrivalStation(null);
                } else {
                    arrivalListCombobox = functionsToUiProvider.routeData.getPossibleArrivalStation(departureComboBox.getSelectedItem().toString());
                }
                selectedArrivalIndex = arrivalComboBox.getSelectedIndex();
                selectedDepartureIndex = departureComboBox.getSelectedIndex();

                functionsToUiProvider.routeData.setLocationA(departureComboBox.getSelectedItem().toString());
                updatePanel();
            }
        });


        JLabel fromLabel = new JLabel(messages.getString("Van"));
        JLabel toLabel = new JLabel(messages.getString("Naar"));


        //set size textfields
        departureComboBox.setPreferredSize(new Dimension(200, 20));
        arrivalComboBox.setPreferredSize(new Dimension(200, 20));


        //center textfield
        JPanel centerTextfields = new JPanel();
        centerTextfields.setLayout(new GridLayout(5, 1, 0, 0));

        JLabel wrongLocationA = new JLabel();
        JLabel wrongLocationB = new JLabel();

        centerTextfields.add(departureComboBox);
        centerTextfields.add(wrongLocationA);
        centerTextfields.add(arrivalComboBox);
        centerTextfields.add(wrongLocationB);


        //navigate
        JButton navigate = new JButton(messages.getString("Navigeren"));
        centerTextfields.add(navigate);
        navigate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                locationA = functionsToUiProvider.routeData.getLocationA();
                locationB = functionsToUiProvider.routeData.getLocationB();
                Trajectory fetchedTrajectory = functionsToUiProvider.fetchRightTrajectory();
                boolean valid = functionsToUiProvider.validateTrajectory(fetchedTrajectory);

                if (valid == true) {
                    functionsToUiProvider.routeData.setSelectedTrajectory(fetchedTrajectory);
                    functionsToUiProvider.routeData.setSelectedTrajectory(fetchedTrajectory);
                    functionsToUiProvider.routeData.setDistance(functionsToUiProvider.calcDistanceToStation());

                    functionsToUiProvider.routeData.resetTime();
                    functionsToUiProvider.routeData.addMinutesTime(functionsToUiProvider.calcMinutesToStation());
                    stopsTrajectory = functionsToUiProvider.generateRoute(functionsToUiProvider.routeData.getSelectedTrajectory());
                    functionsToUiProvider.routeData.setDistance(functionsToUiProvider.calcDistanceToStation());
                    //interfaceContainer.generateListDepartureTimes(interfaceContainer.routeData.getTime(), 20, fetchedTrajectory);
                    times = functionsToUiProvider.getArrivalAndDepartureTimes(
                            20,
                            functionsToUiProvider.routeData.getSelectedTrajectory(),
                            functionsToUiProvider.routeData.getLocationA(),
                            functionsToUiProvider.routeData.getLocationB());


                    distance = functionsToUiProvider.routeData.getDistance();
                    travelTime = functionsToUiProvider.routeData.getTime();

                    selectedPanel = 2;
                    updatePanel();
                } else {
                    wrongLocationB.setText("Geslecteerde route is fout");


                }

            }
        });


        //center labels
        JPanel centerLabels = new JPanel();
        centerLabels.setLayout(new GridLayout(3, 1, 0, 20));
        centerLabels.add(fromLabel);
        centerLabels.add(toLabel);
        centerLabels.add(new JLabel());

        JPanel centerGrid = new JPanel();
        centerGrid.setLayout(new GridBagLayout());
        centerGrid.add(centerLabels);
        centerGrid.add(centerTextfields);

        //radio buttons
        JRadioButton radioButtonBus = new JRadioButton(messages.getString("Bus"));
        JRadioButton radioButtonTrain = new JRadioButton(messages.getString("Trein"));
        switch (vehicleIdentifier) {
            case "bus": {
                radioButtonBus = new JRadioButton(messages.getString("Bus"), true);
                break;
            }
            case "trein":
            case "train": {
                radioButtonTrain = new JRadioButton(messages.getString("Trein"), true);
                break;
            }
            default: {
                System.out.println("Exception");
                break;
            }
        }

        radioButtonBus.setBounds(75, 50, 100, 30);
        radioButtonTrain.setBounds(75, 100, 100, 30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButtonBus);
        bg.add(radioButtonTrain);

        ActionListener sliceActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton radioButton = (AbstractButton) actionEvent.getSource();

                switch (radioButton.getText()) {
                    case "Bus": {
                        functionsToUiProvider.routeData.setVehicleIdentifier("bus");
                        vehicleIdentifier = "bus";
                        break;
                    }
                    case "Train":
                    case "Trein": {
                        functionsToUiProvider.routeData.setVehicleIdentifier("train");
                        vehicleIdentifier = "train";
                        break;
                    }
                    default: {
                        System.out.println("exception");
                        break;
                    }
                }
                functionsToUiProvider.routeData.getTrajectorysWithVehicleIdentifier();
                departureListCombobox = functionsToUiProvider.routeData.getPossibleDepartureStation(null);
                arrivalListCombobox = functionsToUiProvider.routeData.getPossibleArrivalStation(null);
                updatePanel();
            }
        };

        radioButtonBus.addActionListener(sliceActionListener);
        radioButtonTrain.addActionListener(sliceActionListener);


        JPanel radioButtons = new JPanel();
        radioButtons.setLayout(new FlowLayout());

        radioButtons.add(radioButtonBus);
        radioButtons.add(radioButtonTrain);

        radioButtons.setBackground(blue);
        radioButtonTrain.setBackground(blue);
        radioButtonBus.setBackground(blue);


        JPanel logoAndRadioButtons = new JPanel(new BorderLayout());

        //logo


        ImageIcon imageIcon = null;
        try {

            imageIcon = new ImageIcon(new ImageIcon("src/resources/logo.png").getImage().getScaledInstance(700, 166, Image.SCALE_DEFAULT));
        } catch (Exception e) {
        }

        JLabel picLabel = new JLabel(imageIcon);
        logoAndRadioButtons.add(picLabel);


        JPanel radioButtonsPanel = new JPanel();
        radioButtonsPanel.setBackground(blue);
        radioButtonsPanel.add(radioButtons);


        logoAndRadioButtons.add(radioButtonsPanel, BorderLayout.SOUTH);

        center.add(logoAndRadioButtons);


        center.add(centerGrid);

        ////////////////////////////////
        //   bottom language panel   //
        ////////////////////////////////
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridBagLayout());

        bottom.setLayout(new BorderLayout());


        navigatePanel.add(bottom, BorderLayout.SOUTH);

        bottom.add(languageAndGoBackPanel(false));


        return navigatePanel;

    }

    public JPanel routeInfoPanel() {
        int rowPanelRouteInfo = 16;
        JPanel panelRouteInfo = new JPanel(new GridLayout(rowPanelRouteInfo, 1));
        JPanel panelStationsInfo = new JPanel(new GridLayout(1, 1));
        panelRouteInfo.setBackground(lightGray);

        panelRouteInfo.add(new JLabel(messages.getString("Van_") + locationA));
        panelRouteInfo.add(new JLabel(messages.getString("Naar_") + locationB));
        long distanceRoundOff = Math.round(distance);


        panelRouteInfo.add(new JLabel((messages.getString("Afstand")) + Double.toString(distanceRoundOff) + "km"));
        panelRouteInfo.add(new JLabel((messages.getString("Reistijd")) + travelTime.toString()));

        if (vehicleIdentifier.equals("train")) {
            panelRouteInfo.add(new JLabel(messages.getString("Vervoer_type") + messages.getString("Trein")));
        } else {
            panelRouteInfo.add(new JLabel(messages.getString("Vervoer_type") + vehicleIdentifier.toString()));
        }

        return panelRouteInfo;
    }

    public JButton addToFavoriteButton(){

            JButton addToFavorite = new JButton(messages.getString("Voeg_toe_aan_favorieten"));
            addToFavorite.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    functionsToUiProvider.addFavoriteTravelSelectedProfile(locationA, locationB);
                    selectedPanel = 7;
                    updatePanel();
                }
            });

            return addToFavorite;
    }

    private JPanel chosenTrajectoryInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 6));

        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelNorth = new JPanel(new GridLayout());
        JPanel panelSouth = new JPanel(new GridLayout());

        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelNorth, BorderLayout.NORTH);
        panel.add(panelSouth, BorderLayout.SOUTH);

        JPanel panelInnerCenter = new JPanel(new GridLayout(1, 10));
        JPanel panelCenterNorth = new JPanel(new GridLayout(1, 2));

        panelCenter.add(panelInnerCenter, BorderLayout.CENTER);
        panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
        panelCenterNorth.setBackground(blue);

        panelCenterNorth.add(new JLabel(messages.getString("Reisinformatie")));
        panelCenterNorth.add(new JLabel(messages.getString("Reistijden")));

        JPanel panelRouteInfoAndStations = new JPanel(new GridLayout(2, 1));

        panelInnerCenter.add(panelRouteInfoAndStations);


        JPanel panelRouteInfo = routeInfoPanel();
        JPanel panelStationsInfo = new JPanel(new GridLayout(1, 1));

        panelRouteInfo.setBackground(lightGray);
        panelRouteInfoAndStations.add(panelRouteInfo);
        panelRouteInfoAndStations.add(panelStationsInfo);

        for (int i = 0; i < 16 - 7; i++) {
            panelRouteInfo.add(new JLabel());

        }

        JPanel mapAndFavorite = new JPanel(new GridLayout(1, 2));
        JButton showMap = new JButton(messages.getString("Navigeren"));

        mapAndFavorite.add(showMap);
        if (functionsToUiProvider.isLoggedIn()) {
            mapAndFavorite.add(addToFavoriteButton());
        }
        panelRouteInfo.add(mapAndFavorite);

        showMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (functionsToUiProvider.getSelectedProfile() != null) {
                    functionsToUiProvider.addTravelHistoryListSelectedProfile();

                }
                selectedPanel = 3;
                updatePanel();
            }
        });


        JPanel trajectoryInfoPanel = new JPanel(new BorderLayout());
        panelStationsInfo.add(trajectoryInfoPanel);

        JList trajectoryStationsJList = new JList(stopsTrajectory.toArray());
        JScrollPane stationsInfoPane = new JScrollPane();
        stationsInfoPane.setViewportView(trajectoryStationsJList);
        trajectoryStationsJList.setLayoutOrientation(JList.VERTICAL);
        trajectoryStationsJList.setBackground(lightGray);
        trajectoryInfoPanel.add(stationsInfoPane, BorderLayout.CENTER);
        trajectoryInfoPanel.add(new JLabel("Route"), BorderLayout.NORTH);
        trajectoryInfoPanel.setBackground(blue);


        JList timeJList = new JList(times.toArray());

        JScrollPane timesPane = new JScrollPane();
        timesPane.setViewportView(timeJList);
        timeJList.setLayoutOrientation(JList.VERTICAL);
        panelInnerCenter.add(timesPane);
        timeJList.setBackground(lightGray);




        ////////////////////////////////
        ///         top       /////////
        ///////////////////////////////
        panel.add(loginFavoriteAndHistoryTopPanel(true), BorderLayout.NORTH);


        /////////////////////////
        //      bottom      ////
        ////////////////////////

        panelSouth.add(languageAndGoBackPanel(true));
        return panel;
    }

    private JPanel trackPanelGui() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelCenter = new JPanel(new GridLayout(1, 2));
        JPanel panelSouth = new JPanel(new GridLayout());

        panel.add(loginFavoriteAndHistoryTopPanel(true), BorderLayout.NORTH); //north
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);


        ////////////////////////////////
        ///         Center      ////////
        ///////////////////////////////


        JPanel routeInfo = new JPanel(new GridLayout(2, 1));
        JPanel routeInfoBorderLayout = new JPanel(new BorderLayout());
        JPanel routeInfoPanel = routeInfoPanel();
        routeInfoBorderLayout.add(routeInfoPanel, BorderLayout.CENTER);

        if (functionsToUiProvider.isLoggedIn()) {
            routeInfoPanel.add(addToFavoriteButton());
        }
        routeInfoBorderLayout.add(new JLabel("Route Info"), BorderLayout.NORTH);

        panelCenter.add(jxBrowser.getBrowserView(), BorderLayout.CENTER);
        jxBrowser.loadUrl();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            jxBrowser.drawMap(locationA, locationB);
        } catch (Exception e) {

        }




        routeInfo.add(routeInfoBorderLayout);

        routeInfo.add(new JLabel());
        panelCenter.add(routeInfo);

        /////////////////////////
        //      bottom      ////
        ////////////////////////

        panelSouth.add(languageAndGoBackPanel(true));

        return panel;
    }

    private JPanel loginScreenGui() {

        JPanel mainPannel = new JPanel(new BorderLayout());

        ///////////////////////
        //      north       ///
        //////////////////////
        mainPannel.add(loginFavoriteAndHistoryTopPanel(true), BorderLayout.NORTH);


        ////////////////////////
        //      center       ///
        ////////////////////////
        JLabel userLabel = new JLabel(messages.getString("GEBRUIKERSNAAM"));
        JLabel passwordLabel = new JLabel(messages.getString("WATCHWOORD"));
        JTextField userTextField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("LOGIN");
        JButton resetButton = new JButton("RESET");
        JCheckBox showPassword = new JCheckBox(messages.getString("Laat_Paswoord_zien"));


        JPanel centerPanel = new JPanel(new GridBagLayout());
        mainPannel.add(centerPanel, BorderLayout.CENTER);


        userTextField.setPreferredSize(new Dimension(200, 20));
        passwordField.setPreferredSize(new Dimension(200, 20));

        JPanel centerPanelGridLayout = new JPanel(new GridLayout(3, 1));
        centerPanel.add(centerPanelGridLayout);


        JPanel loginTextFields = new JPanel(new GridLayout(3, 2));
        centerPanelGridLayout.add(loginTextFields);

        loginTextFields.add(userLabel);
        loginTextFields.add(userTextField);
        loginTextFields.add(passwordLabel);
        loginTextFields.add(passwordField);
        loginTextFields.add(new JLabel());
        loginTextFields.add(showPassword);
        JLabel loginIncorrect = new JLabel();

        centerPanelGridLayout.add(loginIncorrect);

        JPanel resetAndLoginButton = new JPanel(new GridBagLayout());
        centerPanelGridLayout.add(resetAndLoginButton);
        resetAndLoginButton.add(loginButton);
        JLabel emptyJLabel = new JLabel("bbbbbbbbbbbbb");
        emptyJLabel.setForeground(lightGray);
        resetAndLoginButton.add(emptyJLabel);

        resetAndLoginButton.add(resetButton);
        resetAndLoginButton.add(new JLabel());


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginButton) {
                    String userText;
                    String pwdText;
                    userText = userTextField.getText();
                    pwdText = passwordField.getText();

                    boolean profileExists = functionsToUiProvider.checkIfProfileExists(userText, pwdText);
                    if (profileExists == true) {
                        functionsToUiProvider.saveLoggedInProfile(userText, pwdText);
                        isLogin = true;
                        username = userText;
                        selectedPanel = 1;
                        updatePanel();
                    } else {
                        loginIncorrect.setText("Login incorrect");
                    }

                }
            }
        });

        showPassword.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               if (e.getSource() == showPassword) {
                                                   if (showPassword.isSelected()) {
                                                       passwordField.setEchoChar((char) 0);
                                                   } else {
                                                       passwordField.setEchoChar('*');
                                                   }


                                               }
                                           }
                                       }
        );

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == resetButton) {
                    userTextField.setText("");
                    passwordField.setText("");
                }
            }
        });


        ////////////////////////
        //      south       ///
        ////////////////////////

        mainPannel.add(languageAndGoBackPanel(true), BorderLayout.SOUTH);

        return mainPannel;


    }

    private JPanel travelHistoryPanel() {
        JPanel travelHistoryPanel = new JPanel(new BorderLayout());
        travelHistoryPanel.setBackground(lightGray);


        if (functionsToUiProvider.isLoggedIn() == true){

            System.out.println("loggedin");
            ArrayList<String> travelHistory = functionsToUiProvider.getTravelHistoryListSelectedProfile();

            if (travelHistory.isEmpty()){
                travelHistoryPanel.add(new JLabel("No travel History, please navigate with the application."));

            } else {
                JList travelHistoryJList = new JList(travelHistory.toArray());
                travelHistoryJList.setBackground(lightGray);

                JScrollPane travelHistoryPane = new JScrollPane();
                travelHistoryPane.setViewportView(travelHistoryJList);
                travelHistoryJList.setLayoutOrientation(JList.VERTICAL);
                travelHistoryPanel.add(travelHistoryPane, BorderLayout.CENTER);
            }
        } else{
            travelHistoryPanel.add(new JLabel("No travel History, please login with your account to view your travel history."));

        }



        ////////////////////////////////
        ///         top       /////////
        ///////////////////////////////


        travelHistoryPanel.add(loginFavoriteAndHistoryTopPanel(true), BorderLayout.NORTH);


        /////////////////////////
        //      bottom      ////
        ////////////////////////

        travelHistoryPanel.add(languageAndGoBackPanel(true), BorderLayout.SOUTH);

        return travelHistoryPanel;
    }

    private JPanel loginFavoriteAndHistoryTopPanel(boolean loginButton) {

        int colums = 3;
        JPanel topPanel = new JPanel(new GridLayout(1, colums));
        topPanel.setBackground(blue);

        if (isLogin == false) {
            if (loginButton == true) {
                JButton login = new JButton(("Login"));
                login.setBorderPainted(false);


                login.setBackground(blue);
                topPanel.add(login);
                topPanel.add(new JLabel());

                login.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        selectedPanel = 4;
                        updatePanel();
                    }
                });

            } else {
                topPanel.add(new JLabel());
            }
        } else {
            topPanel.add(new JLabel(messages.getString("Gebruiker")));
            topPanel.add(new JLabel(username));
        }


        JButton travelHistoryButton = new JButton(messages.getString("Reisgeschiedenis"));
        JButton favorite = new JButton(messages.getString("Reisfavorieten"));
        travelHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPanel = 5;
                updatePanel();
            }
        });

        favorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPanel = 7;
                updatePanel();
            }
        });
        favorite.setBackground(blue);
        travelHistoryButton.setBackground(blue);

        topPanel.add(travelHistoryButton);
        topPanel.add(favorite);


        return topPanel;


    }

    private JPanel languageAndGoBackPanel(boolean goBack) {
        JPanel languagePanel = new JPanel(new GridLayout(1, 4));
        languagePanel.setBackground(blue);

        //add empty spaces to bottom grid
        if (goBack == true) {
            JButton goBackButton = new JButton(messages.getString("Vorige"));
            goBackButton.setBorderPainted(false);
            goBackButton.setBackground(blue);
            languagePanel.add(goBackButton);
            goBackButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        selectedPanel = previousPanelList.get(previousPanelList.size() - 1);
                        previousPanelList.remove(previousPanelList.size() - 1);
                        updatePanel();
                        previousPanelList.remove(previousPanelList.size() - 1);
                    } catch (Exception b) {
                    }
                }
            });
        } else {
            languagePanel.add(new JLabel());
        }


        languagePanel.add(new JLabel());


        //languagePanel label
        JPanel languageLabel = new JPanel(new GridLayout(1, 2));
        languageLabel.setBackground(blue);

        languagePanel.add(languageLabel);
        languageLabel.add(new JLabel());

        languageLabel.add(new JLabel(messages.getString("Taal")));

        comboBox = new JComboBox<Language>();
        comboBox.setModel(new DefaultComboBoxModel<Language>(Language.values()));

        if ((messages.getString("taal").equals("NE"))) {
            selectedLanguageOptionComboBox = 1;
            comboBox.setSelectedIndex(0);
        } else {
            selectedLanguageOptionComboBox = 0;
            comboBox.setSelectedIndex(1);
        }

        languagePanel.add(comboBox);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeLanguage(((JComboBox) e.getSource()).getSelectedIndex());
            }
        });

        {
            return languagePanel;
        }
    }

    private JPanel favoritePanelGui() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelCenter = new JPanel(new GridLayout(1, 2));
        JPanel panelSouth = new JPanel(new GridLayout());

        panelCenter.setBackground(lightGray);

        panel.add(loginFavoriteAndHistoryTopPanel(true), BorderLayout.NORTH); //north
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);


        ////////////////////////////////
        ///         Center      ////////
        ///////////////////////////////


        ///////////////////////////
        ///     CENTER LEFT     //
        //////////////////////////


        JPanel favorites = new JPanel(new GridLayout());
        panelCenter.add(favorites);

        if (functionsToUiProvider.isLoggedIn() == true) {
            ArrayList<String> favoritesArrayList = functionsToUiProvider.getFavoriteTravelsListSelectedProfile();

            if (favoritesArrayList.isEmpty()) {
                favorites.add(new JLabel("No favorite travels to view, please add a favorite to your travels."));
            } else {

                JList favoritesList = new JList(functionsToUiProvider.getFavoriteTravelsListSelectedProfile().toArray());
                favoritesList.setBackground(lightGray);

                favoritesList.addListSelectionListener(new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent args0) {
                        if (functionsToUiProvider.getSelectedProfile() != null) {
                            int selectedIndex = favoritesList.getSelectedIndex();
                            String favoriteLocationA = functionsToUiProvider.fetchFavoriteLocationA(selectedIndex);
                            String favoriteLocationB = functionsToUiProvider.fetchFavoriteLocationB(selectedIndex);

                            System.out.println(favoriteLocationA + "xxxxxxxxxxxxxxxxxx" + favoriteLocationB);

                            boolean isAdjusting = args0.getValueIsAdjusting();
                            if (isAdjusting == false) {
                                try {
                                    TimeUnit.MILLISECONDS.sleep(100);
                                    jxBrowser.drawMap(favoriteLocationA, favoriteLocationB);
                                } catch (Exception e) {

                                }
                            }


                        }

                    }
                });


                JScrollPane favoritesPane = new JScrollPane();

                favoritesPane.setViewportView(favoritesList);
                favoritesList.setLayoutOrientation(JList.VERTICAL);
                favorites.add(favoritesPane);
                favoritesList.setBackground(WHITE);
            }

        } else {
            favorites.add(new JLabel("No travel favorite travels, please login and add a travel to your favorite travels."));
        }

        ///////////////////////////
        ///     CENTER RIGHT     //
        //////////////////////////

        JPanel centerPanelRight = new JPanel(new GridBagLayout());
        panelCenter.add(jxBrowser.getBrowserView(), BorderLayout.CENTER);
        jxBrowser.loadUrl();


        /////////////////////////
        //      bottom      ////
        ////////////////////////

        panelSouth.add(languageAndGoBackPanel(true));

        return panel;
    }

    private void changeLanguage(int index) {

        Language language = comboBox.getItemAt(index);

        getBundle(language);

        updatePanel();
    }

    private ResourceBundle getBundle(Language language) {
        //ResourceBundle messages;
        if (language == Language.English)
            messages = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "EN"));
        else
            messages = ResourceBundle.getBundle("MessagesBundle");
        return messages;
    }


}


