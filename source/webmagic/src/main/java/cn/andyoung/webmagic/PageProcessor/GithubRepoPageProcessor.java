package cn.andyoung.webmagic.PageProcessor;

import cn.andyoung.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;
import java.util.Map;

public class GithubRepoPageProcessor implements PageProcessor {
  // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
  private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

  // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
  @Override
  public void process(Page page) {

    System.out.println(page.getHtml());
    // 部分二：定义如何抽取页面信息，并保存下来
    page.putField("author", page.getUrl().regex("https://github.com/(\\w+)/.*").toString());
    System.out.println(page.getUrl().regex("https://github.com/(\\w+)/.*").toString());
    page.putField(
        "name",
        page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
    if (page.getResultItems().get("name") == null) {
      // skip this page
      page.setSkip(true);
    }
    page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

    // 部分三：从页面发现后续的url地址来抓取
    //    page.addTargetRequests(
    //        page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    for (Map.Entry<String, Object> map : page.getResultItems().getAll().entrySet()) {
      System.out.println(map);
    }
  }

  @Override
  public Site getSite() {
    return site;
  }

  public static void main(String[] args) throws JMException {

    Spider spider =
        Spider.create(new GithubRepoPageProcessor())
            .setDownloader(new HttpClientDownloader())
            // 从"https://github.com/code4craft"开始抓
            .addUrl("https://github.com/code4craft")
            // 开启5个线程抓取
            .thread(5);

    SpiderMonitor.instance().register(spider);
    // 启动爬虫
    spider.run();
  }
}
