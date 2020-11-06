package com.yu.voiceworks.crawler;

import com.yu.voiceworks.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DsLite {

    private static String BASE_URL = "https://www.dlsite.com/maniax/work/=/product_id/RJ";

    private static String PROXY_ADDR = "localhost";

    private static int PROXY_PORT = 10809;

    private static String LANG = "ja-jp";

    public static VoiceWork getStaticMetaData(String rjCode){
        String url = BASE_URL+rjCode+".html";
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .proxy(PROXY_ADDR,PROXY_PORT)
                    .ignoreHttpErrors(true)
                    .timeout(8000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null!=document){
            LANG = document.getElementsByTag("html").get(0).attr("lang");
            JPDsLiteUtils.setLang(LANG);
            return getVoiceWork(document,rjCode);
        }
        return null;
    }

    private static VoiceWork getVoiceWork(Document document,String id) {
       return VoiceWork.builder()
                .title(getTitle(document)).id(id)
                .circle(getCircle(document))
                .ageRatings(getAgeRatings(document))
                .release( getRelease(document))
                .series(getSeries(document))
                .scenario(getScenario(document))
                .tags(getTags(document))
                .cvs(getCvs(document))
                .imageAuthor(getImageAuthor(document))
                .workFormat(getWorkFormat(document))
        .build();
    }

    private static String getTitle(Document document) {
        Element element = document.getElementsByClass("base_title_br clearfix").get(0).getElementsByTag("h1").get(0);
        return element.text();
    }

    private static String getWorkFormat(Document document) {
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.WORK_FORMAT)){
                Element td = element.getElementsByTag("td").get(0);
                return td.text();
            }
        }
        return null;
    }

    private static String getImageAuthor(Document document) {
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.IMAGE_AUTHOR)){
                Element td = element.getElementsByTag("td").get(0);
                return td.text();
            }
        }
        return null;
    }

    private static Set<Cv> getCvs(Document document) {
        Elements elements = document.getElementById("work_outline").child(0).children();
        Set<Cv> cvs= new HashSet<Cv>();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.VA)){
                Element td = element.getElementsByTag("td").get(0);
                Elements as = td.getElementsByTag("a");
                for (Element a : as) {
                    String cvName = a.text();
                    cvs.add(new Cv(cvName,cvName));
                }
                return cvs;
            }

        }
        return cvs;
    }

    private static Set<Tag> getTags(Document document) {
        Elements elements = document.getElementById("work_outline").child(0).children();
        Set<Tag> tags= new HashSet<Tag>();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.GENRE)){
                Element td = element.getElementsByTag("td").get(0);
                Elements as = td.getElementsByTag("a");
                for (Element a : as) {
                    String tagName = a.text();
                    String href = a.attr("href");
                    String tagId = href.substring(href.indexOf("genre") + 6, href.indexOf("genre") + 9);
                    tags.add(new Tag(tagId,tagName));
                }
                return tags;
            }

        }
        return tags;
    }

    private static String getScenario(Document document) {
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.SCENARIO)){
                Element td = element.getElementsByTag("td").get(0);
                return td.text();
            }
        }
        return null;
    }

    private static Series getSeries(Document document) {
        Series series = new Series();
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.SERIES)){
                Element a = element.getElementsByTag("td").get(0).child(0);

                String seriesName = a.text();
                String href = a.attr("href");
                String seriesId = href.substring(href.indexOf("SRI"), href.indexOf("SRI")+13);

                series.setSeriesId(seriesId);
                series.setSeriesName(seriesName);
            }
        }
        return series;
    }

    private static String getRelease(Document document) {
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.RELEASE)){
                Element td = element.getElementsByTag("td").get(0);
                return td.text();
            }
        }
        return null;

    }

    private static Circle getCircle(Document document) {
        Circle circle = new Circle();

        Element maker_name = document.getElementsByClass("maker_name").get(0);
        Element a = maker_name.getElementsByTag("a").get(0);


        String circleUrl = a.attr("href");
        String circleName = a.text();
        String circleId = circleUrl.subSequence(circleUrl.indexOf("RG"), circleUrl.indexOf(".html")).toString();
        circle.setCircleId(circleId);
        circle.setCircleName(circleName);
        return circle;
    }

    private static String getAgeRatings(Document document){
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.AGE_RATINGS)){
                Element td = element.getElementsByTag("td").get(0);
                return td.text();
            }
        }
        return null;
    }
}
