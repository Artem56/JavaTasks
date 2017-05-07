package ru.ncedu.java.SolomatinAA.WordProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Artem Solomatin on 15.03.17.
 * NetCracker
 */
public class WordProcessorImpl implements WordProcessor {
    private String text;
    /**
     * @return текущий текст для работы или <code>null</code>,
     * если ни один из set-методов не был выполнен успешно.
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * Принимает текст для работы
     *
     * @param src текст для работы
     * @throws IllegalArgumentException если <code>src == null</code>
     */
    @Override
    public void setSource(String src) {
        if(src == null){
            throw new IllegalArgumentException();
        }
        text = src;
    }

    /**
     * Считывает текст для работы из указанного файла
     *
     * @param srcFile путь до файла с текстом
     * @throws IOException              в случае ошибок при чтении из файла
     * @throws IllegalArgumentException если <code>srcFile == null</code>
     */
    @Override
    public void setSourceFile(String srcFile) throws IOException {
        if(srcFile == null){
            throw new IllegalArgumentException();
        }
        if(srcFile == null)
            throw new IllegalArgumentException();
        byte[] encoded = Files.readAllBytes(Paths.get(srcFile));
        text = new String(encoded);
    }

    /**
     * Считывает текст для работы из указанного потока ввода
     *
     * @param fis поток ввода
     * @throws IOException              в случае ошибок при чтении из потока
     * @throws IllegalArgumentException если <code>fis == null</code>
     */
    @Override
    public void setSource(FileInputStream fis) throws IOException {
        if(fis == null)
            throw new IllegalArgumentException();
        StringBuilder builder = new StringBuilder();
        int ch;
        while((ch = fis.read()) != -1){
            builder.append((char)ch);
        }
        text = builder.toString();
    }

    /**
     * Ищет и возвращает все слова, начинающиеся с указанной последовательности
     * символов без учета регистра. <br/>
     * Если <code>begin</code> - пустая строка или <code>null</code>,
     * то результат содержит все слова, найденные в файле.<br/>
     * Все возвращенные слова должны быть приведены к нижнему регистру.
     *
     * @param begin первые символы искомых слов
     * @return слова, начинающиеся с указанной последовательности символов
     * @throws IllegalStateException если нет текста для работы
     *                               (ни один из set-методов не был успешно выполнен)
     */
    @Override
    public Set<String> wordsStartWith(String begin) {
        if(begin == null)
            begin = "";

        text = text.toLowerCase();
        begin = begin.toLowerCase();
        Set<String> ret = new HashSet<String>();

        Pattern pattern;
        pattern = Pattern.compile("[ \\f\\n\\r\\t\\v]"+begin+"[^ \\f\\n\\r\\t\\v]*");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find())
        {
            ret.add(matcher.group(0).trim());
        }
        ret.remove("");
        pattern = Pattern.compile("^"+begin+"[^ \\f\\n\\r\\t\\v]*");
        matcher = pattern.matcher(text);
        while (matcher.find())
        {
            ret.add(matcher.group(0).trim());
        }

        return ret;
    }
}
