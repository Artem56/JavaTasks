package ru.ncedu.java.SolomatinAA.CurriculumVitae;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Artem Solomatin on 04.04.17.
 * NetCracker
 */
public class TestCurriculumVitae {
    CurriculumVitaeImpl test;
    String phoneNumbers = "lol Artem Solomatin Kek 9854172060 Aaa kek 701239";

    @Before
    public void setUp(){

        test = new CurriculumVitaeImpl();
        test.setText(phoneNumbers);
    }

    @Test
    public void getPhonesTest(){
        List<CurriculumVitae.Phone> phones = test.getPhones();
    }

    @Test
    public void getNameTest(){

    }

    @Test
    public void replaceTest(){
        test.updateLastName("Azaza");
        //System.out.println(test.getText());

        test.updatePhone(new CurriculumVitae.Phone("9854172060"), new CurriculumVitae.Phone("1234567890"));

        List ph = test.getPhones();
        /*for(Object p : ph){
            System.out.println(((CurriculumVitae.Phone) p).getNumber() + "  " + ((CurriculumVitae.Phone) p).getAreaCode() + "  " + ((CurriculumVitae.Phone) p).getExtension());
        }*/
    }

    @Test
    public void hideTest(){
        System.out.println(test.getText());
        test.hide("Artem");

        test.hidePhone("9854172060");
        System.out.println(test.getText());
        test.unhideAll();
        System.out.println(test.getText());
    }
}
