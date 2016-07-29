
/**
 * Created by lhx on 2016/6/27.
 */
    var logoWall = {
        properties: null,
        findByLink: function(categoryId){
            var data = getDataSource('/dataSource/findLink/',categoryId)
            $.each(data, function (i, obj) {
                $(".thumbnailList").append('<img src="' + obj.thumbUri + '" />');
            })
            this.properties.logoLinkList = data;
        },
        changeLinkCategory:function(){
        var me = this;
            $(".LinkCategory").change(function () {
                var categoryId = $(this).val();
                if (categoryId != '') {
                    me.findByLink(categoryId);
                }
            });
        },
        saveComponent: function () {
            if(this.properties.logoLinkList!=null ){
                return this.properties;
            }
            layer.msg("组件参数缺少,未能保存,请完善。");
            return null;
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
        init: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
            this.changeLinkCategory();
        }
    };
