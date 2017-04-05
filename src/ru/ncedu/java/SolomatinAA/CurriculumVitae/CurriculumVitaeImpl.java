package ru.ncedu.java.SolomatinAA.CurriculumVitae;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Artem Solomatin on 04.04.17.
 * NetCracker
 */
public class CurriculumVitaeImpl implements CurriculumVitae {
    private String text;
    private List<Phone> phones = new ArrayList<>();
    public static final String FULL_NAME_PATTERN = "([A-Z][a-z]+\\.?\\s?){2,3}";
    ArrayList hiddenName = new ArrayList();
    ArrayList hiddenPhone = new ArrayList();


    public CurriculumVitaeImpl() {

    }

    /**
     * Sets the text (the main field of the CV).<br/>
     * Implementation note: The text should not be analyzed in this method.<br/>
     *
     * @param text CV text
     */
    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     * It is recommended to call this method in all the other methods of you class.
     *
     * @return The current text of the CV (may be changed by update* methods).
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public String getText() throws IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        return text;
    }

    /**
     * Returns a list of phones in the same order they occur in the CV.<br/>
     * Implementation note: Use {@link #PHONE_PATTERN} to find phones;
     * use groups of that regular expression to get area code and extension from the matches found;
     * if the area code or extension does not exist there, the Phone must store negative value of it.
     *
     * @return A list that can't be null but may be empty (if no one phone is found).
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     * @see Phone
     */
    @Override
    public List<Phone> getPhones() throws IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(getText());
        Phone phone;
        while(matcher.find()){
            int areaCode = -1;
            int extension = -1;
            if (matcher.group(2) != null){
                areaCode = Integer.parseInt(matcher.group(2));
                //System.out.println(areaCode);
            }
            if (matcher.group(7) != null){
                extension = Integer.parseInt(matcher.group(7));
                //System.out.println(extension);
            }
            phone = new Phone(matcher.group(), areaCode, extension);
            //System.out.println(matcher.group());
            //System.out.println(matcher.group(2));
            phones.add(phone);
        }

        return phones;
    }

    /**
     * Returns the full name
     * i.e. the FIRST part of the CV text that satisfies the following criteria:
     * <ol>
     * <li>full name consists of 2 or 3 words separated with a space (' ');</li>
     * <li>each word has 2 or more characters;</li>
     * <li>the first character of the word is upper-case Latin letter (alphabetic character);</li>
     * <li>the last character of the word is either '.' or lower-case Latin letter;</li>
     * <li>non-first and non-last characters of the word can be lower-case Latin letter only.</li>
     * </ol>
     *
     * @return The full name (is exactly equal to its value in the CV text)
     * @throws NoSuchElementException If the CV does not contain a full name that satisfies the criteria.
     * @throws IllegalStateException  If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public String getFullName() throws NoSuchElementException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        Pattern pattern = Pattern.compile(FULL_NAME_PATTERN);
        Matcher matcher = pattern.matcher(getText());
        if(matcher.find()){
            return matcher.group();
        }

        throw new NoSuchElementException();
    }

    /**
     * Returns the first name from the CV (the first word of {@link #getFullName()}).
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException  If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public String getFirstName() throws NoSuchElementException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        return getFullName().split(" ")[0];
    }

    /**
     * Returns the middle name from the CV (the second word of {@link #getFullName()})
     * or null if the full name has two words only.
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException  If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public String getMiddleName() throws NoSuchElementException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        String[] arr = getFullName().split(" ");
        if(arr.length == 3){
            return arr[1];
        }
        return null;
    }

    /**
     * Returns the last name from the CV (the last word of {@link #getFullName()}).
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException  If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public String getLastName() throws NoSuchElementException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        String[] arr = getFullName().split(" ");
        return arr[arr.length  -1];
    }

    /**
     * Replaces the lastName to <code>newLastName</code> in the CV text.
     *
     * @param newLastName Can't be null
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException  If the CV text was not set by {@link #setText(String)}.
     * @see #getLastName()
     */
    @Override
    public void updateLastName(String newLastName) throws NoSuchElementException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        while(true) {
            String lastName = getLastName();
            if(lastName == null){
                throw new NoSuchElementException();
            }
            //System.out.println(lastName);
            //System.out.println(newLastName);
           if(!lastName.equals(newLastName)) {
               //System.out.println(text);
               String str = text.replaceAll(lastName, newLastName);
               setText(str);
               //System.out.println(text);
                break;
            }else{
               break;
           }
        }
    }

    /**
     * Replaces the <code>oldPhone.getNumber()</code> to <code>newPhone.getNumber()</code>
     * in the CV text.
     * Implementation note: using regex here leads to more code than using non-regex methods of {@link String}
     * (or maybe than using non-regex method of {@link String} and a method of {@link StringBuilder}).
     *
     * @param oldPhone Can't be null
     * @param newPhone Can't be null
     * @throws IllegalArgumentException If the CV does not contain a text equal to <code>oldPhone.getNumber()</code>.
     * @throws IllegalStateException    If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public void updatePhone(Phone oldPhone, Phone newPhone) throws IllegalArgumentException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        Pattern pattern = Pattern.compile(oldPhone.getNumber());
        Matcher matcher = pattern.matcher(getText());
        String str;

        //System.out.println(text);
        int ptr = 0;
        while(matcher.find()) {
            str = matcher.replaceAll(newPhone.getNumber());
            setText(str);
            ptr++;
        }
        if(ptr == 0){
            throw new IllegalArgumentException();
        }
        //System.out.println(text);
    }

    /**
     * Finds the <code>piece</code> in the CV text and replaces <code>piece</code>'s characters
     * with 'X' character, excluding the following separating characters: ' ', '.' and '@'.
     * The number of 'X' characters is equal to a number of the replaced characters.<br/>
     * For example: "John A. Smith" is replaced by "XXXX X. XXXXX", "john@hp.com" - by "XXXX@XX.XXX".<br/>
     * This change can be undone by {@link #unhideAll()}.
     *
     * @param piece Can't be null
     * @throws IllegalArgumentException If the CV does not contain a text equal to <code>piece</code>.
     * @throws IllegalStateException    If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public void hide(String piece) throws IllegalArgumentException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        //hiddenName.add(piece);

        Pattern pattern = Pattern.compile(piece);
        Matcher matcher = pattern.matcher(getText());
        String str;
        StringBuffer sb = new StringBuffer("");
        for(int i = 0;i < piece.length();i++) {    //Создаем строку, котрой будем заменять необходимое имя
            sb.append("X");
        }

        //System.out.println("1 " + text);
        int ptr = 0;
        while(matcher.find()) {
            //hiddenName.put(piece, matcher.start());
            //System.out.println("2 " + text);
            hiddenName.add(piece);
            hiddenName.add(matcher.start());
            str = matcher.replaceAll(sb.toString());
            setText(str);
            //System.out.println("3 " + text);
            ptr++;
        }
        if(ptr == 0){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Finds the <code>phone</code> in the CV text
     * and replaces all DIGITS of the <code>phone</code> with 'X' character.<br/>
     * For example: "(123)456 7890" is replaced by "(XXX)XXX XXXX".<br/>
     * This change can be undone by {@link #unhideAll()}.
     *
     * @param phone Can't be null
     * @throws IllegalArgumentException If the CV does not contain a text equal to <code>phone</code>.
     * @throws IllegalStateException    If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public void hidePhone(String phone) throws IllegalArgumentException, IllegalStateException {
        if(text == null || text.equals("")){
            throw new IllegalStateException();
        }
        //hiddenPhone.add(phone);

        Pattern pattern = Pattern.compile(phone);
        Matcher matcher = pattern.matcher(getText());
        String str;
        StringBuffer sb = new StringBuffer("");
        for(int i = 0;i < phone.length();i++) {    //Создаем строку, котрой будем заменять необходимый номер
            sb.append("X");
        }

        int ptr = 0;
        while(matcher.find()) {
            //hiddenPhone.put(phone, matcher.start());
            //System.out.println("start: " + matcher.start());
            hiddenPhone.add(phone);
            hiddenPhone.add(matcher.start());
            //System.out.println("2: " + text);
            str = matcher.replaceAll(sb.toString());
            setText(str);
            //System.out.println("3: " + text);
            //System.out.println("hidden " + hiddenPhone.get(phone) + " size" + hiddenPhone.size());
            ptr++;
        }
        if(ptr == 0){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Undoes all changes made by {@link #hide(String)} and {@link #hidePhone(String)} methods
     * i.e. replaces 'X' pieces of the current CV text ("hidden" pieces inserted by those methods)
     * with appropriate pieces of the original CV text.<br/>
     * Note: there can't be 2 (or more) equal hidden pieces (equal pieces with 'X') in the CV text.<br/>
     * Implementation note: original and hidden pieces should be stored in some collection.
     * Moreover, the collection should be cleared on {@link #setText(String)}.
     *
     * @return The number of replacements made in the CV during unhiding
     * @throws IllegalStateException If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public int unhideAll() throws IllegalStateException {
        char[] charText = text.toCharArray();

        for(int i = 0;i < hiddenName.size();i+=2){
            char[] charName = ((String)hiddenName.get(i)).toCharArray();
            int position = (int)hiddenName.get(i + 1);
            for(int j = 0;j < charName.length;j++){
                charText[position + j] = charName[j];
            }
        }

        for(int i = 0;i < hiddenPhone.size();i+=2){
            char[] charPhone = ((String)hiddenPhone.get(i)).toCharArray();
            int position = (int)hiddenPhone.get(i + 1);
            for(int j = 0;j < charPhone.length;j++){
                charText[position + j] = charPhone[j];
            }
        }
        String str = new String(charText);
        setText(str);

        return 0;
    }
}
