package assignment3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

public class DViz extends DogShelter {                         //TODO: Cleanup console outputs
    //TODO: Implement cost at the top, autotesters and the other useful methods!
    //User Facing Parameters
    protected int wideningCoeff = 2;
    //Panels
    private JPanel GraphRegion;                             //JPanel on which the tree is drawn
    private JPanel MainWindow;                              //The entire window
    private JPanel MainPanel;                               //Right side of the interface with the tree and controls
    private JPanel ListOfDogsSide;                          //Left side of the interface with the list of dogs
    //Scroll panes
    private JScrollPane dogScroller;                        //Scroll pane around list of dogs
    private JScrollPane GraphScroller;                      //Scroll pane around graph area
    //Interactive elements
    private JSlider RandomnessSlider;
    private JSlider WideningSlider;
    private JButton AddRandomDog;
    private JButton AddCustom;
    private JButton forceRefreshButton;
    private JButton RemoveRandom;                           //TODO: Implement this
    private JButton stressTestButton;
    private JSlider testIntensitySlider;
    private JCheckBox drawToStringCheckBox;
    private JCheckBox drawDaysShelteredCheckBox;
    private JButton gradualTestButton;
    private JLabel VetScheduleDisplay;
    private JLabel DogsInTreeDisplay;
    private JLabel OldestDisplay;
    /*
    private JSpinner FluffiestFromMonthSpinner;
    private JLabel FluffiestFromMonthDisplay;
     */
    private JSlider CostPlanningSlider;
    private JButton spamAddButton;
    private JButton spamRemoveButton;
    private JSlider SpamFactorSlider;
    private JLabel SpamFactorDisplay;
    private JLabel TestIntensityDisplay;
    private JLabel Status;
    private JCheckBox drawSubtreesCheckBox;
    protected int randomnessCoeff = 2;
    //Randomization engine for assignment related objects
    private RandomDogs rand = new RandomDogs();             //Random dog generator

    //======================= User facing methods & constructors =========================

    /**
     * Instantiates a DViz object. Functional as a drop in replacement for a DogShelter object.
     * <p>
     * Builds GUI and generates DogShelter
     *
     * @param d any Dog to instantiate the root node.
     */
    public DViz(Dog d) {
        super(d);                                           //Calls constructor of superclass
        $$$setupUI$$$();
        addListeners();                                     //Binds listeners for UI
        refresh();                                          //Draws the graphics elements
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DViz");
        RandomDogs statRand = new RandomDogs();
        frame.setContentPane(statRand.nextDViz().MainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    //======================= UI Generation Methods =========================

    private void createUIComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ListOfDogsSide = new JPanel();
        ListOfDogsSide.setLayout(new BoxLayout(ListOfDogsSide, BoxLayout.Y_AXIS));
        GraphRegion = new JPanel();
        GraphRegion.add(new GraphZone());
        //GraphRegion.add(new ShapesJPanel());
        rand = new RandomDogs();
        //ListOfCatsSide.add(new CatNodeDrawing(rand.nextCatNode()));
        refresh();
    }

    // Arrays aren't used in this assignment
    /*
    private int sum(int[] input) {
        int output = 0;
        for (int i : input) {
            output += i;
        }
        return output;
    }
    */

    private void displayNumbers() {
        try {
            TestIntensityDisplay.setText(testIntensitySlider.getValue() + "");
            SpamFactorDisplay.setText(SpamFactorSlider.getValue() + "");
        } catch (Exception e) {
            TestIntensityDisplay.setText("NaN");
            SpamFactorDisplay.setText("NaN");
        }
        try {
            VetScheduleDisplay.setText(budgetVetExpenses(CostPlanningSlider.getValue()) + " over " + CostPlanningSlider.getValue() + " days.");
            VetScheduleDisplay.setToolTipText("Hi! I'm a tooltip!");
            CostPlanningSlider.setToolTipText(budgetVetExpenses(CostPlanningSlider.getValue()) + " over " + CostPlanningSlider.getValue() + " days.");
        } catch (Exception e) {
            VetScheduleDisplay.setText("0");
        }
        try {
            OldestDisplay.setText(Integer.toString(findOldest().getAge()));
        } catch (Exception e) {
            OldestDisplay.setText("Error");
        }
        /*
        try {
            FluffiestFromMonthDisplay.setText(fluffiestFromMonth((int) FluffiestFromMonthSpinner.getValue()).name + " with fur " + fluffiestFromMonth((int) FluffiestFromMonthSpinner.getValue()).furThickness);
        } catch (Exception e) {
            FluffiestFromMonthDisplay.setText("N/A");
        }
         */
        try {
            ArrayList<Dog> list = new ArrayList<>();

            for (Dog dog : this) {
                list.add(dog);
            }

            int numberOfNodes = list.size();
            DogsInTreeDisplay.setText(Integer.toString(numberOfNodes));
        } catch (Exception e) {
            DogsInTreeDisplay.setText("Error");
        }
    }

    // This year's Assignment 3 has no arrays to deal with.
    /*
    private String displaySumArray(int[] input) {
        return Integer.toString(sum(input));
    }

    private String displayArrayNoZero(int[] input) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            if (input[i] != 0) {
                s.append("at month ");
                s.append(i);
                s.append(" cost was ");
                s.append(input[i]);
                s.append(", ");
            }
        }
        s.replace(s.length() - 2, s.length(), "");
        return s.toString();
    }

    private String displayArray(int[] input) {
        StringBuilder s = new StringBuilder();
        for (int i : input) {
            s.append(i);
            s.append(", ");
        }
        s.replace(s.length() - 2, s.length(), "");
        return s.toString();
    }
     */

    private void updateList() {
        Stack<DogNode> s = new Stack<>();
        DogNode curr = root;
        ListOfDogsSide.removeAll();


        // traverse the tree for side list inOrder
        while (curr != null) {
            // .same isn't a thing in this assignment either.
            /*
            try {
                if (curr.same != null) {
                    CatNode temp = curr.same;
                    while (temp != null) {
                        //s.push(temp);
                        try {
                            temp = temp.same;
                        } catch (NullPointerException e) {//TODO: is this even necessary
                            //showUser("    [DViz / Debug] " + "is this even necessary?");  //TODO: Cleanup console output
                        }
                    }
                }
            } catch (NullPointerException e) {
            }
             */

            while (curr != null) {
                s.push(curr);
                curr = curr.older;
            }
            curr = s.pop();
            //output.add(curr);
            ListOfDogsSide.add(new DogNodeDrawing(curr).panel);

            //System.out.print(curr.data + " ");

            curr = curr.younger;
        }
    }

    private void refresh() {
        int GraphX = 0;
        int GraphY = 0;
        try {
            GraphX = GraphScroller.getHorizontalScrollBar().getValue();
            GraphY = GraphScroller.getVerticalScrollBar().getValue();
        } catch (Exception ignored) {
        }
        int ListY = 0;
        try {
            ListY = dogScroller.getVerticalScrollBar().getValue();
        } catch (Exception ignored) {
        }
        this.GraphRegion.updateUI();
        updateList();
        GraphRegion.repaint();
        ListOfDogsSide.repaint();
        ListOfDogsSide.revalidate();
        try {
            displayNumbers();
        } catch (Exception ignored) {
        }
        try {
            dogScroller.getVerticalScrollBar().setValue(ListY);
        } catch (Exception ignored) {
        }
        try {
            GraphScroller.getVerticalScrollBar().setValue(GraphY);
            GraphScroller.getHorizontalScrollBar().setValue(GraphX);
        } catch (Exception ignored) {
        }
    }

    private void showUser(String s) {
        System.out.println(s);
        Status.setText(s);
        refresh();
    }

    //======================= UI Listener Methods =========================

    private void addListeners() {
        GraphScroller.getVerticalScrollBar().addAdjustmentListener(e -> GraphRegion.repaint());
        GraphScroller.getHorizontalScrollBar().addAdjustmentListener(e -> GraphRegion.repaint());
        dogScroller.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (drawSubtreesCheckBox.isSelected()) {
                ListOfDogsSide.repaint();
            }
        });
        dogScroller.addPropertyChangeListener(evt -> refresh());
        drawSubtreesCheckBox.addActionListener(e -> refresh());
        SpamFactorSlider.addChangeListener(e -> refresh());
        CostPlanningSlider.addChangeListener(e -> refresh());
        testIntensitySlider.addChangeListener(e -> refresh());
        spamRemoveButton.addActionListener(e -> {
            removeDogs(SpamFactorSlider.getValue());
            showUser("    [DViz / SpamRemove] Ran for " + SpamFactorSlider.getValue() + " iterations. Check Console for details.");
            refresh();
        });
        spamAddButton.addActionListener(e -> {
            showUser("    [DViz / SpamAdd] This may take a while.");
            int counter = 0;
            if (root == null) {
                root = rand.nextDogNode();
                counter++;
            }
            while (counter < SpamFactorSlider.getValue()) {
                root.shelter(rand.nextDog());
                counter++;
            }
            showUser("    [DViz / SpamAdd] Ran for " + SpamFactorSlider.getValue() + " iterations. Check Console for details.");
            refresh();
        });
        CostPlanningSlider.addPropertyChangeListener(evt -> refresh());
        gradualTestButton.addActionListener(e -> {
            showUser(("    [DViz / Utility] " + gradualTest(testIntensitySlider.getValue())));
            StressTest dialog = new StressTest();
            dialog.pack();
            dialog.setVisible(true);
        });
        drawDaysShelteredCheckBox.addPropertyChangeListener(evt -> refresh());
        drawToStringCheckBox.addPropertyChangeListener(evt -> refresh());
        forceRefreshButton.addActionListener(e -> refresh());
        RemoveRandom.addActionListener(e -> showUser("    [DViz / Utility] " + removeRandom()));
        stressTestButton.addActionListener(e -> {
            int iter = 0;
            int max = testIntensitySlider.getValue();
            while (iter < max) {
                iter++;
                try {
                    showUser("    [DViz / Utility] " + addDogs());
                } catch (NullPointerException ex) {
                    showUser("    [DViz / Caught Runtime Exception] NullPointerException");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    showUser("    [DViz / Caught Runtime Exception] " + ex.getMessage());
                }
                try {
                    showUser("    [DViz / Utility] " + removeDogs());
                } catch (NullPointerException ex) {
                    showUser("    [DViz / Caught Runtime Exception] NullPointerException");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    showUser("    [DViz / Caught Runtime Exception] " + ex.getMessage());
                }
            }
            StressTest dialog = new StressTest();
            dialog.pack();
            dialog.setVisible(true);
        });
        RandomnessSlider.addChangeListener(e -> randomnessSliderChanged());
        WideningSlider.addChangeListener(e -> wideningSliderChanged());

        AddRandomDog.addActionListener(e -> {
            Dog toAdd = rand.nextDog();
            showUser("    [DViz / AddRandomDog] Adding dog " + toAdd.toString());
            shelter(rand.nextDog());
        });

        AddCustom.addActionListener(e -> {
            AddCustomDog dialog = new AddCustomDog();
            dialog.pack();
            dialog.setVisible(true);
            showUser("    [DViz / AddCustomDog] User prompted for dog details.");
            while (dialog.info == null) {
                if (dialog.cancelled) {
                    showUser("    [DViz / AddCustom] Aborted.");
                    break;
                }
            }
            if (!dialog.cancelled) {
                showUser("    [DViz / AddCustomCat] Adding dog " + dialog.info);
                shelter(dialog.info);
            }
        });
    }

    private void wideningSliderChanged() {
        this.wideningCoeff = WideningSlider.getValue();
        refresh();
    }

    private void randomnessSliderChanged() {
        this.randomnessCoeff = RandomnessSlider.getValue();
        refresh();
    }

    //======================== Auto Tester Methods =======================
    private String removeRandom() {
        DogNode root = this.root;
        DogNode victim;
        ArrayList<Dog> list = new ArrayList<>();
        // Enhanced for loops are cooler
        for (Dog dog : this) {
            list.add(dog);
        }
        int numberOfNodesBefore = list.size();
        int whichToRemove = 0;
        if (list.size() > 0) {
            whichToRemove = rand.nextInt(list.size());
            victim = (this.findDog(root, list.get(whichToRemove)));
        } else {
            return "No nodes to remove from";
        }
        adopt(list.get(whichToRemove));
        list.remove(whichToRemove);
        // Enhanced for loops are cooler
        int numberOfNodesAfter = 0;
        for (Dog dog : this) {
            numberOfNodesAfter++;
        }
        String dogEssentials;
        try {
            dogEssentials = victim.d.toString();
        } catch (Exception e) {
            dogEssentials = "name not found";
        }
        if (numberOfNodesAfter != numberOfNodesBefore - 1 && numberOfNodesAfter != numberOfNodesBefore) {
            String dogString;
            try {
                StringBuilder sb = new StringBuilder();
                if (isLeaf(victim)) sb.append(" a leaf node");
                else if (victim.older == null && victim.younger == null) {
                    sb.append(", had two children");
                } else if (victim.older != null) {
                    sb.append(", had an older");
                } else {
                    sb.append(", had a younger");
                }
                if (victim.d.equals(root.d)) {
                    sb.append(", was the same as root");
                } else if (victim.d.getAge() > root.d.getAge()) {
                    sb.append(", was younger to root");
                } else if (victim.d.getAge() < root.d.getAge()) {
                    sb.append(", was senior to root");
                } else {
                    sb.append(", was same as root (sorry the randomizer is broken)");
                }

                dogString = sb.toString();

            } catch (Exception e) {
                dogString = "not able to be found in tree before remove";
            }
            return "Remove error? Number of dogs before remove was " + numberOfNodesBefore + " after remove was " + numberOfNodesAfter + " dog removed was " + dogEssentials + dogString;
        } else if (numberOfNodesAfter == numberOfNodesBefore) {
            String dogString;
            try {
                StringBuilder sb = new StringBuilder();
                if (isLeaf(victim)) sb.append(" a leaf node");
                else if (victim.older == null && victim.younger == null) {
                    sb.append(", had two children");
                } else if (victim.older != null) {
                    sb.append(", had a senior");
                } else {
                    sb.append(", had a junior");
                }
                if (victim.d.equals(root.d)) {
                    sb.append(", was the same as root");
                } else if (victim.d.getAge() > root.d.getAge()) {
                    sb.append(", was younger to root");
                } else if (victim.d.getAge() < root.d.getAge()) {
                    sb.append(", was senior to root");
                } else {
                    sb.append(", was same as root (sorry the randomizer is broken)");
                }

                dogString = sb.toString();

            } catch (Exception e) {
                dogString = "not able to be found in tree before remove";
            }
            return "The dog " + dogEssentials + dogString + " somehow evaded removal.";
        } else {
            return "Remove successful according to number of nodes. May " + dogEssentials + " rest in peace.";
        }
    }

    private String removeDogs(int toExtermiante) {
        showUser("    [DViz / SpamRemove] This may take a while.");
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        while (counter < toExtermiante) {
            //sb.append(this.removeRandom());
            showUser("    [DViz / SpamRemove] " + this.removeRandom());
            refresh();
            counter++;
        }
        return sb.toString();
    }

    public void delay(long pauseTimeMillis) {
    /*This function pauses code execution for an input amount of time in milliseconds.
    It exists only because delay() isn't a thing in java. */
        try {
            Thread.sleep(pauseTimeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String gradualTest(int maximum) {
        StringBuilder output = new StringBuilder("    [DViz / GradualTest] Test beginning with " + maximum + " stages." + "\n");
        root = rand.nextDogNode();
        for (int i = 1; i <= maximum; i++) {
            output.append("    [DViz / GradualTest] Reached stage ").append(i).append("\n");
            for (int j = 1; j <= i; j++) {
                shelter(rand.nextDog());
                /*if (maximum < 10) {
                    delay(150);
                }*/
                this.refresh();
            }
            for (int j = 1; j <= i; j++) {
                try {
                    output.append("    [DViz / GradualTest] ").append(removeRandom()).append("\n");
                } catch (Exception e) {
                    output.append("    [DViz / GradualTest / Caught Runtime Exception] ").append(e.getMessage()).append("\n");
                    e.printStackTrace();
                }
                /*if (maximum < 10) {
                    delay(150);
                }*/
                this.refresh();
            }
            root = rand.nextDogNode();
        }
        output.append("    [DViz / GradualTest] Test concluded.");
        return output.toString();
    }

    private String removeDogs() {
        ArrayList<DogNode> theDeparted = new ArrayList<>();
        ArrayList<Dog> list = new ArrayList<>();
        // Please Sasha please use enhanced for loops
        for (Dog dog : this) {
            list.add(dog);
        }
        int numberOfNodesBefore = list.size();
        int toExtermiante = rand.nextInt(list.size());
        int numberRemoved = 0;
        int whichToRemove;
        while (list.size() != numberOfNodesBefore - toExtermiante) {
            whichToRemove = rand.nextInt(list.size());
            theDeparted.add(this.findDog(root, list.get(whichToRemove)));
            adopt(list.get(whichToRemove));
        }
        int numberOfNodesAfter = 0;
        for (Dog dog : this) { // I am BEGGING you
            numberOfNodesAfter++;
        }
        if (numberOfNodesAfter != numberOfNodesBefore - toExtermiante) {
            return "Remove error? Number of dogs before remove was " + numberOfNodesBefore + " after remove was " + numberOfNodesAfter + " DogNodes to remove were multiple";
        } else {
            return "Remove successful according to number of nodes.";
        }
    }

    private boolean isLeaf(DogNode d) {
        return d.younger == null && d.older == null;
    }

    private DogNode findDog(DogNode iter, Dog dog) {
        if (iter == null || dog == null) {
            return null;
        }
        if (iter.d.equals(dog)) {
            return iter;
        }
        // Traverse by, uh, age? yeah this is just the BST part of the treap
        if (iter.d.getAge() > dog.getAge()) {
            return findDog(iter.younger, dog);
        } else if (iter.d.getAge() < dog.getAge()) {
            return findDog(iter.older, dog);
        } else {
            return null;
        }
    }

    private String removeAllDogs() {
        ArrayList<Dog> list = new ArrayList<>();
        for (Dog dog : this) {
            list.add(dog);
        }
        int numberOfNodesBefore = list.size();
        int toExterminate = list.size() - 1;
        int numberRemoved = 0;
        int whichToRemove;
        while (list.size() != numberOfNodesBefore - toExterminate) {
            whichToRemove = rand.nextInt(list.size());
            adopt(list.get(whichToRemove));
        }
        int numberOfNodesAfter = 0;
        for (Dog dog : this) { // I am BEGGING you
            numberOfNodesAfter++;
        }
        if (numberOfNodesAfter != numberOfNodesBefore - toExterminate) {
            return "Remove error? Number of dogs before remove was " + numberOfNodesBefore + " after remove was " + numberOfNodesAfter;
        } else {
            return "Random remove probably succeeded. Number of dogs before remove was " + numberOfNodesBefore + " after remove was " + numberOfNodesAfter;
        }
    }

    private String addDogs() {
        int numToAdd = rand.nextInt(10);
        int iter = 0;
        while (iter < numToAdd) {
            iter++;
            shelter(rand.nextDog());
        }
        return "added " + numToAdd + " dogs to the tree";

    }

    //======================= Overridden methods =========================

    @Override
    public void shelter(Dog d) {
        if (root == null) {
            root = new DogNode(d);
        }
        super.shelter(d);
        refresh();
    }

    @Override
    public void adopt(Dog d) {
        //showUser("    [DViz / Debug] " + "Remove called on cat with name " + c.name);
        super.adopt(d);
        refresh();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        MainWindow = new JPanel();
        MainWindow.setLayout(new BorderLayout(0, 0));
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridBagLayout());
        MainPanel.setAutoscrolls(true);
        MainPanel.setMaximumSize(new Dimension(5120, 2147483647));
        MainPanel.setMinimumSize(new Dimension(556, 415));
        MainPanel.setPreferredSize(new Dimension(1000, 415));
        MainWindow.add(MainPanel, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        MainPanel.add(panel1, gbc);
        final JLabel label1 = new JLabel();
        label1.setEnabled(true);
        Font label1Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Visualization Controls");
        label1.setVerticalAlignment(1);
        label1.setVerticalTextPosition(1);
        panel1.add(label1, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, BorderLayout.WEST);
        drawSubtreesCheckBox = new JCheckBox();
        drawSubtreesCheckBox.setText("Draw Subtrees");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(drawSubtreesCheckBox, gbc);
        drawDaysShelteredCheckBox = new JCheckBox();
        drawDaysShelteredCheckBox.setEnabled(true);
        drawDaysShelteredCheckBox.setSelected(false);
        drawDaysShelteredCheckBox.setText("Draw Days in Shelter");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(drawDaysShelteredCheckBox, gbc);
        drawToStringCheckBox = new JCheckBox();
        drawToStringCheckBox.setSelected(true);
        drawToStringCheckBox.setText("Draw toString");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(drawToStringCheckBox, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel1.add(panel3, BorderLayout.EAST);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel3.add(panel4, BorderLayout.CENTER);
        WideningSlider = new JSlider();
        WideningSlider.setMaximum(10);
        WideningSlider.setMinimum(1);
        WideningSlider.setPaintLabels(true);
        WideningSlider.setPaintTicks(true);
        WideningSlider.setSnapToTicks(false);
        WideningSlider.setToolTipText("Widening Coefficeint");
        WideningSlider.setValue(2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(WideningSlider, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Widening Coefficent");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        panel4.add(label2, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel3.add(panel5, BorderLayout.EAST);
        RandomnessSlider = new JSlider();
        RandomnessSlider.setMaximum(22);
        RandomnessSlider.setMinimum(1);
        RandomnessSlider.setPaintLabels(true);
        RandomnessSlider.setPaintTicks(true);
        RandomnessSlider.setValue(2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(RandomnessSlider, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Randomness Coefficient");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        panel5.add(label3, gbc);
        forceRefreshButton = new JButton();
        forceRefreshButton.setText("Force Refresh");
        panel3.add(forceRefreshButton, BorderLayout.WEST);
        GraphScroller = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 100.0;
        gbc.weighty = 10.0;
        gbc.fill = GridBagConstraints.BOTH;
        MainPanel.add(GraphScroller, gbc);
        GraphRegion.setMinimumSize(new Dimension(300, 200));
        GraphScroller.setViewportView(GraphRegion);
        dogScroller = new JScrollPane();
        dogScroller.setAutoscrolls(false);
        dogScroller.setHorizontalScrollBarPolicy(30);
        dogScroller.setMaximumSize(new Dimension(400, 32767));
        dogScroller.setMinimumSize(new Dimension(300, 39));
        dogScroller.setPreferredSize(new Dimension(350, 10));
        dogScroller.setVerticalScrollBarPolicy(22);
        MainWindow.add(dogScroller, BorderLayout.WEST);
        dogScroller.setBorder(BorderFactory.createTitledBorder(null, "Dogs In Order", TitledBorder.LEFT, TitledBorder.TOP, this.$$$getFont$$$("Arial Black", Font.BOLD, 14, dogScroller.getFont()), new Color(-16777216)));
        dogScroller.setViewportView(ListOfDogsSide);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        MainWindow.add(panel6, BorderLayout.SOUTH);
        final JLabel label4 = new JLabel();
        label4.setText("Status:");
        panel6.add(label4, BorderLayout.WEST);
        Status = new JLabel();
        Status.setHorizontalTextPosition(2);
        Status.setText("<current status>");
        panel6.add(Status, BorderLayout.CENTER);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        MainWindow.add(scrollPane1, BorderLayout.EAST);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        scrollPane1.setViewportView(panel7);
        stressTestButton = new JButton();
        stressTestButton.setText("Stress Test");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(stressTestButton, gbc);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(panel8, gbc);
        final JLabel label5 = new JLabel();
        label5.setEnabled(true);
        Font label5Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setHorizontalAlignment(10);
        label5.setText("Constants");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel8.add(label5, gbc);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel8.add(panel9, gbc);
        final JLabel label6 = new JLabel();
        label6.setFocusTraversalPolicyProvider(false);
        label6.setHorizontalAlignment(0);
        label6.setHorizontalTextPosition(0);
        label6.setText("Test Intensity");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel9.add(label6, gbc);
        testIntensitySlider = new JSlider();
        testIntensitySlider.setMaximum(30);
        testIntensitySlider.setMinimum(1);
        testIntensitySlider.setName("Test Intensity");
        testIntensitySlider.setOrientation(1);
        testIntensitySlider.setPaintLabels(true);
        testIntensitySlider.setPaintTicks(true);
        testIntensitySlider.setToolTipText("Test Intensity");
        testIntensitySlider.setValue(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel9.add(testIntensitySlider, gbc);
        SpamFactorSlider = new JSlider();
        SpamFactorSlider.setMaximum(200);
        SpamFactorSlider.setMinimum(1);
        SpamFactorSlider.setOrientation(1);
        SpamFactorSlider.setPaintLabels(true);
        SpamFactorSlider.setPaintTicks(true);
        SpamFactorSlider.setSnapToTicks(false);
        SpamFactorSlider.setToolTipText("Spam Factor");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel9.add(SpamFactorSlider, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Spam Factor");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel9.add(label7, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel9.add(spacer1, gbc);
        SpamFactorDisplay = new JLabel();
        SpamFactorDisplay.setHorizontalAlignment(0);
        SpamFactorDisplay.setHorizontalTextPosition(0);
        SpamFactorDisplay.setText("Factor");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel9.add(SpamFactorDisplay, gbc);
        TestIntensityDisplay = new JLabel();
        TestIntensityDisplay.setHorizontalAlignment(0);
        TestIntensityDisplay.setHorizontalTextPosition(0);
        TestIntensityDisplay.setText("Intensity");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel9.add(TestIntensityDisplay, gbc);
        gradualTestButton = new JButton();
        gradualTestButton.setText("Gradual Test");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(gradualTestButton, gbc);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel7.add(panel10, gbc);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel10.add(panel11, gbc);
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Vet Weeks");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel11.add(label8, gbc);
        VetScheduleDisplay = new JLabel();
        VetScheduleDisplay.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel11.add(VetScheduleDisplay, gbc);
        CostPlanningSlider = new JSlider();
        CostPlanningSlider.setMaximum(365);
        CostPlanningSlider.setMinimum(1);
        CostPlanningSlider.setToolTipText("NumberofMonths");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel11.add(CostPlanningSlider, gbc);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel10.add(panel12, gbc);
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("Dogs in Tree");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(label9, gbc);
        DogsInTreeDisplay = new JLabel();
        DogsInTreeDisplay.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel12.add(DogsInTreeDisplay, gbc);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel10.add(panel13, gbc);
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setHorizontalAlignment(2);
        label10.setHorizontalTextPosition(2);
        label10.setText("Oldest");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel13.add(label10, gbc);
        OldestDisplay = new JLabel();
        OldestDisplay.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel13.add(OldestDisplay, gbc);
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setText("Automatic Tests");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel10.add(label11, gbc);
        spamAddButton = new JButton();
        spamAddButton.setText("Spam Add (almost definitely broken)");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(spamAddButton, gbc);
        final JLabel label12 = new JLabel();
        label12.setEnabled(true);
        Font label12Font = this.$$$getFont$$$("Arial Black", Font.BOLD, 14, label12.getFont());
        if (label12Font != null) label12.setFont(label12Font);
        label12.setText("Tree Manipulation");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(label12, gbc);
        AddCustom = new JButton();
        AddCustom.setText("Add Custom Dog");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(AddCustom, gbc);
        RemoveRandom = new JButton();
        RemoveRandom.setText("Rem Random Dog");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(RemoveRandom, gbc);
        spamRemoveButton = new JButton();
        spamRemoveButton.setText("Spam Remove (probably broken)");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(spamRemoveButton, gbc);
        AddRandomDog = new JButton();
        AddRandomDog.setHorizontalTextPosition(0);
        AddRandomDog.setText("Add Random Dog\n(might duplicate values)");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(AddRandomDog, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainWindow;
    }


//======================= UI Element Nested Classes =========================

    class GraphZone extends JPanel {                    //Responsible for drawing the binary tree
        Dimension idealSize;
        DogNode startNode;
        boolean mainWindow = false;

        public GraphZone() {

            //setBorder(BorderFactory.createLineBorder(Color.black));
            this.idealSize = new Dimension(1080, 1080);
            this.mainWindow = true;
            this.startNode = root;
            try {
                GraphScroller.getHorizontalScrollBar().setValue(540);
            } catch (Exception n) {
                //showUser("    [DViz / Debug] " + "not resized");
            }
            /*
            if (mainWindow) {
                try {
                    GraphScroller.getHorizontalScrollBar().setValue(GraphRegion.getWidth() / 2);
                } catch (Exception e) {
                    try {
                        GraphScroller.getHorizontalScrollBar().setValue(GraphScroller.getWidth() / 2);
                    } catch (Exception f) {
                        try {
                            GraphScroller.getHorizontalScrollBar().setValue(960);
                        } catch (Exception n) {
                            //showUser("    [DViz / Debug] " + "not resized");
                        }
                    }
                }
            }*/
        }

        public GraphZone(Dimension d, DogNode startNode) {
            this.idealSize = d;
            this.startNode = startNode;
        }

        public Dimension getPreferredSize() {
            return this.idealSize;      //TODO: Dynamic sizing
        }

        public void paintComponent(Graphics g) {
            //Draw the tree
            super.paintComponent(g);
            int[] start = {this.getWidth() / 2, 20};
            if (mainWindow) {
                this.startNode = root;
            }
            if (startNode != null) {
                drawNodeWithChildren(g, start, startNode);
            } else {
                g.drawString("root is null; tree is empty", this.getWidth() / 2, this.getHeight() / 2);
                g.drawString("root is null; tree is empty", this.getWidth() / 2, 20);
            }

            //Attempt auto resize //TODO: check this implementation
            this.setPreferredSize(this.getPreferredSize());

            //Attempt to automatically scroll to the middle of the graphics region

        }

        private void drawNodeWithChildren(Graphics g, int[] coords, DogNode node) {
            //showUser("    [DViz / Debug] " + "drawing node " + node + " at coords " + coords[0] + ", " + coords[1]);
            StringBuilder title = new StringBuilder();
            if (drawDaysShelteredCheckBox.isSelected()) {
                title.append(node.d.getDaysAtTheShelter());
            }
            if (drawToStringCheckBox.isSelected()) {
                title.append(" ").append(node.d.toString());
            }
            /*
            if (drawSamesRadioButton.isSelected()) {
                if (node.same != null) {
                    CatNode temp = node;
                    int iter = 1;
                    try {
                        while (temp.same != null) {
                            temp = temp.same;
                            g.setColor(Color.DARK_GRAY);
                            g.drawLine(coords[0], coords[1], coords[0], coords[1] + (20 * iter));
                            StringBuilder titleSame = new StringBuilder();
                            if (drawDaysShelteredRadioButton.isSelected()) {
                                titleSame.append(temp.data.monthHired);
                            }
                            if (drawToStringRadioButton.isSelected()) {
                                titleSame.append(" ").append(temp.data.name);
                            }
                            g.drawRect(coords[0], coords[1] + (20 * iter), titleSame.toString().length() * 7, 20);
                            g.drawString(titleSame.toString(), coords[0] + 2, coords[1] + (20 * iter) + 15);
                            iter++;
                        }
                        g.setColor(Color.BLACK);
                    } catch (Exception e) {
                    }
                }
            }

             */
            g.drawRect(coords[0], coords[1], title.toString().length() * 7, 20);
            g.drawString(title.toString(), coords[0] + 2, coords[1] + 15);
            if (node.older != null) {
                g.setColor(Color.BLUE);
                //showUser("    [DViz / Debug] " + "recursing to senior");
                int[] nodeOlderCoords;
                if (mainWindow) {
                    nodeOlderCoords = new int[]{coords[0] - ((35 + (int) Math.round(Math.sqrt(coords[1])) * wideningCoeff + (int) Math.round(Math.pow(randomnessCoeff / 2.0 - rand.nextInt(Math.abs(randomnessCoeff)), 2)) + (3 - rand.nextInt(3 * randomnessCoeff)))), coords[1] + (50 + (7 - rand.nextInt(14)))};
                } else {
                    nodeOlderCoords = new int[]{coords[0] - ((35 + (int) Math.round(Math.sqrt(coords[1])))), coords[1] + 35};
                }
                g.drawLine(coords[0], coords[1], nodeOlderCoords[0], nodeOlderCoords[1]);
                drawNodeWithChildren(g, nodeOlderCoords, node.older);
            }
            if (node.younger != null) {
                g.setColor(Color.DARK_GRAY);
                //showUser("    [DViz / Debug] " + "recursing to junior");
                int[] nodeYoungerCoords;
                if (mainWindow) {
                    nodeYoungerCoords = new int[]{coords[0] + ((35 + (int) Math.round(Math.sqrt(coords[1])) * wideningCoeff + (int) Math.round(Math.pow(randomnessCoeff / 2.0 - rand.nextInt(Math.abs(randomnessCoeff)), 2)) + (3 - rand.nextInt(3 * randomnessCoeff)))), coords[1] + (50 + (7 - rand.nextInt(14)))};
                } else {
                    nodeYoungerCoords = new int[]{coords[0] + ((35 + (int) Math.round(Math.sqrt(coords[1])))), coords[1] + 35};
                }
                g.drawLine(coords[0], coords[1], nodeYoungerCoords[0], nodeYoungerCoords[1]);
                drawNodeWithChildren(g, nodeYoungerCoords, node.younger);
            }
        }
    }

    class DogNodeDrawing extends JComponent {
        //private static RandomCats rand;
        protected JPanel panel;
        protected JScrollPane scrollPane;
        private final ArrayList<DogBox> DogBoxList = new ArrayList<>();
        private final Dimension idealSize;
        private final DogNode node;
        private final Dimension size = new Dimension(20, 20);
        private final boolean isList = false;
        private final int listLength = 0;
        private final ArrayList<DogNode> dogsList = new ArrayList<>();
        private JPanel DogsList;
        private JPanel innerPanel;
        private JTextArea DogsOfMonth;

        public DogNodeDrawing(DogNode input) {
            this.node = input;
            $$$setupUI$$$();
            this.dogsList.add(this.node);
            // Dogs don't have a node with the same seniority
            /*
            if (this.node.same != null) {
                this.isList = true;
                CatNode temp = this.node;
                while (temp.same != null) {
                    listLength++;
                    temp = temp.same;
                    dogsList.add(temp);
                }
            }
             */
            this.idealSize = new Dimension((30 + (node.d.toString().length() * 3 + (9 * Double.toString(node.d.getExpectedVetCost()).length()))), (20 + this.listLength * 20));

            //showUser("    [DViz / Debug] " + "Size of catnode visualizer is " + this.idealSize);

            panel.setSize(idealSize);
            this.setPreferredSize(idealSize);
            this.setSize(idealSize);
            this.setMinimumSize(idealSize);

            panel.setBackground(Color.GRAY);

            if (dogsList.size() > 1) {
                //innerPanel.add(new JLabel("    [DViz / Debug] " + "Cats hired on " + node.data.monthHired));
                DogsOfMonth.setEnabled(true);
                DogsOfMonth.setVisible(true);
                DogsOfMonth.setText("Dog sheltered " + node.d.getDaysAtTheShelter() + " days ago");
            }

            for (DogNode node : dogsList) {
                //showUser("    [DViz / Debug] " + new CatBox(cat.data).getSize());
                DogBoxList.add(new DogBox(node.d));
            }

            for (DogBox box : DogBoxList) {
                DogsList.add(box.mainPanel);
            }

            add(panel);

            this.setBackground(Color.gray);
        }

        /*public static void main(String[] args) {
            rand = new RandomCats();
            JFrame frame = new JFrame("CatNodeDrawing");
            CatTree.CatNode testCat = rand.nextCatNode();
            testCat.same = rand.nextCatNode();
            frame.setContentPane(new CatNodeDrawing(testCat).panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }*/

        private void createUIComponents() {
            DogsList = new JPanel();
            DogsList.setLayout(new BoxLayout(this.DogsList, BoxLayout.Y_AXIS));

        }

        /**
         * Method generated by IntelliJ IDEA GUI Designer
         * >>> IMPORTANT!! <<<
         * DO NOT edit this method OR call it in your code!
         *
         * @noinspection ALL
         */
        private void $$$setupUI$$$() {
            createUIComponents();
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            innerPanel = new JPanel();
            innerPanel.setLayout(new BorderLayout(0, 0));
            panel.add(innerPanel);
            scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(31);
            innerPanel.add(scrollPane, BorderLayout.SOUTH);
            scrollPane.setViewportView(DogsList);
            DogsOfMonth = new JTextArea();
            DogsOfMonth.setEditable(false);
            DogsOfMonth.setEnabled(false);
            DogsOfMonth.setFocusable(false);
            Font DogsOfMonthFont = this.$$$getFont$$$("Franklin Gothic Medium Cond", -1, 14, DogsOfMonth.getFont());
            if (DogsOfMonthFont != null) DogsOfMonth.setFont(DogsOfMonthFont);
            DogsOfMonth.setLineWrap(true);
            DogsOfMonth.setOpaque(true);
            DogsOfMonth.setVisible(false);
            innerPanel.add(DogsOfMonth, BorderLayout.NORTH);
        }

        /**
         * @noinspection ALL
         */
        private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
            if (currentFont == null) return null;
            String resultName;
            if (fontName == null) {
                resultName = currentFont.getName();
            } else {
                Font testFont = new Font(fontName, Font.PLAIN, 10);
                if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                    resultName = fontName;
                } else {
                    resultName = currentFont.getName();
                }
            }
            Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
            boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
            Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
            return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
        }

        /**
         * @noinspection ALL
         */
        public JComponent $$$getRootComponent$$$() {
            return panel;
        }

        public class DogBox extends JComponent {
            protected JPanel mainPanel;
            protected Dog data;
            private JPanel UpperPanel;
            private JPanel LowerPanel;
            private JTextArea BigText;
            private JButton removeButton;
            private JPanel removeBtn;
            private JPanel graphArea;
            private GraphZone gZ;

            public DogBox() {
                $$$setupUI$$$();
                data = new RandomDogs().nextDog();
                BigText.setText(data.toString());
                LowerPanel.add(new JLabel("Sheltered " + data.getDaysAtTheShelter()));
                LowerPanel.add(new JLabel("Age " + data.getAge()));
                LowerPanel.add(new JLabel("Next App. " + data.getDaysToNextVetAppointment()));
                LowerPanel.add(new JLabel("Cost " + data.getExpectedVetCost()));
                this.setSize(70, 30);
                DogNodeDrawing.this.createUIComponents();
                addListeners();
            }

            public DogBox(Dog data) {
                if (data == null) {
                    return;
                }
                this.data = data;
                $$$setupUI$$$();

                BigText.setText(data.toString());
                LowerPanel.add(new JLabel("Sheltered " + data.getDaysAtTheShelter()));
                LowerPanel.add(new JLabel("Age " + data.getAge()));
                LowerPanel.add(new JLabel("Next App. " + data.getDaysToNextVetAppointment()));
                LowerPanel.add(new JLabel("Cost " + data.getExpectedVetCost()));
                this.setSize(70, 30);
                //CatNodeDrawing.this.createUIComponents();
                addListeners();
            }

            /* public static void main(String[] args) {
                 JFrame frame = new JFrame("CatBox");
                 frame.setContentPane(new CatBox().mainPanel);
                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 frame.pack();
                 frame.setVisible(true);
             }
        */
            private void addListeners() {

                /*drawTreeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GraphRegion.removeAll();
                        GraphRegion.add(new GraphZone(new Dimension(1280, 800), findCat(root, data)));
                    }
                });*/
                removeButton.addActionListener(e -> adopt(data));
            }

            private void createUIComponents() {
                try {
                    if (drawSubtreesCheckBox.isSelected()) {
                        gZ = new GraphZone(new Dimension(200, 100), findDog(root, data));
                        this.graphArea = new JPanel();
                        graphArea.add(gZ);
                        gZ.repaint();
                    } else {
                        this.graphArea = new JPanel();
                    }
                } catch (NullPointerException e) {
                    this.graphArea = new JPanel();
                }
            }

            /**
             * Method generated by IntelliJ IDEA GUI Designer
             * >>> IMPORTANT!! <<<
             * DO NOT edit this method OR call it in your code!
             *
             * @noinspection ALL
             */
            private void $$$setupUI$$$() {
                createUIComponents();
                final JPanel panel1 = new JPanel();
                panel1.setLayout(new GridBagLayout());
                mainPanel = new JPanel();
                mainPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc;
                gbc = new GridBagConstraints();
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel1.add(mainPanel, gbc);
                final JScrollPane scrollPane1 = new JScrollPane();
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
                mainPanel.add(scrollPane1, gbc);
                LowerPanel = new JPanel();
                LowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                Font LowerPanelFont = this.$$$getFont$$$(null, -1, 12, LowerPanel.getFont());
                if (LowerPanelFont != null) LowerPanel.setFont(LowerPanelFont);
                scrollPane1.setViewportView(LowerPanel);
                removeBtn = new JPanel();
                removeBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.BOTH;
                mainPanel.add(removeBtn, gbc);
                BigText = new JTextArea();
                BigText.setEditable(false);
                BigText.setEnabled(true);
                Font BigTextFont = this.$$$getFont$$$("Arial", Font.PLAIN, 14, BigText.getFont());
                if (BigTextFont != null) BigText.setFont(BigTextFont);
                BigText.setOpaque(false);
                BigText.setWrapStyleWord(true);
                removeBtn.add(BigText);
                removeButton = new JButton();
                removeButton.setText("remove");
                removeBtn.add(removeButton);
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.fill = GridBagConstraints.BOTH;
                mainPanel.add(graphArea, gbc);
            }

            /**
             * @noinspection ALL
             */
            private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
                if (currentFont == null) return null;
                String resultName;
                if (fontName == null) {
                    resultName = currentFont.getName();
                } else {
                    Font testFont = new Font(fontName, Font.PLAIN, 10);
                    if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                        resultName = fontName;
                    } else {
                        resultName = currentFont.getName();
                    }
                }
                Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
                boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
                Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
                return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
            }

        }
    }

    class AddCustomDog extends JDialog {
        private JPanel contentPane;
        private JButton buttonOK;
        private JButton buttonCancel;
        private JTextField enterNameTextField;
        public Dog info;
        private JSlider sliderAge, sliderCost, sliderDays, sliderApp;
        private JButton randomNameButton;
        private final RandomDogs rand = new RandomDogs();
        private JSpinner spinnerAge, spinnerCost, spinnerApp, spinnerDays;
        public boolean cancelled = false;
        private int daysInShelter = 0;
        private int age = 0;
        private int nextApp = 0;
        private int nextAppCost = 0;
        private String name = "";

        public AddCustomDog() {
            setContentPane(contentPane);
            setModal(true);
            getRootPane().setDefaultButton(buttonOK);
            addListeners();
        }

        private void onOK() {
            // add your code here
            daysInShelter = sliderDays.getValue();
            nextApp = sliderApp.getValue();
            nextAppCost = sliderCost.getValue();
            age = sliderAge.getValue();
            name = enterNameTextField.getText();
            info = new Dog(name, age, daysInShelter, nextApp, nextAppCost);
            //showUser("    [DViz / Debug] " + info);
            //addCat(new CatInfo(name, monthHired, furThick, nextApp, nextAppCost));
            dispose();
        }

        private void onCancel() {
            // add your code here if necessary
            info = null;
            cancelled = true;
            dispose();
        }

       /* public static void main(String[] args) {
            AddCustomCat dialog = new AddCustomCat();
            dialog.pack();
            dialog.setVisible(true);
            System.exit(0);
        }*/

        private void addListeners() {
            sliderAge.addChangeListener(e -> sliderThickChanged());
            spinnerAge.addChangeListener(e -> spinnerThickChanged());
            sliderApp.addChangeListener(e -> sliderAppChanged());
            spinnerApp.addChangeListener(e -> spinnerAppChanged());
            sliderCost.addChangeListener(e -> sliderCostChanged());
            spinnerCost.addChangeListener(e -> spinnerCostChanged());
            sliderDays.addChangeListener(e -> sliderMonthChanged());
            spinnerDays.addChangeListener(e -> spinnerMonthChanged());
            refresh();

            buttonOK.addActionListener(e -> onOK());

            buttonCancel.addActionListener(e -> onCancel());

            // call onCancel() when cross is clicked
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onCancel();
                }
            });

            // call onCancel() on ESCAPE
            contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            randomNameButton.addActionListener(e -> enterNameTextField.setText(rand.nextName()));
        }

        private void refresh() {
            sliderMonthChanged();
            sliderThickChanged();
            sliderCostChanged();
            sliderAppChanged();
            spinnerCostChanged();
            spinnerAppChanged();
            spinnerThickChanged();
            spinnerMonthChanged();
        }

        private void sliderThickChanged() {
            spinnerAge.setValue(sliderAge.getValue());
        }

        private void sliderMonthChanged() {
            spinnerDays.setValue(sliderDays.getValue());
        }

        private void sliderAppChanged() {
            spinnerApp.setValue(sliderApp.getValue());
        }

        private void sliderCostChanged() {
            spinnerCost.setValue(sliderCost.getValue());
        }

        private void spinnerMonthChanged() {
            sliderDays.setValue(Integer.parseInt(spinnerDays.getValue().toString()));
        }

        private void spinnerThickChanged() {
            sliderAge.setValue(Integer.parseInt(spinnerAge.getValue().toString()));
        }

        private void spinnerCostChanged() {
            sliderCost.setValue(Integer.parseInt(spinnerCost.getValue().toString()));
        }

        private void spinnerAppChanged() {
            sliderApp.setValue(Integer.parseInt(spinnerApp.getValue().toString()));
        }

        {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
            $$$setupUI$$$();
        }

        /**
         * Method generated by IntelliJ IDEA GUI Designer
         * >>> IMPORTANT!! <<<
         * DO NOT edit this method OR call it in your code!
         *
         * @noinspection ALL
         */
        private void $$$setupUI$$$() {
            contentPane = new JPanel();
            contentPane.setLayout(new GridBagLayout());
            final JPanel panel1 = new JPanel();
            panel1.setLayout(new BorderLayout(0, 0));
            GridBagConstraints gbc;
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            contentPane.add(panel1, gbc);
            final JLabel label1 = new JLabel();
            label1.setText("Dog Wizard - <3 sashaphoto.ca, meow10811");
            panel1.add(label1, BorderLayout.WEST);
            final JPanel panel2 = new JPanel();
            panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel1.add(panel2, BorderLayout.EAST);
            buttonOK = new JButton();
            buttonOK.setText("OK");
            panel2.add(buttonOK);
            buttonCancel = new JButton();
            buttonCancel.setText("Cancel");
            panel2.add(buttonCancel);
            final JPanel panel3 = new JPanel();
            panel3.setLayout(new BorderLayout(0, 0));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            contentPane.add(panel3, gbc);
            final JPanel panel4 = new JPanel();
            panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel3.add(panel4, BorderLayout.NORTH);
            enterNameTextField = new JTextField();
            enterNameTextField.setHorizontalAlignment(0);
            enterNameTextField.setMinimumSize(new Dimension(140, 30));
            enterNameTextField.setPreferredSize(new Dimension(140, 30));
            enterNameTextField.setText("Enter Name");
            enterNameTextField.setToolTipText("Enter Name");
            panel4.add(enterNameTextField);
            randomNameButton = new JButton();
            randomNameButton.setText("Random Name");
            panel4.add(randomNameButton);
            final JPanel panel5 = new JPanel();
            panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel3.add(panel5, BorderLayout.CENTER);
            final JPanel panel6 = new JPanel();
            panel6.setLayout(new GridBagLayout());
            panel5.add(panel6);
            sliderAge = new JSlider();
            sliderAge.setToolTipText("Fur Thickness");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel6.add(sliderAge, gbc);
            final JLabel label2 = new JLabel();
            label2.setText("Age");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            panel6.add(label2, gbc);
            spinnerAge = new JSpinner();
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel6.add(spinnerAge, gbc);
            final JPanel panel7 = new JPanel();
            panel7.setLayout(new GridBagLayout());
            panel5.add(panel7);
            sliderCost = new JSlider();
            sliderCost.setToolTipText("Estimated Grooming Cost");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel7.add(sliderCost, gbc);
            final JLabel label3 = new JLabel();
            label3.setText("Vet Cost");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            panel7.add(label3, gbc);
            spinnerCost = new JSpinner();
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel7.add(spinnerCost, gbc);
            final JPanel panel8 = new JPanel();
            panel8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            contentPane.add(panel8, gbc);
            final JPanel panel9 = new JPanel();
            panel9.setLayout(new GridBagLayout());
            panel8.add(panel9);
            sliderDays = new JSlider();
            sliderDays.setMaximum(243);
            sliderDays.setMinimum(0);
            sliderDays.setToolTipText("Month Hired");
            sliderDays.setValue(122);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel9.add(sliderDays, gbc);
            final JLabel label4 = new JLabel();
            label4.setText("Days in Shelter");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            panel9.add(label4, gbc);
            spinnerDays = new JSpinner();
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel9.add(spinnerDays, gbc);
            final JPanel panel10 = new JPanel();
            panel10.setLayout(new GridBagLayout());
            panel8.add(panel10);
            sliderApp = new JSlider();
            sliderApp.setToolTipText("Next Appointment");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel10.add(sliderApp, gbc);
            final JLabel label5 = new JLabel();
            label5.setText("Next Appointment");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            panel10.add(label5, gbc);
            spinnerApp = new JSpinner();
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel10.add(spinnerApp, gbc);
        }

        /**
         * @noinspection ALL
         */
        public JComponent $$$getRootComponent$$$() {
            return contentPane;
        }

    }

    class StressTest extends JDialog {
        private JPanel contentPane;
        private JButton buttonOK;

        public StressTest() {
            setContentPane(contentPane);
            setModal(true);
            getRootPane().setDefaultButton(buttonOK);

            buttonOK.addActionListener(e -> onOK());
        }

        private void onOK() {
            // add your code here
            dispose();
        }

        {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
            $$$setupUI$$$();
        }

        /**
         * Method generated by IntelliJ IDEA GUI Designer
         * >>> IMPORTANT!! <<<
         * DO NOT edit this method OR call it in your code!
         *
         * @noinspection ALL
         */
        private void $$$setupUI$$$() {
            contentPane = new JPanel();
            contentPane.setLayout(new GridBagLayout());
            final JPanel panel1 = new JPanel();
            panel1.setLayout(new GridBagLayout());
            GridBagConstraints gbc;
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            contentPane.add(panel1, gbc);
            final JPanel panel2 = new JPanel();
            panel2.setLayout(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            panel1.add(panel2, gbc);
            buttonOK = new JButton();
            buttonOK.setText("OK");
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel2.add(buttonOK, gbc);
            final JPanel panel3 = new JPanel();
            panel3.setLayout(new BorderLayout(0, 0));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            contentPane.add(panel3, gbc);
            final JLabel label1 = new JLabel();
            label1.setText("View the Java console to see how you fared.");
            panel3.add(label1, BorderLayout.NORTH);
            final JLabel label2 = new JLabel();
            label2.setText("If the list disappears or the program freezes, adding a random dog (can replace root) is likely to help.");
            panel3.add(label2, BorderLayout.SOUTH);
            final JLabel label3 = new JLabel();
            label3.setText("Failure messages are generated by counting using the iterator. If your iterator does not work, you may ignore them.");
            panel3.add(label3, BorderLayout.WEST);
        }

        /**
         * @noinspection ALL
         */
        public JComponent $$$getRootComponent$$$() {
            return contentPane;
        }

    }

}