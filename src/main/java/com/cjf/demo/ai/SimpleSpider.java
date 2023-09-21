package com.cjf.demo.ai;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SimpleSpider {

    public static void main(String[] args) {
        String url = "https://www.baidu.com";
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc.title());
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                System.out.println(link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

