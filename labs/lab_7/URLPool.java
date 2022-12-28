package labs.lab_7;

import java.util.*;

/*
 * Класс описывающий пулл ссылок
 */
public class URLPool {
    // Хранит количество потоков в ожидании
    private int waitCount = 0;

    private int maxDepth;

    // Ссылки ожидающие обработку
    private LinkedList<URLDepthPair> toProcessURLs;

    // Обработанные ссылки
    private LinkedList<URLDepthPair> processedURLs;

    // Хешсет для хранения неповторяющихся ссылок
    private HashSet<String> unrepeatedURLs;

    public URLPool(int _maxDepth) {
        toProcessURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
        unrepeatedURLs = new HashSet<String>();

        maxDepth = _maxDepth;
    }

    public int getWaitCount() {
        synchronized (this) {
            return waitCount;
        }
    }

    /*
     * Вызывает wait пока в pending urls не появится ссылка (фризит поток)
     */
    public URLDepthPair get() {
        synchronized (this) {
            while (toProcessURLs.size() == 0) {
                waitCount++;
                try {
                    wait();
                } catch (InterruptedException e) {
                }
                waitCount--;
            }

            return toProcessURLs.removeFirst();
        }
    }

    /*
     * Обрезает ссылку, добавляет ее в список обработанных и увиденных ссылок и
     * уведомляет поток с обработчиком
     */
    public void add(URLDepthPair nextPair) {
        synchronized (this) {
            String url = nextPair.getURL().toString();
            String cut = (url.endsWith("/")) ? url.substring(0, url.length() - 1) : url;
            if (unrepeatedURLs.contains(cut)) {
                return;
            }
            unrepeatedURLs.add(cut);

            if (nextPair.getDepth() < maxDepth) {
                toProcessURLs.add(nextPair);
                notify();
            }
            processedURLs.add(nextPair);
        }
    }

    /*
     * Выводит все ссылки
     */
    public void printURLs() {
        synchronized (this) {
            while (!processedURLs.isEmpty()) {
                System.out.println(processedURLs.removeFirst());
            }
        }
    }
}