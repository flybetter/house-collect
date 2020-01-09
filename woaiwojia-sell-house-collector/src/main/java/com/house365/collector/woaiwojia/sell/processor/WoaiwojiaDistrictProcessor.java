package com.house365.collector.woaiwojia.sell.processor;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractDistrictProcessor;
import com.house365.collector.base.vo.DistrictVO;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 我爱我家区属板块Processor
 */
public class WoaiwojiaDistrictProcessor extends AbstractDistrictProcessor {

    private Set<String> districtUrlSet = new HashSet<>();
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public WoaiwojiaDistrictProcessor(String cityCode, String cityRootUrl) {
        this.cityCode = cityCode;
        this.cityRootUrl = cityRootUrl;
    }

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {

        // 设置数据类型
        setDataType(page);

        List<Selectable> districtTags = page.getHtml().xpath("/html/body/div[3]/div[2]/div/ul/li[1]/div[3]/div[1]/ul/a")
                .nodes();
        for (Selectable districtTag : districtTags) {

            String desc = districtTag.xpath("/a/li/text()").toString();
            if (!StringUtils.equals("全部", desc)) {

                String href = districtTag.css("a", "href").toString().replace("?","");
                String districtUrl = cityRootUrl + href;
                if (!districtUrlSet.contains(districtUrl)) {
                    districtUrlSet.add(districtUrl);
                    // 添加区属URL
                    page.addTargetRequest(districtUrl);
                }
            }
        }

        // 提取区属名
        String district = "";
        for (Selectable districtTag : districtTags) {

            String desc = districtTag.xpath("/a/li/text()").toString();
            if (!StringUtils.equals("全部", desc)) {

                String liClass = districtTag.xpath("/a/li").css("li","class").toString();
                if (StringUtils.equals(liClass, "new_di_tab_cur")) {

                    district = districtTag.xpath("/a/li/text()").toString();
                }
            }
        }

        // 提取板块名,板块URL
        List<Selectable> subDistrictTags = page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div/ul/li[1]/div[3]/div[1]/dl/dd/a").nodes();
        if (subDistrictTags.size() != 0) {

            List<DistrictVO> districts = new ArrayList<>(subDistrictTags.size());
            for (Selectable subDistrictTag : subDistrictTags) {

                String href = subDistrictTag.css("a", "href").toString().replace("?","");
                String subDistrictUrl = cityRootUrl + href;
                String subDistrict = subDistrictTag.xpath("/a/text()").toString();

                DistrictVO objSubDistrict = new DistrictVO(cityCode, PlatformEnum.WOAIWOJIA.getId(), district,
                        subDistrict, subDistrictUrl);
                districts.add(objSubDistrict);
            }
            // 板块
            page.putField(FieldNameConstant.DISTRICTS, districts);
        }
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return site;
    }
}
