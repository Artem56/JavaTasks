package ru.ncedu.java.SolomatinAA.ArrayVector;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Artem Solomatin on 15.03.17.
 * NetCracker
 */
public class ImplArrayVector implements ArrayVector {
    private double[] x;

    public ImplArrayVector() {
    }

    /**
     * Задает все элементы вектора (определяет длину вектора).
     * Передаваемый массив не клонируется.
     *
     * @param elements Не равен null
     */
    @Override
    public void set(double... elements) {
        System.arraycopy(elements, 0, x, 0, elements.length);
    }

    /**
     * Возвращает все элементы вектора. Массив не клонируется.
     */
    @Override
    public double[] get() {
        return x;
    }

    /**
     * Возвращает копию вектора (такую, изменение элементов
     * в которой не приводит к изменению элементов данного вектора).<br/>
     * Рекомендуется вызвать метод clone() у самого массива или использовать
     * {@link System#arraycopy(Object, int, Object, int, int)}.
     */
    @Override
    public ArrayVector clone() {
        ImplArrayVector a = new ImplArrayVector();
        a.set(this.x);
        return a;
    }

    /**
     * Возвращает число элементов вектора.
     */
    @Override
    public int getSize() {
        if(x == null) return 0;
        return x.length;
    }

    /**
     * Изменяет элемент по индексу.
     *
     * @param index В случае выхода индекса за пределы массива:<br/>
     *              а) если index<0, ничего не происходит;<br/>
     *              б) если index>=0, размер массива увеличивается так, чтобы index стал последним.
     * @param value
     */
    @Override
    public void set(int index, double value) {
        if(index < 0 || x == null) {
            return;
        }
        else if(index < x.length) {
            x[index] = value;
        }
        else{
            x = Arrays.copyOf(x, index+1);
            x[index] = value;
        }

    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index В случае выхода индекса за пределы массива
     *              должно генерироваться ArrayIndexOutOfBoundsException
     */
    @Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if(index >= x.length ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return x[index];
    }

    /**
     * Возвращает максимальный элемент вектора.
     */
    @Override
    public double getMax() {
        int length = x.length;
        Arrays.sort(x);
        return x[length - 1];
    }

    /**
     * Возвращает минимальный элемент вектора.
     */
    @Override
    public double getMin() {
        Arrays.sort(x);
        return x[0];
    }

    /**
     * Сортирует элементы вектора в порядке возрастания.
     */
    @Override
    public void sortAscending() {
        Arrays.sort(x);
    }

    /**
     * Умножает вектор на число.<br/>
     * Замечание: не пытайтесь использовать безиндексный цикл foreach:
     * для изменения элемента массива нужно знать его индекс.
     *
     * @param factor
     */
    @Override
    public void mult(double factor) {
        for(int i = 0;i < x.length;i++){
            x[i] *= factor;
        }
    }

    /**
     * Складывает вектор с другим вектором, результат запоминает в элементах данного вектора.<br/>
     * Если векторы имеют разный размер, последние элементы большего вектора не учитываются<br/>
     * (если данный вектор - больший, его размер менять не надо, просто не меняйте последние элементы).
     *
     * @param anotherVector Не равен null
     * @return Ссылка на себя (результат сложения)
     */
    @Override
    public ArrayVector sum(ArrayVector anotherVector) {
        for(int i = 0;i < x.length && i<anotherVector.getSize();i++){
            x[i] += anotherVector.get(i);
        }
        return this;
    }

    /**
     * Возвращает скалярное произведение двух векторов.<br/>
     * Если векторы имеют разный размер, последние элементы большего вектора не учитываются.
     *
     * @param anotherVector Не равен null
     */
    @Override
    public double scalarMult(ArrayVector anotherVector) {
        double res = 0;
        for(int i = 0;i < x.length && i<anotherVector.getSize();i++){
            res += x[i] * anotherVector.get(i);
        }
        return res;
    }

    /**
     * Возвращает евклидову норму вектора (длину вектора
     * в n-мерном евклидовом пространстве, n={@link #getSize()}).
     * Квадрат нормы вектора равен скалярному произведению вектора на себя.
     */
    @Override
    public double getNorm() {
        return Math.sqrt(scalarMult(this));
    }
}
