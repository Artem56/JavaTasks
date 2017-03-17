package ru.ncedu.java.SolomatinAA.Employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Artem Solomatin on 15.03.17.
 * Employee
 */

/*
Убрать 2 метода
 */
public class TestEmployee {
    private ImplEmployee a;
    private ImplEmployee b;
    private ImplEmployee c;
    private ImplEmployee d;
    private ImplEmployee e;

    @Before
    public void setUp(){
        a = new ImplEmployee("God", "Allah", null);
        b = new ImplEmployee("Boss", "First", a);
        c = new ImplEmployee("Boss", "Second", a);
        d = new ImplEmployee("Baker", "B", b);
        e = new ImplEmployee("Baker", "C", c);
    }

    @Test
    public void testSalary() throws Exception{
        a.increaseSalary(1000);
        e.increaseSalary(1);

        Assert.assertEquals(2000,a.getSalary());
        Assert.assertEquals(1001, e.getSalary());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals("Boss", c.getFirstName());
    }

    @Test
    public void testGetFullName() throws Exception {
        Assert.assertEquals("God Allah", a.getFullName());
    }

    @Test
    public void testManager() throws Exception {
        e.setManager(b);
        Assert.assertEquals("Boss First", e.getManagerName());
    }

    @Test
    public void testGetTopManager() throws Exception {
        Assert.assertEquals(a, e.getTopManager());
        Assert.assertEquals(a, d.getTopManager());
    }


}
