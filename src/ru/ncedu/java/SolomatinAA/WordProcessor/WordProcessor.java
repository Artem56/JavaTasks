package ru.ncedu.java.SolomatinAA.WordProcessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Artem Solomatin on 15.03.17.
 * NetCracker
 */
public interface WordProcessor
{
    /**
     * @return текущий текст для работы или <code>null</code>,
     * если ни один из set-методов не был выполнен успешно.
     */
    String getText ();
    /**
     * Принимает текст для работы
     * @param src текст для работы
     * @throws IllegalArgumentException если <code>src == null</code>
     */
    void setSource (String src);
    /**
     * Считывает текст для работы из указанного файла
     * @param srcFile путь до файла с текстом
     * @throws IOException в случае ошибок при чтении из файла
     * @throws IllegalArgumentException если <code>srcFile == null</code>
     */
    void setSourceFile (String srcFile) throws IOException;
    /**
     * Считывает текст для работы из указанного потока ввода
     * @param fis поток ввода
     * @throws IOException в случае ошибок при чтении из потока
     * @throws IllegalArgumentException если <code>fis == null</code>
     */
    void setSource (FileInputStream fis) throws IOException;
    /**
     * Ищет и возвращает все слова, начинающиеся с указанной последовательности
     * символов без учета регистра. <br/>
     * Если <code>begin</code> - пустая строка или <code>null</code>,
     * то результат содержит все слова, найденные в файле.<br/>
     * Все возвращенные слова должны быть приведены к нижнему регистру.
     * @param begin первые символы искомых слов
     * @return слова, начинающиеся с указанной последовательности символов
     * @throws IllegalStateException если нет текста для работы
     *  (ни один из set-методов не был успешно выполнен)
     */
    Set<String> wordsStartWith (String begin);
}
