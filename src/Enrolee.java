import java.io.*;

public class Enrolee implements Serializable
{
    private int registrationNumber;
    private String firstName, middleName, lastName;
    private int birthYear;
    private int[] exams;
    private int averageMark;

    public Enrolee(int regNum, String fname, String sname, String lname, int birthYear, int[] exams, int averageMark)
    {
        this.registrationNumber = regNum;
        this.firstName = fname;
        this.middleName = sname;
        this.lastName = lname;
        this.birthYear = birthYear;
        this.exams = exams;
        this.averageMark = averageMark;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public int getAverageMark()
    {
        return averageMark;
    }

    public String getInfo()
    {
        String out = "Reg Number:\t" + registrationNumber + '\n';
        out += "Name:\t" + firstName + " " + middleName + " " + lastName + "\n";
        out += "Birth year:\t" + birthYear + "\n";
        out += "Exams:\t" + exams[0] + ", " + exams[1] + ", " + exams[2] + "\n";
        out += "Average Mark:\t" + averageMark;
        return out;
    }

    public void writeToFile(FileOutputStream outputStream)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(this);
            //out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Enrolee readFromFile(FileInputStream inputStream)
    {
        try
        {
            ObjectInputStream input = new ObjectInputStream(inputStream);
            Object result = input.readObject();
            //input.close();
            return (Enrolee)result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
