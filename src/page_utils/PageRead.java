package page_utils;

import java.io.*;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PageRead {

    public static StringBuilder readPage(String pageAddr) {
        try {
            URL url = new URL(pageAddr);
            //URLConnection url_conn = url.openConnection();
            
            //int size = url_conn.getContentLength();
            
            StringBuilder sb;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                
                String line;
                sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
            }

            return sb;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
