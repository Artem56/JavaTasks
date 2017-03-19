package ru.ncedu.java.SolomatinAA.ArrayVector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Artem Solomatin on 15.03.17.
 * NetCracker
 */
/*
в первом методе что значит массив не клонируется?
в функции 6 произойдет ли выход из функции с генерацией исключения?(еслинет, то добавить return)
в функции 7 возвращается максимальное или минимальное значение? в зависимости от этого пересмотреть 7, 8, 9
 */
public class TestArrayVector {
    double[] x= new double[]{3, 5, 2, 8, 0, -4, 1.4, 3.14, 12000, -123, 0.2};
    double[] expected = new double[]{3, 5, 2, 8, 0, -4, 1.4, 3.14, 12000, -123, 0.2, 13579};
    double expectedScalarMult = 144015258.9;
    ImplArrayVector test;
    ImplArrayVector test2;

    @Before
    public void setUp(){
        test = new ImplArrayVector();
        test2 = new ImplArrayVector();
    }

    @Test
    public void setGetTest(){
        test.set(x);
        double[] actual = test.get();

        Assert.assertEquals(x, actual);
    }

    @Test
    public void cloneAndSizeTest(){
        test.set(x);
        ArrayVector actual = test.clone();

        Assert.assertEquals(test.get(), actual.get());
        Assert.assertEquals(11, test.getSize());

    }

    @Test
    public void setGetElementTest() {
        test.set(x);

        test.set(-1, 1_000_000_000);
        test.set(11, 13579);

        double actual = test.get(6);

        Assert.assertEquals(1.4, actual, 0.01);
        Assert.assertEquals(expected[10], test.get(10), 0.01);
    }

    @Test
    public void maxMinTest(){
        test.set(x);

        Assert.assertEquals(12000, test.getMax(), 0.1);
        Assert.assertEquals(-123, test.getMin(), 0.1);
    }

    @Test
    public void sumTest(){
        test.set(x);
        test2.set(expected);
        ArrayVector sum = test.sum(test2);

        test.mult(2);
        Assert.assertEquals(test, sum);
    }
}
