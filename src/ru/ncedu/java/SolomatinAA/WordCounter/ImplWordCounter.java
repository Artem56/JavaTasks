package ru.ncedu.java.SolomatinAA.WordCounter;

import java.io.PrintStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Artem Solomatin on 16.03.17.
 * NetCracker
 */
public class ImplWordCounter implements WordCounter {
    private String text;
    private HashMap<String, Long> entry;

    ImplWordCounter(){
        entry = new HashMap<>();
    }
    /**
     * Принимает текст для анализа
     *
     * @param text текст для анализа
     */
    @Override
    public void setText(String text) {
        this.text = text;

    }

    /**
     * @return текст, переданный для анализа при последнем вызове метода
     * {@link #setText(String) setText}, или <code>null</code>,
     * если указанный метод еще не вызывался или последний раз вызывался
     * с параметром <code>null</code>
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * Возвращает {@link Map}&lt;{@link String}, {@link Long}&gt;,
     * сопоставляющую каждому слову количество его вхождений в анализируемый текст.
     * Все возвращаемые слова должны быть приведены к нижнему регистру.
     *
     * @return результат подсчета количеств вхождений слов
     * @throws IllegalStateException если не задан текст для анализа
     *                               (метод {@link #setText(String) setText} еще не вызывался
     *                               или последний раз вызывался с параметром <code>null</code>)
     */
    @Override
    public Map<String, Long> getWordCounts() {
        if (this.getText() == null || this.getText().equals("")) {
            throw new IllegalStateException();
        }

        String text = this.text.toLowerCase();

        String[] tokens = text.split(" +");
        HashSet<String> words = new HashSet<>();



        for (String token : tokens) {
            System.out.print(token + " ");
            if (!token.equals(" ")) {
                words.add(token);
            }
        }

        Collections.addAll(words, tokens);
        //now we have a set of unique words

        for (String tmp : words) {
            long count = 0L;
            if (!tmp.equals("")) {
                Pattern pattern = Pattern.compile("(^| )+" + tmp + "($| )+");
                System.out.println(pattern + " '" + tmp + "'");    //pre-test
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    count++;
                }
                entry.put(tmp, count);
            }
        }
        return entry;
    }

    /**
     * Возвращает список из {@link Entry Map.Entry}&lt;{@link String}, {@link Long}&gt;,
     * сопоставляющий каждому слову количество его вхождений в анализируемый текст
     * и упорядоченный в прядке убывания количества вхождений слова.<br/>
     * Слова с одинаковым количеством вхождений упорядочиваются в алфавитном порядке (без учета регистра!).<br/>
     * Все возвращаемые слова должны быть приведены к нижнему регистру.
     *
     * @return упорядоченный результат подсчета количеств вхождений слов
     * @throws IllegalStateException если не задан текст для анализа
     *                               (метод {@link #setText(String) setText} еще не вызывался
     *                               или последний раз вызывался с параметром <code>null</code>)
     */
    @Override
    public List<Map.Entry<String, Long>> getWordCountsSorted() {
        return null;
    }

    /**
     * Упорядочивает результат подсчета количеств вхождений слов в порядке убывания количества вхождений.<br/>
     * Слова с одинаковым количеством вхождений упорядочиваются в алфавитном порядке (без учета регистра!).<br/>
     * Реализация этого метода не является обязательной, но если он будет реализован, то реализация
     * метода {@link #getWordCountsSorted()} будет тривиальной.
     *
     * @param orig неупорядоченный результат подсчета
     * @return упорядоченный результат подсчета или <code>null</code>, если <code>orig == null</code>
     */
    @Override
    public List<Map.Entry<String, Long>> sortWordCounts(Map<String, Long> orig) {
        if (orig == null) {
            return null;
        }
        List<Map.Entry<String, Long>> sortedList = new ArrayList<>(orig.entrySet());
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return sortedList;
    }

    /**
     * Распечатывает слова и количество их вхождений в указанный поток вывода.
     * <br/>
     * Формат вывода следующий:
     * <ul>
     * <li>Каждое слово вместе с количеством вхождений выводится на отдельной строке</li>
     * <li>На каждой строке слово и количество вхождений разделены одним! пробелом,
     * никаких других символов на строке быть не должно</li>
     * </ul>
     * Все выводимые слова должны быть приведены к нижнему регистру.<br/>
     * Метод не является автоматически тестируемым, но мы узнаем, если вы его не реализуете.<br/>
     * Проверить корректность метода (для себя, в методе main) вы можете, передав в него System.out.
     *
     * @param ps поток вывода
     * @throws IllegalStateException если не задан текст для анализа
     *                               (метод {@link #setText(String) setText} еще не вызывался
     *                               или последний раз вызывался с параметром <code>null</code>)
     */
    @Override
    public void printWordCounts(PrintStream ps) {

    }

    /**
     * Распечатывает слова и количество их вхождений в указанный поток вывода.<br/>
     * Слова выводятся в порядке убывания количества их вхождений, причем слова
     * с одинаковыми количествами вхождений выводятся в алфавитном порядке.
     * <br/>
     * Формат вывода следующий:
     * <ul>
     * <li>Каждое слово вместе с количеством вхождений выводится на отдельной строке</li>
     * <li>На каждой строке слово и количество вхождений разделены одним! пробелом,
     * никаких других символов на строке быть не должно</li>
     * </ul>
     * Все выводимые слова должны быть приведены к нижнему регистру.<br/>
     * Метод не является автоматически тестируемым, но мы узнаем, если вы его не реализуете.<br/>
     * Проверить корректность метода (для себя, в методе main) вы можете, передав в него System.out.
     *
     * @param ps поток вывода
     * @throws IllegalStateException если не задан текст для анализа
     *                               (метод {@link #setText(String) setText} еще не вызывался
     *                               или последний раз вызывался с параметром <code>null</code>)
     */
    @Override
    public void printWordCountsSorted(PrintStream ps) {

    }
}
