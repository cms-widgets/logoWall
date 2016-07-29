/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.logoWall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.huotu.hotcms.service.entity.Link;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import me.jiangcai.lib.resource.service.ResourceService;
import org.apache.http.entity.ContentType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget{
    public static final String VALID_LOGO_LINK_LIST = "logoLinkList";
    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String VALID_STYLE_TEMPLATE = "styleTemplate";

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
        if (locale.equals(Locale.CHINESE)) {
            return "A custom Widget";
        }
        return "logoWall";
    }

    @Override
    public String description() {
        return "这是一个logo墙控件，你可以对组件进行自定义修改。";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return description();
        }
        return "This is a logoWall,  you can make custom change the component.";
    }

    @Override
    public int dependBuild() {
        return 0;
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }


    @Override
    public Resource widgetJs() {
        return new ClassPathResource("js/logoWall.js", getClass().getClassLoader());
    }


    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png",new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                ,getClass().getClassLoader()));
        return map;
    }

    @Override
    public Resource widgetDependencyContent(ContentType contentType) {
        return null;
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle[] widgetStyles = styles();
        boolean flag = false;
        if (widgetStyles == null || widgetStyles.length < 1) {
            throw new IllegalArgumentException();
        }
        for (WidgetStyle ws : widgetStyles) {
            if ((flag = ws.id().equals(styleId))) {
                break;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException();
        }
        //加入控件独有的属性验证

        List<List> logoLinkList = (List<List>) componentProperties.get(VALID_LOGO_LINK_LIST);
        if (logoLinkList == null && logoLinkList.size()>0) {
            throw new IllegalArgumentException("控件属性缺少");
        }
    }

    @Override
    public Class springConfigClass() {
        return null;
    }

    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        List<Link> list = new ArrayList<>();
        Link link1 = new Link();
        link1.setId(1L);
        link1.setTitle("logo1");
        link1.setThumbUri("http://placehold.it/106x82?text=logo1");

        Link link2 = new Link();
        link2.setId(2L);
        link2.setTitle("logo1");
        link2.setThumbUri("http://placehold.it/106x82?text=logo2");

        Link link3 = new Link();
        link3.setId(1L);
        link3.setTitle("logo3");
        link3.setThumbUri("http://placehold.it/106x82?text=logo3");

        Link link4 = new Link();
        link4.setId(4L);
        link4.setTitle("logo4");
        link4.setThumbUri("http://placehold.it/106x82?text=logo4");

        list.add(link1);
        list.add(link2);
        list.add(link3);
        list.add(link4);
        ComponentProperties properties = new ComponentProperties();
        properties.put("logoLinkList",list);
        return properties;
    }



}
