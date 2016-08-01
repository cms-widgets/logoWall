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
import com.huotu.widget.test.WidgetTest;
import com.huotu.widget.test.bean.WidgetViewController;
import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
public class TestWidgetInfo extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return false;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        try{
            currentWidgetProperties.get();
        }catch (IllegalStateException ignored){
            assertThat(0).as("save没有属性值返回异常").isEqualTo(0);
        }

        List<WebElement> options =  editor.findElements(By.tagName("option"));
        assertThat(options.size()).isNotEqualTo(0);
        try{
            currentWidgetProperties.get();
        }catch (IllegalStateException ignored){
            assertThat(0).as("save没有属性值返回异常").isEqualTo(0);
        }

    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) {

        ComponentProperties componentProperties = new ComponentProperties();
        ComponentProperties properties = new ComponentProperties();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> link1 = new HashedMap();
        link1.put("linkUrl","logo1");
        link1.put("thumbUri","http://placehold.it/106x82?text=logo1");

        Map<String,Object> link2 = new HashedMap();
        link2.put("linkUrl","logo1");
        link2.put("thumbUri","http://placehold.it/106x82?text=logo1");

        list.add(link1);
        list.add(link2);
        properties.put(WidgetInfo.VALID_LOGO_LINK_LIST,list);
        componentProperties.put("properties", properties);

        WebElement webElement = uiChanger.apply(componentProperties);

        List<WebElement> thumbnails = webElement.findElements(By.className("thumbnail"));
        assertThat(thumbnails.size()).isEqualTo(2);

    }

}
