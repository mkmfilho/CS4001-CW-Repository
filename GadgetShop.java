import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GadgetShop extends JFrame implements ActionListener {

    // Text fields (ten)
    private final JTextField tfModel = new JTextField();
    private final JTextField tfPrice = new JTextField();
    private final JTextField tfWeight = new JTextField();
    private final JTextField tfSize = new JTextField();
    private final JTextField tfInitialCredit = new JTextField();
    private final JTextField tfInitialMemory = new JTextField();
    private final JTextField tfPhoneNumber = new JTextField();
    private final JTextField tfDuration = new JTextField();
    private final JTextField tfDownloadSize = new JTextField();
    private final JTextField tfDisplayNumber = new JTextField();

    // Buttons
    private final JButton btnAddMobile = new JButton("Add Mobile");
    private final JButton btnAddMP3 = new JButton("Add MP3");
    private final JButton btnClear = new JButton("Clear");
    private final JButton btnDisplayAll = new JButton("Display All");
    private final JButton btnMakeCall = new JButton("Make A Call");
    private final JButton btnDownloadMusic = new JButton("Download Music");

    // Output area
    private final JTextArea logArea = new JTextArea(12, 50);

    // Storage
    private final ArrayList<Gadget> gadgets = new ArrayList<>();

    public GadgetShop() {
        super("Gadget Shop");

        // Top panel for inputs (use GridBag for readable layout)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Row helper
        int row = 0;
        addLabeled(inputPanel, "Model:", tfModel, row++, c);
        addLabeled(inputPanel, "Price (decimal):", tfPrice, row++, c);
        addLabeled(inputPanel, "Weight (grams):", tfWeight, row++, c);
        addLabeled(inputPanel, "Size (e.g. 71mm x 137mm x 9mm):", tfSize, row++, c);
        addLabeled(inputPanel, "Initial credit (minutes):", tfInitialCredit, row++, c);
        addLabeled(inputPanel, "Initial memory (MB):", tfInitialMemory, row++, c);
        addLabeled(inputPanel, "Phone number:", tfPhoneNumber, row++, c);
        addLabeled(inputPanel, "Duration (minutes):", tfDuration, row++, c);
        addLabeled(inputPanel, "Download size (MB):", tfDownloadSize, row++, c);
        addLabeled(inputPanel, "Display number (index):", tfDisplayNumber, row++, c);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        buttonsPanel.add(btnAddMobile);
        buttonsPanel.add(btnAddMP3);
        buttonsPanel.add(btnClear);
        buttonsPanel.add(btnDisplayAll);
        buttonsPanel.add(btnMakeCall);
        buttonsPanel.add(btnDownloadMusic);

        // Log area in a scroll pane
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Wire buttons
        btnAddMobile.addActionListener(this);
        btnAddMP3.addActionListener(this);
        btnClear.addActionListener(this);
        btnDisplayAll.addActionListener(this);
        btnMakeCall.addActionListener(this);
        btnDownloadMusic.addActionListener(this);

        // Frame layout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(8, 8));
        cp.add(inputPanel, BorderLayout.NORTH);
        cp.add(buttonsPanel, BorderLayout.CENTER);
        cp.add(scroll, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center
        setVisible(true);
    }

    // Helper to add label + field to GridBag
    private void addLabeled(JPanel panel, String labelText, JComponent field, int row, GridBagConstraints c) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0.0;
        panel.add(new JLabel(labelText), c);
        c.gridx = 1;
        c.weightx = 1.0;
        panel.add(field, c);
    }

    // Action handling
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnAddMobile) {
            addMobile();
        } else if (src == btnAddMP3) {
            addMP3();
        } else if (src == btnClear) {
            clearInputs();
        } else if (src == btnDisplayAll) {
            displayAll();
        } else if (src == btnMakeCall) {
            makeCall();
        } else if (src == btnDownloadMusic) {
            downloadMusic();
        }
    }

    // Input helper methods with validation
    private String getModelInput() {
        return tfModel.getText().trim();
    }

    private double getPriceInput() {
        try {
            return Double.parseDouble(tfPrice.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid price (decimal).");
            return -1;
        }
    }

    private int getWeightInput() {
        try {
            return Integer.parseInt(tfWeight.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid weight (integer grams).");
            return -1;
        }
    }

    private String getSizeInput() {
        return tfSize.getText().trim();
    }

    private int getInitialCreditInput() {
        try {
            return Integer.parseInt(tfInitialCredit.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid initial credit (integer minutes).");
            return -1;
        }
    }

    private int getInitialMemoryInput() {
        try {
            return Integer.parseInt(tfInitialMemory.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid initial memory (integer MB).");
            return -1;
        }
    }

    private String getPhoneNumberInput() {
        return tfPhoneNumber.getText().trim();
    }

    private int getDurationInput() {
        try {
            return Integer.parseInt(tfDuration.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid duration (integer minutes).");
            return -1;
        }
    }

    private int getDownloadSizeInput() {
        try {
            return Integer.parseInt(tfDownloadSize.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid download size (integer MB).");
            return -1;
        }
    }

    // Special: get display number with try/catch and range check
    private int getDisplayNumberInput() {
        int displayNumber = -1;
        String text = tfDisplayNumber.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a display number.");
            return -1;
        }
        try {
            int n = Integer.parseInt(text);
            if (n >= 0 && n < gadgets.size()) {
                displayNumber = n;
            } else {
                JOptionPane.showMessageDialog(this, "Display number out of range. Valid range: 0 to " + (gadgets.size() - 1));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Display number must be an integer.");
        }
        return displayNumber;
    }

    // Button handlers
    private void addMobile() {
        String model = getModelInput();
        double price = getPriceInput();
        int weight = getWeightInput();
        String size = getSizeInput();
        int credit = getInitialCreditInput();

        if (model.isEmpty() || size.isEmpty() || price < 0 || weight < 0 || credit < 0) {
            return;
        }

        Mobile m = new Mobile(model, price, weight, size, credit);
        gadgets.add(m);
        log("Added Mobile at index " + (gadgets.size() - 1) + ": " + model);
    }

    private void addMP3() {
        String model = getModelInput();
        double price = getPriceInput();
        int weight = getWeightInput();
        String size = getSizeInput();
        int memory = getInitialMemoryInput();

        if (model.isEmpty() || size.isEmpty() || price < 0 || weight < 0 || memory < 0) {
            return;
        }

        MP3 p = new MP3(model, price, weight, size, memory);
        gadgets.add(p);
        log("Added MP3 at index " + (gadgets.size() - 1) + ": " + model);
    }

    private void clearInputs() {
        tfModel.setText("");
        tfPrice.setText("");
        tfWeight.setText("");
        tfSize.setText("");
        tfInitialCredit.setText("");
        tfInitialMemory.setText("");
        tfPhoneNumber.setText("");
        tfDuration.setText("");
        tfDownloadSize.setText("");
        tfDisplayNumber.setText("");
        log("Inputs cleared.");
    }

    private void displayAll() {
        if (gadgets.isEmpty()) {
            log("No gadgets to display.");
            return;
        }
        for (int i = 0; i < gadgets.size(); i++) {
            log("Gadget " + i + ":");
            appendGadgetDisplayToLog(gadgets.get(i));
        }
    }

    private void makeCall() {
        int index = getDisplayNumberInput();
        if (index == -1) return;

        Gadget g = gadgets.get(index);
        if (!(g instanceof Mobile)) {
            JOptionPane.showMessageDialog(this, "Selected gadget is not a Mobile phone.");
            return;
        }
        Mobile m = (Mobile) g;
        String phone = getPhoneNumberInput();
        int duration = getDurationInput();
        if (phone.isEmpty() || duration <= 0) {
            JOptionPane.showMessageDialog(this, "Enter a valid phone number and positive duration.");
            return;
        }
        m.makeCall(phone, duration);
        log("After call: index " + index + " (" + m.getModel() + ") credit = " + m.getCreditMinutes() + " minutes");
    }

    private void downloadMusic() {
        int index = getDisplayNumberInput();
        if (index == -1) return;

        Gadget g = gadgets.get(index);
        if (!(g instanceof MP3)) {
            JOptionPane.showMessageDialog(this, "Selected gadget is not an MP3 player.");
            return;
        }
        MP3 p = (MP3) g;
        int size = getDownloadSizeInput();
        if (size <= 0) {
            JOptionPane.showMessageDialog(this, "Enter a positive download size.");
            return;
        }
        p.downloadMusic(size);
        log("After download: index " + index + " (" + p.getModel() + ") available memory = " + p.getAvailableMemory() + " MB");
    }

    // Utility: append text to log area and System.out
    private void log(String text) {
        logArea.append(text + System.lineSeparator());
        System.out.println(text);
    }

    // Utility: call gadget.display() and also append its fields to the log area
    private void appendGadgetDisplayToLog(Gadget g) {
        // call the display method (which prints to System.out)
        g.display();

        // Also append the same info to the GUI log area for screenshots
        StringBuilder sb = new StringBuilder();
        sb.append("Model: ").append(g.getModel()).append(" | ");
        sb.append("Price: \u00A3").append(g.getPrice()).append(" | ");
        sb.append("Weight: ").append(g.getWeight()).append(" g | ");
        sb.append("Size: ").append(g.getSize());

        if (g instanceof Mobile) {
            Mobile m = (Mobile) g;
            sb.append(" | Calling credit: ").append(m.getCreditMinutes()).append(" minutes");
        } else if (g instanceof MP3) {
            MP3 p = (MP3) g;
            sb.append(" | Available memory: ").append(p.getAvailableMemory()).append(" MB");
        }
        log(sb.toString());
    }

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GadgetShop());
    }
}
