package com.house365.collector.lianjia.sell.processor;

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
 * 链家区属板块Processor
 */
public class LianjiaDistrictProcessor extends AbstractDistrictProcessor {

    private Set<String> districtUrlSet = new HashSet<>();
    private Set<String> subDistrictUrlSet = new HashSet<>();
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public LianjiaDistrictProcessor(String cityCode, String cityRootUrl) {
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

        List<Selectable> districtTags = page.getHtml().xpath("/html/body/div[3]/div/div[1]/dl[2]/dd/div[1]/div/a")
                .nodes();
        for (Selectable districtTag : districtTags) {
            String href = districtTag.css("a", "href").toString();
            String districtUrl = cityRootUrl + href;
            if (!districtUrlSet.contains(districtUrl)) {
                districtUrlSet.add(districtUrl);
                // 添加区属URL
                page.addTargetRequest(districtUrl);
            }
        }

        // 提取区属名
        String district = "";
        List<String> districtNames = page.getHtml().xpath
                ("/html/body/div[3]/div/div[1]/dl[2]/dd/div[1]/div/a").css(".selected", "text").all();
        if (districtNames.size() > 0) {
            district = districtNames.get(0);
        }

        // 提取板块名,板块URL
        List<Selectable> subDistrictTags = page.getHtml().xpath
                ("/html/body/div[3]/div/div[1]/dl[2]/dd/div[1]/div[2]/a").nodes();
        if (subDistrictTags.size() != 0) {
            List<DistrictVO> districts = new ArrayList<>(subDistrictTags.size());
            for (Selectable subDistrictTag : subDistrictTags) {
                String href = subDistrictTag.css("a", "href").toString();
                String subDistrictUrl = cityRootUrl + href;
                String subDistrict = subDistrictTag.xpath("/a/text()").toString();

                if (!subDistrictUrlSet.contains(subDistrictUrl)) {

                    // 添加板块URL
                    DistrictVO objSubDistrict = new DistrictVO(cityCode, PlatformEnum.LIANJIA.getId(), district,
                            subDistrict, subDistrictUrl);
                    districts.add(objSubDistrict);
                    subDistrictUrlSet.add(subDistrictUrl);
                }
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
