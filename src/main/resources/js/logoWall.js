
/**
 * Created by lhx on 2016/6/27.
 */
CMSWidgets.initWidget({
    // 编辑器相关
    editor: {
        properties: null,
        findByLink: function (categoryId) {
            getDataSource('findLink', categoryId, function onSuccess(json) {
                if (json != '') {
                    $.each(data, function (i, obj) {
                        $(".thumbnailList").append('<img src="' + obj.thumbUri + '" />');
                    });
                    this.properties.logoLinkList = data;
                }
            }, function onError(json) {
                layer.msg('服务器错误,请稍后再试', {time: 2000});
            });
        },
        changeLinkCategory: function () {
            var me = this;
            $(".linkdiv").on("change", ".LinkCategory", function () {
                var categoryId = $(this).val();
                if (categoryId != '') {
                    me.findByLink(categoryId);
                }
            });

        },
        saveComponent: function (onSuccess, onFailed) {
            if (this.properties.logoLinkList != null) {
                onSuccess(this.properties)
                return this.properties;
            }
            onFailed("组件数据缺少,未能保存,请完善。");
            return ;
        },
        initProperties: function () {
            this.properties.logoLinkList = [];
            /*<![CDATA[*/
            var categoryId = -1;
            var categorys = /*[[${@cmsDataSourceService.findLinkCategory()}]]*/ 'Sebastian';
            $.each(categorys, function (i, obj) {
                if (categoryId == -1) {
                    categoryId = obj.id;
                }
                $(".LinkCategory").append('<option value="' + obj.id + '" >' + obj.title + '</option>');
            });
            /*]]>*/
            this.findByLink(categoryId);
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
            this.changeLinkCategory();
        },
        close: function (gloablId) {
            $('.linkdiv').off("change", ".LinkCategory");
        }
    }
});
