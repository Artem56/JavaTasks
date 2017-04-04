package ru.ncedu.java.SolomatinAA.WordCounter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by Artem Solomatin on 18.03.17.
 * NetCracker
 */
/*
Поиск слов в тексте не работает со строками, которые содержатся в других строка. Если добавить пробелы, то перестают работать. Сейчас не работает, если одинаковые слова стоят подряд
 */
public class TestWordCounter {
    WordCounterImpl test;
    String text = "   2times  1time 2times 3times  2timeSecond  3times       2timeSecond 3times";
    Map<String, Long> unsortedExpected;


    @Before
    public void setUp(){
        test = new WordCounterImpl();

        unsortedExpected = new HashMap<>();
        unsortedExpected.put("1time", 1L);
        unsortedExpected.put("2times", 2L);
        unsortedExpected.put("2timesecond", 2L);
        unsortedExpected.put("3times", 3L);
    }

    @Test
    public void setGetText(){
        test.setText(text);
        Assert.assertEquals(text, test.getText());
    }

    @Test
    public void getWordCountsTest(){
        test.setText(text);
        Map<String, Long> map = test.getWordCounts();

        Assert.assertEquals(unsortedExpected, map);
    }

    @Test
    public void sortWordTest(){
        test.setText(text);
        Map<String, Long> map = test.getWordCounts();
        List<Map.Entry<String, Long>> list1 = test.sortWordCounts(null);
        List<Map.Entry<String, Long>> list2 = test.sortWordCounts(map);

        Assert.assertEquals(null, list1);
        System.out.println(list2);
    }
}
