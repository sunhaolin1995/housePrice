package org.haolin;

import org.haolin.dto.HousePriceDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙浩林
 * @date: ${DATE} ${TIME}
 */
public class sendStringService {

    public static void main(String[] args) throws IOException {
        getService("广贤梁院");
        System.out.println();
    }

    public static HousePriceDTO getService(String housingEstate) throws IOException {
        // 设置爬虫访问链接
        String url = "https://dl.ke.com/ershoufang/rs"+housingEstate+"/";

        // 使用Jsoup连接链接并获取页面内容
        Document doc = Jsoup.connect(url).get();

        // 使用CSS选择器定位二手房信息所在的标签
        Elements elements = doc.select(".sellListContent .clear");

        String totalNum = doc.select(".total").get(0).childNodes().get(1).childNodes().get(0).toString();


        HousePriceDTO housePriceDTO = new HousePriceDTO();

        String total =housingEstate +"\n" +"今天共上架"+totalNum+"套二手房 \n";
        housePriceDTO.setTotalNum(total);

        List<String> res = new ArrayList<>();

        // 遍历所有二手房信息
        for (Element element : elements) {

            String s1 = "<tr><td>";
            // 使用CSS选择器定位房屋价格所在的标签
            Element priceElement = element.select(".totalPrice").first();

            // 获取房屋总价格
            String price = priceElement.text();

            // 输出房屋价格
            s1=s1+price+"</td><td>";

            //获取房屋单价
            Element unitPrice = element.select(".unitPrice").first();

            // 获取单价
            String unitPriceStr = unitPrice.text();

            // 输出单价
            s1=s1+unitPriceStr+"</td><td>";


            //输出房子信息
            String houseInfo = element.select(".houseInfo").first().text();

            s1=s1+houseInfo +"</td></tr>";


            res.add(s1);

        }

        housePriceDTO.setHouseList(res);
        return housePriceDTO;

    }
}




