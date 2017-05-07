package ru.ncedu.java.SolomatinAA.Checker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Artem Solomatin on 05.05.17.
 * NetCracker
 */
public class CheckerImpl implements Checker {
    @Override
    public Pattern getPLSQLNamesPattern() {
        return Pattern.compile("^([A-Za-z][\\$\\w]{0,29})$");
    }

    @Override
    public Pattern getHrefURLPattern() {
        return Pattern.compile("^(\\<\\s*[Aa]\\s*[Hh][Rr][Ee][Ff]\\s*\\=\\s*(\"(\\s*[\\w\\.\\-]+\\s*)*\"|[\\w\\.\\-]+)\\s*(/)?\\>)$");
    }

    @Override
    public Pattern getEMailPattern() {
        return Pattern.compile("^([A-Za-z0-9][\\w\\.\\-]{0,20}[A-Za-z0-9]\\@([A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9]\\.)+(com|ru|net|org))$");
    }

    @Override
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        boolean state = false;
        if (inputString == null && pattern == null){
            return true;
        } else if (inputString == null || pattern == null){
            throw new IllegalArgumentException();
        }else{
            Matcher matcher = pattern.matcher(inputString);
            if (matcher.find()){
                state = true;
            }
        }
        return state;
    }

    @Override
    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        List<String> result = new ArrayList<>();
        if (inputString == null || pattern == null){
            throw new IllegalArgumentException();
        }else{
            Matcher matcher = pattern.matcher(inputString);
            while(matcher.find()){
                result.add(matcher.group());
            }
        }
        return result;
    }
}
