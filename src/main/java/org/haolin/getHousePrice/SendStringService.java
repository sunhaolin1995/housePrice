package org.haolin.getHousePrice;

import org.haolin.dto.HousePriceDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙浩林
 * @date: ${DATE} ${TIME}
 */
@Component
public class SendStringService {

    public static void main(String[] args) throws IOException {
        HousePriceDTO dTO = getService("广贤梁院");
        System.out.println(dTO);
    }


    public static HousePriceDTO getService(String housingEstate) throws IOException {
        // 设置爬虫访问链接
        String url = "https://dl.ke.com/ershoufang/rs"+housingEstate+"/";

        // 使用Jsoup连接链接并获取页面内容
        Document doc = Jsoup.connect(url).get();

        int pageNum = 1; // 页面编号


        HousePriceDTO housePriceDTO = new HousePriceDTO();
        List<String> res = new ArrayList<>();
        while (true){
             String baseurl = url + "pg" + pageNum + "/";
             doc = Jsoup.connect(baseurl).get();
            // 使用CSS选择器定位二手房信息所在的标签
            Elements elements = doc.select(".sellListContent .clear");

            if (elements.size() == 0) {
                break;
            }
            pageNum++;


            String totalNum = doc.select(".total").get(0).childNodes().get(1).childNodes().get(0).toString();

            String total =housingEstate +"\n" +"今天共上架"+totalNum+"套二手房 \n";
            housePriceDTO.setTotalNum(total);



            // 遍历所有二手房信息
            for (Element element : elements) {

                if (element.select(".img").size() == 0){
                    continue;
                }

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

                s1=s1+houseInfo +"</td><td>";

                //获取关注人数和发布时间
                String followInfo = element.select(".followInfo").first().text();

                s1 = s1+followInfo +"</td><td>";

                String href = "";
                //获取房子的链接

                href  = element.select(".img").first().attributes().get("href");

                s1=s1+href+"</td></tr>";

                res.add(s1);

            }

        }



        housePriceDTO.setHouseList(res);
        return housePriceDTO;

    }
}




