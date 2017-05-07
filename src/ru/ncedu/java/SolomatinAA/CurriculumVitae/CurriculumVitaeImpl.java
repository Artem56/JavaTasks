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
    private static final String FULL_NAME_PATTERN = "(^|[^A-Za-z])(([A-Z][a-z]*[a-z\\.]) ([A-Z][a-z]*[a-z\\.])( ([A-Z][a-z]*[a-z\\.]))?)";
    private static final String PHONE_PATTERN =
            "(\\(?([1-9][0-9]{2})\\)?[-. ]*)?([1-9][0-9]{2})[-. ]*(\\d{2})[-. ]*(\\d{2})(\\s*ext\\.?\\s*([0-9]+))?";
    private Map<String, String> mem = new HashMap<>();


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
        if (text == null || text.equals("")) {
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
            List <Phone> phones = new ArrayList<>();
            String text = this.getText();
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String area = matcher.group(2);
                String ex = matcher.group(7);
                String number = matcher.group();
                int areaCode = -1;
                int extension = -1;
                if (area != null) {
                    areaCode = Integer.parseInt(area);
                }
                if (ex != null) {
                    extension = Integer.parseInt(ex);
                }
                phones.add(new Phone(number, areaCode, extension));
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        Pattern pattern = Pattern.compile(FULL_NAME_PATTERN);
        Matcher matcher = pattern.matcher(getText());
        if (matcher.find()){
            return matcher.group(2);
        }
        else throw new NoSuchElementException();
    }

    /**
     * Returns the first name from the CV (the first word of {@link #getFullName()}).
     *
     * @throws NoSuchElementException If the CV does not contain a full name.
     * @throws IllegalStateException  If the CV text was not set by {@link #setText(String)}.
     */
    @Override
    public String getFirstName() throws NoSuchElementException, IllegalStateException {
        if (text == null || text.equals("")) {
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        String[] arr = getFullName().split(" ");
        if (arr.length == 3) {
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        String[] arr = getFullName().split(" ");
        return arr[arr.length - 1];
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }

        text = getText().replace(getLastName(), newLastName);
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        boolean state = false;
        if(text.contains(oldPhone.getNumber())){
            state = true;
            this.text = text.replaceAll(oldPhone.getNumber(), newPhone.getNumber());
        }

        if(!state){
            throw new IllegalArgumentException();
        }
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        String text = getText();
        String copy = piece;
        if (text.contains(piece)){
            Pattern pattern = Pattern.compile("([^ .@])");
            Matcher matcher = pattern.matcher(piece);
            while(matcher.find()){
                copy = copy.replace(matcher.group(), "X");
            }
            mem.put(copy, piece);
            this.text = text.replaceAll(piece, copy);
        }else {
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        String text = getText();
        String copy = phone;
        if (text.contains(phone)){
            Pattern pattern = Pattern.compile("(\\d)");
            Matcher matcher = pattern.matcher(phone);
            while(matcher.find()){
                copy = copy.replace(matcher.group(), "X");
            }
            mem.put(copy, phone);
            String result = text.replace(phone, copy);
            this.setText(result);
        }else {
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
        if (text == null || text.equals("")) {
            throw new IllegalStateException();
        }
        String text = getText();
        int count = 0;
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(mem.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> a, Map.Entry<String, String> b) {
                return b.getKey().length() - a.getKey().length();
            }
        });

        for (Map.Entry<String, String> fin: mem.entrySet()) {
            Pattern pattern = Pattern.compile(fin.getKey());
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()){
                text = text.replaceAll(matcher.group(),fin.getValue());
                count++;
            }
        }
        mem.clear();

        setText(text);
        return count;
    }
}
