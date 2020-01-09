package com.house365.collector.anjuke.sell.processor;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractDistrictProcessor;
import com.house365.collector.base.vo.DistrictVO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 安居客区属板块Processor
 */
public class AnjukeDistrictProcessor extends AbstractDistrictProcessor {

    private Set<String> districtUrlSet = new HashSet<>();
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public AnjukeDistrictProcessor(String cityCode, String cityRootUrl) {
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

        List<Selectable> districtTags = page.getHtml().xpath("/html/body/div[1]/div[2]/div[3]/div[1]/span[2]/a")
                .nodes();
        for (Selectable districtTag : districtTags) {
            String districtUrl = districtTag.css("a", "href").toString();
            if (!districtUrlSet.contains(districtUrl)) {
                districtUrlSet.add(districtUrl);
                // 添加区属URL
                page.addTargetRequest(districtUrl);
            }
        }

        // 提取区属名
        String district = "";
        List<String> districtNames = page.getHtml().xpath
                ("/html/body/div[1]/div[2]/div[3]/div[1]/span[2]/span").css(".selected-item", "text").all();
        if (districtNames.size() > 0) {
            district = districtNames.get(0);
        }

        // 提取板块名,板块URL
        List<Selectable> subDistrictTags = page.getHtml().xpath
                ("/html/body/div[1]/div[2]/div[3]/div[1]/span[2]/div/a").nodes();
        if (subDistrictTags.size() != 0) {
            List<DistrictVO> districts = new ArrayList<>(subDistrictTags.size());
            for (Selectable subDistrictTag : subDistrictTags) {
                String subDistrictUrl = subDistrictTag.css("a", "href").toString();
                String subDistrict = subDistrictTag.xpath("/a/text()").toString();

                DistrictVO objSubDistrict = new DistrictVO(cityCode, PlatformEnum.ANJUKE.getId(), district,
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
