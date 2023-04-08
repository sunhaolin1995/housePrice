package org.haolin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * @author 孙浩林
 * @date: ${DATE} ${TIME}
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 设置爬虫访问链接
        String url = "https://dl.ke.com/ershoufang/rs和泰万家/";

        // 使用Jsoup连接链接并获取页面内容
        Document doc = Jsoup.connect(url).get();

        // 使用CSS选择器定位二手房信息所在的标签
        Elements elements = doc.select(".sellListContent .clear");

        String totalNum = doc.select(".total").get(0).childNodes().get(1).childNodes().get(0).toString();


        System.out.println("今天共上架"+totalNum+"套二手房");
        // 遍历所有二手房信息
        for (Element element : elements) {
            // 使用CSS选择器定位房屋价格所在的标签
            Element priceElement = element.select(".totalPrice").first();

            // 获取房屋总价格
            String price = priceElement.text();

            // 输出房屋价格
            System.out.print(price+"  ");

            //获取房屋单价
            Element unitPrice = element.select(".unitPrice").first();

            // 获取单价
            String unitPriceStr = unitPrice.text();

            // 输出单价
            System.out.print(unitPriceStr);

            //输出房子信息
            System.out.println(element.select(".houseInfo").first().text());

        }
    }
}




