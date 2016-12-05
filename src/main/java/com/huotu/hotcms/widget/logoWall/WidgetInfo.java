/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.logoWall;

import com.huotu.hotcms.service.common.ContentType;
import com.huotu.hotcms.service.common.LinkType;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Link;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.LinkRepository;
import com.huotu.hotcms.service.service.CategoryService;
import com.huotu.hotcms.service.service.ContentService;
import com.huotu.hotcms.widget.*;
import com.huotu.hotcms.widget.entity.PageInfo;
import com.huotu.hotcms.widget.repository.PageInfoRepository;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget, PreProcessWidget {
    public static final String LINK_SERIAL = "linkSerial";
    private static final String VALID_DATA_LIST = "dataList";

    @Override
    public String groupId() {
        return "com.huotu.hotcms.widget.logoWall";
    }

    @Override
    public String widgetId() {
        return "logoWall";
    }


    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "LOGO墙";
        }
        return "logoWall";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "这是一个logo墙控件，你可以对组件进行自定义修改。";
        }
        return "This is a logoWall,  you can make custom change the component.";
    }

    @Override
    public String dependVersion() {
        return "1.1.0";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png", new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                , getClass().getClassLoader()));
        map.put("js/logoWall.js", new ClassPathResource("js/logoWall.js", getClass().getClassLoader()));
        return map;
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType) {
        if (mediaType.isCompatibleWith(Javascript)) {
            return new ClassPathResource("js/logoWall.js", getClass().getClassLoader());
        }
        return null;
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle style = WidgetStyle.styleByID(this, styleId);
        //加入控件独有的属性验证
        String linkSerial = (String) componentProperties.get(LINK_SERIAL);
        if (linkSerial == null || linkSerial.equals("")) {
            throw new IllegalArgumentException("控件属性缺少数据源");
        }
    }

    @Override
    public Class springConfigClass() {
        return null;
    }

    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        CategoryRepository categoryRepository = getCMSServiceFromCMSContext(CategoryRepository.class);
        //todo 获取列表要过滤掉已删除的数据源
        List<Category> list =
                categoryRepository.findBySiteAndContentTypeAndDeletedFalse(CMSContext.RequestContext().getSite(), ContentType.Link);
        if (list.isEmpty()) {
            Category category = initCategory();
            initLink(category, resourceService);
            properties.put(LINK_SERIAL, category.getSerial());
        } else {
            properties.put(LINK_SERIAL, list.get(0).getSerial());
        }
        return properties;
    }

    @Override
    public void prepareContext(WidgetStyle style, ComponentProperties properties, Map<String, Object> variables
            , Map<String, String> parameters) {
        String serial = (String) properties.get(LINK_SERIAL);
        LinkRepository linkRepository = getCMSServiceFromCMSContext(LinkRepository.class);
        PageInfoRepository pageInfoRepository = getCMSServiceFromCMSContext(PageInfoRepository.class);
        List<Link> links = linkRepository.findByCategory_SiteAndCategory_Serial(CMSContext.RequestContext().getSite(), serial);
        for (Link link : links) {
            if (link.getLinkType().isPage()) {
                PageInfo pageInfo = pageInfoRepository.findOne(link.getPageInfoID());
                link.setPagePath(pageInfo.getPagePath());
            }
        }
        variables.put(VALID_DATA_LIST, links);
    }


    /**
     * 初始化数据源
     *
     * @return
     */
    private Category initCategory() {
        CategoryService categoryService = getCMSServiceFromCMSContext(CategoryService.class);
        CategoryRepository categoryRepository = getCMSServiceFromCMSContext(CategoryRepository.class);
        Category category = new Category();
        category.setContentType(ContentType.Link);
        category.setName("链接数据源");
        categoryService.init(category);
        category.setSite(CMSContext.RequestContext().getSite());
        //保存到数据库
        categoryRepository.save(category);
        return category;
    }

    /**
     * /**
     * 初始化一个图片
     *
     *
     *
     * @param category
     * @param resourceService
     * @return
     */
    private Link initLink(Category category, ResourceService resourceService) throws IOException {
        ContentService contentService = getCMSServiceFromCMSContext(ContentService.class);
        LinkRepository linkRepository = getCMSServiceFromCMSContext(LinkRepository.class);
        Link link = new Link();
        link.setTitle("link");
        link.setCategory(category);
        link.setDeleted(false);
        link.setLinkType(LinkType.Link);
        link.setCreateTime(LocalDateTime.now());
        link.setLinkUrl("http://www.huobanplus.com/");
        ClassPathResource classPathResource = new ClassPathResource("img/logo.png", getClass().getClassLoader());
        InputStream inputStream = classPathResource.getInputStream();
        String imgPath = "_resources/" + UUID.randomUUID().toString() + ".png";
        resourceService.uploadResource(imgPath, inputStream);
        link.setThumbUri(imgPath);
        contentService.init(link);
        return linkRepository.saveAndFlush(link);
    }


}
