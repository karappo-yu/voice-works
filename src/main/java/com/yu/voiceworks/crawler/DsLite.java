package com.yu.voiceworks.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yu.voiceworks.entity.*;
import com.yu.voiceworks.entity.po.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class DsLite {

    private static String BASE_URL = "https://www.dlsite.com/maniax/work/=/product_id/RJ";

    private static String DYNAMIC_AJAX_URL = "https://www.dlsite.com/maniax-touch/product/info/ajax?product_id=RJ";

    private static String IMAGE_ADDR = "https://img.dlsite.jp/modpub/images2/work/doujin/RJ{0}/RJ{1}_img_{2}.jpg"; //0id2 1 rjCode 2type

    private static String PROXY_ADDR = "localhost";

    private static String MAIN_IMAGE = "main";

    private static String SAM_IMAGE = "sam";

    private static String IMAG_PATH = "F:\\voice\\img";

    private static String IMAG_NAME = "RJ{0}_{1}.jpg";

    private static int TIME_OUT = 8000;

    private static int PROXY_PORT = 10809;

    private static String LANG = "ja-jp";

    public static Set<Tag> getAllTags() {
        //TODO
        return null;
    }

    /**下载给定的rj号的图片到 @IMAG_PATH 下，包括大图和小图
     * @param rjCode
     */
    public static void getImage(String rjCode){

        int id = Integer.parseInt(rjCode);

        int id2  = (id % 1000 == 0) ? id:(id / 1000) * 1000 + 1000;

        String mainUrl = MessageFormat.format(IMAGE_ADDR, id2+"", id+"", MAIN_IMAGE);
        String samUrl = MessageFormat.format(IMAGE_ADDR, id2+"", id+"", SAM_IMAGE);

        downloadImg(rjCode, mainUrl,MAIN_IMAGE);
        downloadImg(rjCode,samUrl,SAM_IMAGE);
    }

    private static void downloadImg(String rjCode, String url,String type) {
        File imagPath = new File(IMAG_PATH);
        String imgName = MessageFormat.format(IMAG_NAME, rjCode,type);
        File file = new File(imagPath, imgName);
        if (file.exists()){
            log.info("[RJ{}] 封面已存在:{}",rjCode,imgName);
            return;
        }
        try {
            log.info("[RJ{}] 开始下载:{}........",rjCode,imgName);
            URL mainImg = new URL(url);
            DataInputStream dis = new DataInputStream(mainImg.openStream());
            FileOutputStream fos  = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while((length=dis.read(buffer))>0){
                output.write(buffer,0,length);
            }

            fos.write(output.toByteArray());
            dis.close();
            fos.close();
            log.info("[RJ{}] 下载成功:{}",rjCode,imgName);
        } catch (MalformedURLException e) {
            log.error("[RJ{}] 下载失败:{}",rjCode,imgName);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("[RJ{}] 下载失败:{}",rjCode,imgName);
            e.printStackTrace();
        }
    }

    /**爬取给定rj号的动态信息并解析
     * @param id rj号
     * @return
     */
    public static VoiceWorkDynamic getDynamicData(String id){
        log.info("[RJ{}] 获取动态数据.....",id);
        String url = DYNAMIC_AJAX_URL +id;
        Document document = null;
        try {
            document = Jsoup.connect(url).proxy(PROXY_ADDR,PROXY_PORT)
                    .ignoreHttpErrors(true)
                    .timeout(TIME_OUT).get();
        } catch (IOException e) {
            log.error("[RJ{}] 获取动态数据异常:{}",id,e.getMessage());
        }
        String jsonData = document.text();
        VoiceWorkDynamic one = parseJsonData(jsonData,id);
        return one;
    }

    private static VoiceWorkDynamic parseJsonData(String jsonData,String id) {
        ObjectMapper mapper = new ObjectMapper();
        // 转换为格式化的json
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            log.info("[RJ{}] 解析动态数据.....",id);
            Map map = mapper.readValue(jsonData, Map.class);
            Map<String,Object> dataMap = (Map<String,Object>)map.get("RJ" + id);
            Integer price = (Integer)dataMap.get("price");
            Integer dlCount = Integer.parseInt((String) dataMap.get("dl_count"));
            Integer rateAverage = (Integer)dataMap.get("rate_average");
            Double rateAverage2Dp = (Double)dataMap.get("rate_average_2dp");
            Integer rateCount = (Integer)dataMap.get("rate_count");


            List<Map> rateCountDetailMaps = (List<Map>)dataMap.get("rate_count_detail");

            List<RateCountDetail> rateCountDetails = new LinkedList<>();
            for (Map map1 : rateCountDetailMaps) {
                Integer review_point = (Integer)map1.get("review_point");
                Integer count = (Integer) map1.get("count");
                Integer ratio = (Integer) map1.get("ratio");

                rateCountDetails.add(new RateCountDetail(review_point,count,ratio));
            }

            Integer reviewCount = Integer.parseInt((String) dataMap.get("review_count"));




            List<Map> rankMaps = (List<Map>)dataMap.get("rank");

            List<Rank> ranks = new LinkedList<>();

            for (Map rankMap : rankMaps) {
                String term = (String) rankMap.get("term");
                String category = (String) rankMap.get("category");
                Integer rank = (Integer) rankMap.get("rank");
                String rank_date = (String) rankMap.get("rank_date");
                ranks.add(new Rank(term,category,rank,rank_date));
            }

           return VoiceWorkDynamic.builder().workId(id)
                    .price(price)
                    .dlCount(dlCount)
                    .rateAverage(rateAverage)
                    .rateAverage2Dp(rateAverage2Dp)
                    .rateCount(rateCount)
                    .rateCountDetail(rateCountDetails)
                    .reviewCount(reviewCount)
                    .rank(ranks).build();
        } catch (JsonProcessingException e) {
            log.error("[RJ{}] 解析动态数据异常{}",id,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**爬取给定rj号的静态信息并解析
     * @param rjCode
     * @return
     */
    public static VoiceWork getStaticMetaData(String rjCode){
        String url = BASE_URL+rjCode+".html";
        Document document = null;
        try {
            log.info("[RJ{}] 获取音声静态数据.....",rjCode);
            document = Jsoup.connect(url)
                    .proxy(PROXY_ADDR,PROXY_PORT)
                    .ignoreHttpErrors(true)
                    .timeout(TIME_OUT).get();
        } catch (IOException e) {
            log.error("[RJ{}] 获取音声静态数据异常{}",rjCode,e.getMessage());
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
        Series series = null;
        Elements elements = document.getElementById("work_outline").child(0).children();
        for (Element element : elements) {
            String text = element.child(0).text();
            if (StringUtils.equals(text.trim(),JPDsLiteUtils.SERIES)){
                Element a = element.getElementsByTag("td").get(0).child(0);

                String seriesName = a.text();
                String href = a.attr("href");
                String seriesId = href.substring(href.indexOf("SRI"), href.indexOf("SRI")+13);
                series = new Series();
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
