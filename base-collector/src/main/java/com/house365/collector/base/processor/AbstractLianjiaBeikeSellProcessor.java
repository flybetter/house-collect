package com.house365.collector.base.processor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractLianjiaBeikeSellProcessor extends AbstractSellProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLianjiaBeikeSellProcessor.class);

    private static int PAGE_SIZE = 30;
    private static int PAGE_NUM_LIMIT = 100;
    private static int PRICE_LIMIT = 8;
    private Set<String> listPageUrlSet = new HashSet<>();

    /**
     * 获取当前价格区间
     *
     * @param url
     * @param listPageRootUrl
     * @return
     */
    protected int getCurrPrice(String url, String listPageRootUrl) {

        String pageAndPrice = StringUtils.remove(StringUtils.substring(url, listPageRootUrl.length()), "/");
        Pattern pattern = Pattern.compile("(pg\\d{1,3})(p\\d{1})");
        Matcher matcher = pattern.matcher(pageAndPrice);
        String price = "";

        while (matcher.find()) {
            price = matcher.group(2);
        }

        if (StringUtils.isEmpty(price)) {
            return 1;
        } else {
            return Integer.valueOf(StringUtils.remove(price, "p"));
        }
    }

    /**
     * 获取当前页码
     *
     * @param url
     * @param listPageRootUrl
     * @param pagePattern
     * @return
     */
    protected int getCurrPageNum(String url, String listPageRootUrl, String pagePattern) {

        String pageAndPrice = StringUtils.remove(StringUtils.substring(url, listPageRootUrl.length()), "/");
        Pattern pattern = Pattern.compile(pagePattern);
        Matcher matcher = pattern.matcher(pageAndPrice);
        String page = "";

        while (matcher.find()) {
            page = matcher.group(1);
        }

        if (StringUtils.isEmpty(page)) {
            return 1;
        } else {
            return Integer.valueOf(StringUtils.remove(page, "pg"));
        }
    }

    /**
     * 处理页面
     *
     * @param page
     * @param houseTotalXpath
     * @param houseCardXpath
     * @param isFullCollect
     */
    protected void processPage(Page page, String houseTotalXpath, String houseCardXpath, boolean isFullCollect) {

        // 处理列表页
        if (page.getUrl().regex(listPageUrlPattern).match()) {

            if (isFullCollect) {
                // 全量采集

                listPageUrlSet.add(page.getUrl().toString());

                int total = Integer.valueOf(StringUtils.trim(page.getHtml().xpath
                        (houseTotalXpath).toString()));

                // 从当前page url获取价格范围index 页码
                int currPageNum = getCurrPageNum(page.getUrl().toString(), listPageRootUrl, "(pg\\d{1,3})(p\\d{1})");
                int currPrice = getCurrPrice(page.getUrl().toString(), listPageRootUrl);

                if (total > 0) {

                    int maxPageNum = total % PAGE_SIZE == 0 ? (total / PAGE_SIZE) : (total / PAGE_SIZE + 1);
                    // 贝壳列表页最多只能翻到100页
                    maxPageNum = maxPageNum >= PAGE_NUM_LIMIT ? PAGE_NUM_LIMIT : maxPageNum;

                    // 加入详情页url
                    page.addTargetRequests(page.getHtml().xpath(houseCardXpath).links()
                            .regex(detailPageUrlPattern).all());

                    if (currPageNum < PAGE_NUM_LIMIT) {
                        // 当前价格列表页下一页
                        String listPageUrl = listPageRootUrl + "pg" + (currPageNum + 1) + "p" + currPrice + "/";
                        if (!listPageUrlSet.contains(listPageUrl)) {
                            // 加入列表页url
                            page.addTargetRequest(listPageUrl);
                            listPageUrlSet.add(listPageUrl);
                        }
                    }
                } else {

                    // 当前价格列表页无房源
                    // 当前价格所有列表页已遍历完，从下一价格的第一页开始
                    if (currPrice + 1 <= PRICE_LIMIT) {

                        String listPageUrl = listPageRootUrl + "pg1" + "p" + (currPrice + 1) + "/";
                        if (!listPageUrlSet.contains(listPageUrl)) {
                            // 加入列表页url
                            page.addTargetRequest(listPageUrl);
                            listPageUrlSet.add(listPageUrl);
                        }
                    }
                }
            } else {
                // 增量采集
                List<Selectable> liTags = page.getHtml().xpath(houseCardXpath + "/li").css("li.clear").nodes();

                // 当前列表页是否存在最新发布房源
                boolean hasLatestSell = false;

                for (Selectable liTag : liTags) {

                    // 贝壳
                    String followInfo = liTag.xpath("/li/div/div[2]/div[3]/text()").toString();
                    if (StringUtils.isBlank(followInfo)) {

                        // 链家
                        followInfo = liTag.xpath("/li/div[1]/div[4]/text()").toString();
                    }
                    if (StringUtils.contains(followInfo, "今天发布") || StringUtils.contains(followInfo, "刚刚发布")) {

                        // 当前liTag为新发布房源
                        hasLatestSell = true;
                        page.addTargetRequests(liTag.links().regex(detailPageUrlPattern).all());
                    }
                }

                // 当前列表页存在最新发布房源的话,就再往后翻一页
                if (hasLatestSell) {

                    int currPageNum = getCurrPageNum(page.getUrl().toString(), listPageRootUrl, "(pg\\d{1,3})(co32)");
                    String listPageUrl = listPageRootUrl + "pg" + (currPageNum + 1) + "co32/";
                    if (!listPageUrlSet.contains(listPageUrl)) {
                        // 加入列表页url
                        page.addTargetRequest(listPageUrl);
                        listPageUrlSet.add(listPageUrl);
                    }
                }
            }

            // 列表页不传递给pipeline处理
            page.setSkip(true);
        } else if (page.getUrl().regex(detailPageUrlPattern).match()) {

            // 处理详情页
            processDetailPage(page);
        }

    }

    /**
     * 处理详情页
     *
     * @param page
     */
    public abstract void processDetailPage(Page page);
}
