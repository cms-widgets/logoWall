/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.logoWall;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            return "LOGO墙";
        }
        return "logoWall";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "这是一个logo墙控件，你可以对组件进行自定义修改。";
        }
        return "This is a logoWall,  you can make custom change the component.";
    }

    @Override
    public String dependVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png",new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                ,getClass().getClassLoader()));
        map.put("js/logoWall.js",new ClassPathResource("js/logoWall.js",getClass().getClassLoader()));
        return map;
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType) {
        if (mediaType.isCompatibleWith(Javascript)){
            return new ClassPathResource("js/logoWall.js",getClass().getClassLoader());
        }
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

        List logoLinkList = (List) componentProperties.get(VALID_LOGO_LINK_LIST);
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
        List<String> list = new ArrayList<>();
        list.add("http://placehold.it/106x82?text=logo4");
        list.add("http://placehold.it/106x82?text=logo3");
        list.add("http://placehold.it/106x82?text=logo2");
        list.add("http://placehold.it/106x82?text=logo1");
        ComponentProperties properties = new ComponentProperties();
        properties.put(VALID_LOGO_LINK_LIST,list);
        return properties;
    }



}
