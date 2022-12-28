package labs.lab_7;

import java.io.*;
import java.net.*;
import java.util.regex.*;

/*
 * Класс потока сканера
 */
public class CrawlerTask implements Runnable {

    // Регулярные выражения дочерних ссылок
    public static final String LINK_REGEX = "href\\s*=\\s*\"([^$^\"]*)\"";
    public static final Pattern LINK_PATTERN = Pattern.compile(LINK_REGEX, Pattern.CASE_INSENSITIVE);

    public static int waitTime;

    private URLPool pool;

    public CrawlerTask(URLPool p) {
        pool = p;
    }

    /*
     * Посылает get request для пары, возвращает сокет
     */
    public Socket sendRequest(URLDepthPair pair)
            throws UnknownHostException, SocketException, IOException {

        Socket socket = new Socket(pair.getHost(), 80);
        socket.setSoTimeout(waitTime * 1000);

        OutputStream os = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(os, true);

        writer.println("GET " + pair.getDocPath() + " HTTP/1.1");
        writer.println("Host: " + pair.getHost());
        writer.println("Connection: close");
        writer.println();

        return socket;
    }

    /*
     * Обрабатывает ссылку и добавляет результат в пулл
     */
    public void processURL(URLDepthPair pair) throws IOException {
        Socket socket;
        try {
            socket = sendRequest(pair);
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
            return;
        } catch (SocketException e) {
            System.err.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String str;
        while ((str = reader.readLine()) != null) {
            Matcher LinkFinder = LINK_PATTERN.matcher(str);
            while (LinkFinder.find()) {
                String newURL = LinkFinder.group(1);
                URL newSite;
                try {
                    if (URLDepthPair.isAbsolute(newURL)) {
                        newSite = new URL(newURL);
                    } else {
                        newSite = new URL(pair.getURL(), newURL);
                    }
                    pool.add(new URLDepthPair(newSite, pair.getDepth() + 1));
                } catch (MalformedURLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        reader.close();

        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /*
     * Запуск
     */
    public void run() {
        URLDepthPair nextPair;
        while (true) {
            nextPair = pool.get();
            try {
                processURL(nextPair);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}