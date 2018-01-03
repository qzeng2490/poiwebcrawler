import org.apache.log4j.BasicConfigurator;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SinaBlogProcessor implements PageProcessor, Pipeline {

    public static final String URL_LIST = "http://www\\.poi86\\.com/poi/district/1151/\\d+\\.html";

    public static final String URL_POST = "http://www\\.poi86\\.com/poi/\\d+\\.html";

    private Site site = Site
            .me()
            .setDomain("poi86.com")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().$(".panel-body .table").links().regex(URL_POST).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else {
            page.putField("name", page.getHtml().xpath("//div[@class='panel-heading']/h2/text()").toString());
            page.putField("address", page.getHtml().$(".list-group li:nth-child(4)").xpath("//li/text()").toString());
            page.putField("lnglat",
                    page.getHtml().$(".list-group li:nth-child(10)").xpath("//li/text()").toString());
        }
    }
    public void process(ResultItems resultItems, Task task){

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
//        String[] urls = new String[1923];
//        for(int i=1;i<=1923;i++) {
//            String url = "http://www.poi86.com/poi/district/1151/"+i+".html";
//            urls[i-1] = url;
//        }
        String[] urls = new String[4];
        urls[0] = "http://www.poi86.com/poi/district/1151/1.html";
        urls[1] = "http://www.poi86.com/poi/district/1151/500.html";
        urls[2] = "http://www.poi86.com/poi/district/1151/1000.html";
        urls[3] = "http://www.poi86.com/poi/district/1151/1400.html";

        Spider.create(new SinaBlogProcessor()).addUrl(urls)
                .addPipeline(new JsonFilePipeline("/Users/zengqiang/Downloads/shunde2/"))
                .thread(20)
                .run();
    }
}