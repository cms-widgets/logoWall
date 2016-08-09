
/**
 * Created by lhx on 2016/6/27.
 */
CMSWidgets.initWidget({
    // 编辑器相关
    editor: {
        properties: null,
        uploadImg: function () {
            var that = this;
            uploadForm({
                ui: '#logoImageUploader',
                inputName: 'file',
                maxWidth: 180,
                maxHeight: 130,
                isCongruent: false,
                successCallback: function (files, data, xhr, pd) {
                    that.properties.logoLinkList.push(data.fileUri);
                },
                deleteCallback: function (resp, data, jqXHR) {
                    $.grep(editor.properties.logoLinkList, function (obj, i) {
                        if (obj == data.fileUri) {
                            that.properties.logoLinkList.splice(i, 1);
                        }
                    })
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
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
            this.uploadImg();
        },
        close: function (gloablId) {

        }
    }
});
