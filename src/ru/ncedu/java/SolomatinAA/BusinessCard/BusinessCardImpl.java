package ru.ncedu.java.SolomatinAA.BusinessCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Artem Solomatin on 16.03.17.
 * NetCracker
 */
public class BusinessCardImpl implements BusinessCard {
    private String name;
    private String lastName;
    private String department;
    private String birthDate; //"DD-MM-YYYY"
    private char gender; //F or M
    private int salary; // number from 100 to 100000 Phone number : 10-digits number
    private String phoneNumber;
    private Calendar brthDate;

    public BusinessCardImpl(){

    }

    private BusinessCardImpl(String name, String lastName, String department, String birthDate, char gender, int salary, String phoneNumber, Calendar brthDate){
        this.name = name;
        this.lastName = lastName;
        this.department = department;
        this.birthDate = birthDate;
        this.gender = gender;
        this.salary = salary;
        this.brthDate = brthDate;
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

        String nameArg = null;
        String lastNameArg = null;
        String departmentArg = null;
        String birthDateArg = null; //"DD-MM-YYYY"
        char genderArg = 'a'; //F or M
        int salaryArg = -1; // number from 100 to 100000 Phone number : 10-digits number
        String phoneNumberArg = null;
        Calendar brthDateArg = null;

        if (scanner.hasNext()){
            nameArg = scanner.next();
        }
        if (scanner.hasNext())
            lastNameArg = scanner.next();
        if (scanner.hasNext())
            departmentArg = scanner.next();
        if (scanner.hasNext()){
            birthDateArg = scanner.next();
            brthDateArg = this.setDate(birthDateArg);
            if ( brthDateArg == null )
                throw new InputMismatchException();
        }
        if (scanner.hasNext()){
            String gndr = scanner.next();
            if (!(gndr.equals("F") || gndr.equals("M")))
                throw new InputMismatchException();
            genderArg = gndr.charAt(0);
        }
        if (scanner.hasNext()){
            String numb = scanner.next();
            try{
                salaryArg = Integer.parseInt(numb);
            }catch(NumberFormatException nfe){
                throw new InputMismatchException();
            }
            if (salaryArg < 100 || salaryArg > 100000 )
                throw new InputMismatchException();
        }
        if (scanner.hasNext()){
            String numb = scanner.next();
            if(numb.length() != 10)
                throw new InputMismatchException();
            phoneNumberArg = numb.substring(0,10);
        }
        if (phoneNumberArg == null)
            throw new NoSuchElementException();

        return new BusinessCardImpl(nameArg, lastNameArg, departmentArg, birthDateArg, genderArg, salaryArg,phoneNumberArg, brthDateArg);
    }

    private Calendar setDate(String stringToValidate) {
        if(stringToValidate == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        Date date;

        try{
            date = sdf.parse(stringToValidate);
        }catch (ParseException e){
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    /**
     * @return Employee Name and Last name separated by space (" "), like "Chuck Norris"
     */
    @Override
    public String getEmployee() {
        return name + " " + lastName;
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
	        Calendar today = Calendar.getInstance();
	        Calendar birth = Calendar.getInstance();
	        birth.set(Integer.parseInt(birthDate.substring(6)),
	                Integer.parseInt(birthDate.substring(3, 5)),
	                Integer.parseInt(birthDate.substring(0, 2)));

	        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH) ||
	                today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) &&
	                        today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
	            age--;
	        }

	        return age;
    }

    /**
     * @return Employee Gender: either "Female" or "Male"
     */
    @Override
    public String getGender() {
        return (gender == 'F')?("Female"):("Male");
    }

    /**
     * @return Employee Phone Number in the following format: "+7 123-456-78-90"
     */
    @Override
    public String getPhoneNumber() {
        String number = phoneNumber;
        return "+7 "+ number.substring(0,3) +
                "-" + number.substring(3,6) +
                "-" + number.substring(6,8) +
                "-" + number.substring(8,10);
    }
}
