package ru.ncedu.java.SolomatinAA.BusinessCard;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Artem Solomatin on 16.03.17.
 * NetCracker
 */
public class BusinessCardImpl implements BusinessCard {
    private String name;
    private String surname;
    private String department;
    private String birthDate;
    private Character gender;
    private int salary;
    private long phoneNumber;

    public BusinessCardImpl(){

    }

    public BusinessCardImpl(String name, String surname, String department, String birthDate, Character gender, int salary, long phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.birthDate = birthDate;
        this.gender = gender;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method obtains (via Scanner) information from an input stream
     * that contains the following information about an Employee:<br/>
     * Name - String<br/>
     * Last name - String<br/>
     * Department - String <br/>
     * Birth date - String in format: "DD-MM-YYYY", where DD - two-digits birth date,
     * MM - two-digits month of birth, YYYY - year of birth<br/>
     * Gender : F or M - Character<br/>
     * Salary : number from 100 to 100000<br/>
     * Phone number : 10-digits number<br/>
     * All entries are separated with ";" sign<br/>
     * The format of input is the following:<br/>
     * Name;Last name;Department;Birth date;Gender;Salary;Phone number
     *
     * @param scanner Data source
     * @return Business Card
     * @throws InputMismatchException Thrown if input value
     *                                does not correspond to the data format given above (for example,
     *                                if phone number is like "AAA", or date format is incorrect, or salary is too high)
     * @throws NoSuchElementException Thrown if input stream hasn't enough values
     *                                to construct a BusinessCard
     */
    @Override
    public BusinessCard getBusinessCard(Scanner scanner) {
        scanner.useDelimiter(";");

        String name = null;
        String surname = null;
        String department = null;
        String birthDate = null; //"DD-MM-YYYY"
        Character gender = null; //F or M
        int salary = -1; // number from 100 to 100000 Phone number : 10-digits number
        long phoneNumber = -1;



        if (scanner.hasNext()){
            name = scanner.next();
        }
        if (scanner.hasNext())
            surname = scanner.next();
        if (scanner.hasNext())
            department = scanner.next();
        if (scanner.hasNext()){
            birthDate = scanner.next();
            //правильная ли введена дата
        }
        if (scanner.hasNext()){
            String gndr = scanner.next();
            switch (gndr) {
                case "F":
                    gender = new Character('F');
                    break;
                case "M":
                    gender = new Character('M');
                    break;
                default:
                    throw new InputMismatchException();
            }
        }
        if (scanner.hasNext()){
            String numb = scanner.next();
            try{
                salary = Integer.parseInt(numb);
            }catch(NumberFormatException nfe){
                throw new InputMismatchException();
            }
            if (salary < 100 || salary > 100000 )
                throw new InputMismatchException();
        }
        if (scanner.hasNext()){
            String phn = scanner.next();
            if(phn.length() != 10) {
                throw new InputMismatchException();
            }
            phoneNumber = Long.valueOf(phn);
        }
        if (name == null || surname == null || department == null || birthDate == null || gender == null ||
                salary == -1 || phoneNumber == -1) {
            throw new NoSuchElementException();
        }
        return new BusinessCardImpl(name, surname, department, birthDate, gender, salary, phoneNumber);
    }

    /**
     * @return Employee Name and Last name separated by space (" "), like "Chuck Norris"
     */
    @Override
    public String getEmployee() {
        return name + " " + surname;
    }

    /**
     * @return Employee Department name, like "DSI"
     */
    @Override
    public String getDepartment() {
        return department;
    }

    /**
     * @return Employee Salary, like 1000
     */
    @Override
    public int getSalary() {
        return salary;
    }

    /**
     * @return Employee Age in years, like 69
     */
    @Override
    public int getAge() {
        long currentTime = System.currentTimeMillis();
        long birthTime;

        Calendar diff = Calendar.getInstance();
        diff.setTimeInMillis(currentTime);



        return 0;//current.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
    }

    /**
     * @return Employee Gender: either "Female" or "Male"
     */
    @Override
    public String getGender() {
        if(gender.toString().equals("F")) return "Female";
        if(gender.toString().equals("M"))return "Male";
        return null;
    }

    /**
     * @return Employee Phone Number in the following format: "+7 123-456-78-90"
     */
    @Override
    public String getPhoneNumber() {
        String number = Long.toString(phoneNumber);
        return "+7 " + number.substring(0, 3) + "-" +
                number.substring(3, 6) + "-" +
                number.substring(6, 8) + "-" +
                number.substring(8, 10);
    }
}
