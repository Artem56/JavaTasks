package ru.ncedu.java.SolomatinAA.Reflections;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Artem Solomatin on 05.05.17.
 * NetCracker
 */
public class ReflectionsImpl implements Reflections {
    @Override
    public Object getFieldValueByName(Object object, String fieldName) throws NoSuchFieldException {
        if (object == null || fieldName == null){
            throw new NullPointerException();
        }
        Class clazz = object.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        }catch (Exception e){
            throw new NoSuchFieldException();
        }
        Object result;
        try{
            result = field.get(object);
        }catch (IllegalAccessException e){
            throw new NoSuchFieldException();
        }
        return result;
    }

    @Override
    public Set<String> getProtectedMethodNames(Class clazz) {
        if (clazz == null){
            throw new NullPointerException();
        }
        Set<String> list = new HashSet<>();
        Method[] fields = clazz.getDeclaredMethods();
        for (Method field : fields) {
            field.setAccessible(true);
            int fieldModifiers = field.getModifiers();
            if (Modifier.isProtected(fieldModifiers)) {
                list.add(field.getName());
            }
        }
        return list;
    }

    @Override
    public Set<Method> getAllImplementedMethodsWithSupers(Class clazz) {
        if (clazz == null){
            throw new NullPointerException();
        }

        Class tmp = clazz;
        Set<Method> list = new HashSet<>();

        do{
            Method[] methods = tmp.getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                list.add(method);
            }
            tmp = tmp.getSuperclass();
        }while(!tmp.equals(Object.class));

        Method[] methods = Object.class.getDeclaredMethods();

        for (Method method : methods) {
            method.setAccessible(true);
            list.add(method);
        }
        return list;
    }

    @Override
    public List<Class> getExtendsHierarchy(Class clazz) {
        if (clazz == null){
            throw new NullPointerException();
        }
        List<Class> list = new ArrayList<>();
        while(!clazz.equals(Object.class)){
            clazz = clazz.getSuperclass();
            list.add(clazz);
        }

        return list;
    }

    @Override
    public Set<Class> getImplementedInterfaces(Class clazz) {
        if (clazz == null){
            throw new NullPointerException();
        }

        Set<Class> result = new HashSet<>();
        Class[] interfaces = clazz.getInterfaces();

        Collections.addAll(result, interfaces);
        return result;
    }

    @Override
    public List<Class> getThrownExceptions(Method method) {
        if (method == null){
            throw new NullPointerException();
        }
        List<Class> result = new ArrayList<>();
        Class[] exceptions = method.getExceptionTypes();

        Collections.addAll(result, exceptions);
        return result;
    }

    @Override
    public String getFooFunctionResultForDefaultConstructedClass() {
        Class clazz = SecretClass.class;
        String result = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);

            Object secretClass = constructor.newInstance();

            Method method = clazz.getDeclaredMethod("foo");
            method.setAccessible(true);

            result = (String) method.invoke(secretClass);

        }catch (NoSuchMethodException e){
            System.out.println("Error in constructor");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.out.println("Some error");
        }
        return result;
    }

    @Override
    public String getFooFunctionResultForClass(String constructorParameter, String string, Integer... integers) {
        Class clazz = SecretClass.class;
        String result = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(new Class<?>[]{String.class});
            constructor.setAccessible(true);

            Object secretCass = constructor.newInstance(new String[] {constructorParameter});

            Method
                    method = clazz.getDeclaredMethod("foo", new Class<?>[]{String.class, Integer[].class});
            method.setAccessible(true);

            result = (String) method.invoke(secretCass, new Object[]{string, integers});

        }catch (NoSuchMethodException e){
            System.out.println("Error in constructor");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println("Some error");
        }
        return result;
    }
}
