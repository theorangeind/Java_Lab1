import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends JFrame
{
    private JButton buttonShowAll = new JButton("Show All");
    private JButton buttonFindEnrolee = new JButton("Find Enrolee");
    private JButton buttonAddEnrolee = new JButton("Add Enrolee");
    private JButton buttonAddNewEnrolee = new JButton("Add New Enrolee");
    private JButton buttonFindLowGrades = new JButton("Find Low Grades");
    private JButton buttonMainMenu = new JButton("Main Menu");
    private JButton buttonDeleteEnrolee = new JButton("Delete Enrolee");
    private JButton buttonPrev = new JButton("Previous");
    private JButton buttonNext = new JButton("Next");
    private JButton buttonFind = new JButton("Find");
    private JButton buttonDeleteLowGrades = new JButton("Delete enrolees with grade lower");

    private JButton buttonSaveList = new JButton("Save List");

    private JTextField fieldRegNumber = new JTextField();
    private JTextField fieldFirstName = new JTextField();
    private JTextField fieldMiddleName = new JTextField();
    private JTextField fieldLastName = new JTextField();
    private JTextField fieldBirthYear = new JTextField();
    private JTextField fieldExam1 = new JTextField();
    private JTextField fieldExam2 = new JTextField();
    private JTextField fieldExam3 = new JTextField();
    private JTextField fieldAverageMark = new JTextField();

    private int currentPageIndex = 0;

    private ButtonListener buttonListener = new ButtonListener();

    private boolean useTempList = false;
    private List<Enrolee> tempList;

    public UserInterface()
    {
        //Window title
        super("Enrolee Info-Base");
        //Window properties
        this.setBounds(100, 100, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Button events
        buttonShowAll.addActionListener(buttonListener);
        buttonFindEnrolee.addActionListener(buttonListener);
        buttonFind.addActionListener(buttonListener);
        buttonMainMenu.addActionListener(buttonListener);
        buttonDeleteEnrolee.addActionListener(buttonListener);
        buttonNext.addActionListener(buttonListener);
        buttonPrev.addActionListener(buttonListener);
        buttonFindLowGrades.addActionListener(buttonListener);
        buttonDeleteLowGrades.addActionListener(buttonListener);
        buttonAddEnrolee.addActionListener(buttonListener);
        buttonAddNewEnrolee.addActionListener(buttonListener);

        buttonSaveList.addActionListener(buttonListener);

        OpenStartWindow();
    }

    public void clearFields()
    {
        fieldRegNumber.setText("");
        fieldBirthYear.setText("");
        fieldFirstName.setText("");
        fieldMiddleName.setText("");
        fieldLastName.setText("");
        fieldExam1.setText("");
        fieldExam2.setText("");
        fieldExam3.setText("");
        fieldAverageMark.setText("");
        revalidate();
        repaint();
    }

    public void OpenStartWindow()
    {
        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(6, 1, 0, 10));
        container.add(new JLabel("Welcome"));
        container.add(buttonShowAll);
        container.add(buttonFindEnrolee);
        container.add(buttonAddEnrolee);
        container.add(buttonFindLowGrades);
        container.add(buttonSaveList);
        revalidate();
        repaint();
    }

    public void OpenShowAllWindow()
    {
        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(4, 1, 0, 10));
        JTextArea textArea = new JTextArea(6, 40);
        textArea.setEditable(false);
        container.add(textArea);
        container.add(new JScrollPane(textArea));
        container.add(buttonNext);
        container.add(buttonPrev);
        container.add(buttonMainMenu);
        textArea.append("Total found: " + (useTempList ? tempList.size() : Main.infoBase.getListSize()) + "\n");
        Enrolee e = useTempList ? tempList.get(currentPageIndex) : Main.infoBase.getEnrolee(currentPageIndex);
        if(e != null)
        {
            textArea.append(e.getInfo());
        }
        revalidate();
        repaint();
    }

    public void OpenFindEnroleeWindow()
    {
        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(3, 1, 0, 10));
        container.add(fieldMiddleName);
        container.add(buttonFind);
        container.add(buttonMainMenu);
        revalidate();
        repaint();
    }

    public void OpenFindLowGradesWindow()
    {
        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(3, 1, 0, 10));
        container.add(fieldAverageMark);
        container.add(buttonDeleteLowGrades);
        container.add(buttonMainMenu);
        fieldAverageMark.setToolTipText("Enter a number between 1 & 12");
        revalidate();
        repaint();
    }

    public void OpenAddEnroleeWindow()
    {
        Container container = this.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(10, 1, 0, 10));
        container.add(new JLabel("Reg Number"));
        container.add(fieldRegNumber);
        container.add(new JLabel("First name"));
        container.add(fieldFirstName);
        container.add(new JLabel("Middle name"));
        container.add(fieldMiddleName);
        container.add(new JLabel("Last name"));
        container.add(fieldLastName);
        container.add(new JLabel("Birth Year"));
        container.add(fieldBirthYear);
        container.add(new JLabel("Exam 1"));
        container.add(fieldExam1);
        container.add(new JLabel("Exam 2"));
        container.add(fieldExam2);
        container.add(new JLabel("Exam 3"));
        container.add(fieldExam3);
        container.add(new JLabel("Average Mark"));
        container.add(fieldAverageMark);
        container.add(buttonAddNewEnrolee);
        container.add(buttonMainMenu);
        revalidate();
        repaint();
    }

    private static int getValidIntFromString(String originalValue, int min, int max, JTextComponent debugField)
    {
        int result;
        try
        {
            result = Integer.parseInt(originalValue);
            if(result >= min && result <= max)
            {
                return result;
            }
            else
            {
                if(debugField != null) debugField.setText("Please, enter a number between" + min + " & " + max);
            }
        }
        catch (NumberFormatException e)
        {
            if(debugField != null) debugField.setText("Please, enter a number between" + min + " & " + max);
        }

        return -1;
    }

    class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == buttonNext)
            {
                if(currentPageIndex >= (useTempList ? tempList.size() : Main.infoBase.getListSize()) - 1) currentPageIndex = 0;
                else
                {
                    currentPageIndex++;
                }
                OpenShowAllWindow();
            }
            else if(event.getSource() == buttonPrev)
            {
                if(currentPageIndex <= 0) currentPageIndex = (useTempList ? tempList.size() : Main.infoBase.getListSize()) - 1;
                else
                {
                    currentPageIndex--;
                }
                OpenShowAllWindow();
            }
            else if(event.getSource() == buttonShowAll)
            {
                OpenShowAllWindow();
            }
            else if(event.getSource() == buttonMainMenu)
            {
                clearFields();
                useTempList = false;
                OpenStartWindow();
            }
            else if(event.getSource() == buttonDeleteEnrolee)
            {

            }
            else if(event.getSource() == buttonFindEnrolee)
            {
                OpenFindEnroleeWindow();
            }
            else if(event.getSource() == buttonFind)
            {
                tempList = new ArrayList<Enrolee>();
                int searchIndex = -1;
                while(searchIndex != -2)
                {
                    searchIndex = Main.infoBase.getEnroleeIndex(fieldMiddleName.getText(), searchIndex + 1);
                    if(searchIndex == -2) break;
                    tempList.add(Main.infoBase.getEnrolee(searchIndex));
                }

                if(!tempList.isEmpty())
                {
                    useTempList = true;
                    currentPageIndex = 0;
                    OpenShowAllWindow();
                }
                else
                {
                    fieldMiddleName.setText("Can't find enrolee");
                }
            }
            else if(event.getSource() == buttonFindLowGrades)
            {
                OpenFindLowGradesWindow();
            }
            else if(event.getSource() == buttonDeleteLowGrades)
            {
                int thresholdMark;
                try
                {
                    thresholdMark = Integer.parseInt(fieldAverageMark.getText());
                    if(thresholdMark > 0 && thresholdMark <= 12)
                    {
                        fieldAverageMark.setText("Deleted: " + Main.infoBase.deleteLowGrades(thresholdMark));
                    }
                    else
                    {
                        fieldAverageMark.setText("Please, enter a number between 1 & 12");
                    }
                }
                catch (NumberFormatException e)
                {
                    fieldAverageMark.setText("Please, enter a number between 1 & 12");
                }
            }
            else if(event.getSource() == buttonAddEnrolee)
            {
                OpenAddEnroleeWindow();
            }
            else if(event.getSource() == buttonAddNewEnrolee)
            {
                int regNum, birthY, exam1, exam2, exam3, avMark;

                regNum = getValidIntFromString(fieldRegNumber.getText(), 1, 99999, fieldRegNumber);
                birthY = getValidIntFromString(fieldBirthYear.getText(), 1980, 3000, fieldBirthYear);
                exam1 = getValidIntFromString(fieldExam1.getText(), 0, 200, fieldExam1);
                exam2 = getValidIntFromString(fieldExam2.getText(), 0, 200, fieldExam2);
                exam3 = getValidIntFromString(fieldExam3.getText(), 0, 200, fieldExam3);
                avMark = getValidIntFromString(fieldAverageMark.getText(), 0, 12, fieldAverageMark);

                boolean flag = regNum != -1 && birthY != -1 && exam1 != -1 && exam2 != -1 && exam3 != -1;
                if(fieldFirstName.getText().equals(""))
                {
                    fieldFirstName.setText("Enter a name here!");
                    flag = false;
                }
                if(fieldMiddleName.getText().equals(""))
                {
                    fieldMiddleName.setText("Enter a surname here!");
                    flag = false;
                }
                if(fieldLastName.getText().equals(""))
                {
                    fieldLastName.setText("Enter a lastname here!");
                    flag = false;
                }

                if(flag)
                {
                    Main.infoBase.addEnrolee(regNum, fieldFirstName.getText(), fieldMiddleName.getText(), fieldLastName.getText(), birthY, new int[] {exam1, exam2, exam3}, avMark);
                    clearFields();
                    fieldRegNumber.setText("Enrolee Added");
                    revalidate();
                    repaint();
                }
            }
            else if(event.getSource() == buttonSaveList)
            {
                Main.infoBase.exportList();
            }
        }
    }
}