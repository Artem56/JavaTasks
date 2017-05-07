package ru.ncedu.java.SolomatinAA.CurriculumVitae;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Solomatin on 04.04.17.
 * NetCracker
 */
public class TestCurriculumVitae {
    CurriculumVitaeImpl test;
    String phoneNumbers = "lol Artem. K. Solomatin   9854172060 Aaa Kek Azaz 701239";

    @BeforeClass
    public void setUp(){
        test = new CurriculumVitaeImpl();
    }

    @Before
    public void setting(){
        test.setText(phoneNumbers);
    }

    @Test
    public void getPhonesTest(){

        List<CurriculumVitae.Phone> phones = test.getPhones();
        for(CurriculumVitae.Phone ph : phones){
            System.out.println(ph.getNumber());
        }
    }

    @Test
    public void getNameTest(){
        System.out.println("full name  " + test.getFullName());
        System.out.println("first name  " + test.getFirstName());
        System.out.println("middle name  " + test.getMiddleName());
        System.out.println("last name  " + test.getLastName());
    }

    @Test
    public void replaceTest(){
        test.updateLastName("Azaza");
        //System.out.println(test.getText());

        test.updatePhone(new CurriculumVitae.Phone("9854172060"), new CurriculumVitae.Phone("1234567890"));

        //List ph = test.getPhones();
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
