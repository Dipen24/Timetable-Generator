package AdminPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SchedulePanel extends JPanel {
    private static final String[] DAYS = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
    private static final String[] TIME_SLOTS = { "8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00",
            "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00" };

    private String componentType;
    private int numberOfSections;
    private int sessionsPerWeek;
    private boolean isParallel;

    private List<JComboBox<String>> dayComboBoxes;
    private List<JComboBox<String>> timeComboBoxes;

    public SchedulePanel(String componentType, int numberOfSections, int sessionsPerWeek, boolean isParallel) {
        this.componentType = componentType;
        this.numberOfSections = numberOfSections;
        this.sessionsPerWeek = sessionsPerWeek;
        this.isParallel = isParallel;

        dayComboBoxes = new ArrayList<>();
        timeComboBoxes = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 248, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add section headers
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Section"), gbc);

        gbc.gridx = 1;
        add(new JLabel("Day"), gbc);

        gbc.gridx = 2;
        add(new JLabel("Time"), gbc);

        // Add schedule rows for each section
        for (int i = 0; i < numberOfSections; i++) {
            gbc.gridy = i + 1;

            // Section label
            gbc.gridx = 0;
            add(new JLabel(componentType + (i + 1)), gbc);

            // Day combo box
            gbc.gridx = 1;
            JComboBox<String> dayCombo = new JComboBox<>(DAYS);
            dayComboBoxes.add(dayCombo);
            add(dayCombo, gbc);

            // Time combo box
            gbc.gridx = 2;
            JComboBox<String> timeCombo = new JComboBox<>(TIME_SLOTS);
            timeComboBoxes.add(timeCombo);
            add(timeCombo, gbc);

            // If parallel scheduling, disable day and time selection for subsequent
            // sections
            if (isParallel && i > 0) {
                dayCombo.setEnabled(false);
                timeCombo.setEnabled(false);

                // Add listeners to first section to update others
                if (i == 1) {
                    dayComboBoxes.get(0).addActionListener(e -> {
                        String selectedDay = (String) dayComboBoxes.get(0).getSelectedItem();
                        for (int j = 1; j < dayComboBoxes.size(); j++) {
                            dayComboBoxes.get(j).setSelectedItem(selectedDay);
                        }
                    });

                    timeComboBoxes.get(0).addActionListener(e -> {
                        String selectedTime = (String) timeComboBoxes.get(0).getSelectedItem();
                        for (int j = 1; j < timeComboBoxes.size(); j++) {
                            timeComboBoxes.get(j).setSelectedItem(selectedTime);
                        }
                    });
                }
            }
        }
    }

    public String[][] getSchedule() {
        String[][] schedule = new String[numberOfSections][2];
        for (int i = 0; i < numberOfSections; i++) {
            schedule[i][0] = (String) dayComboBoxes.get(i).getSelectedItem();
            schedule[i][1] = (String) timeComboBoxes.get(i).getSelectedItem();
        }
        return schedule;
    }

    public void setSchedule(String[][] schedule) {
        for (int i = 0; i < Math.min(numberOfSections, schedule.length); i++) {
            dayComboBoxes.get(i).setSelectedItem(schedule[i][0]);
            timeComboBoxes.get(i).setSelectedItem(schedule[i][1]);
        }
    }
}