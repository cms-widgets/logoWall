/**
 * Created by lhx on 2016/6/27.
 */
CMSWidgets.initWidget({
    // 编辑器相关
    editor: {
        saveComponent: function (onFailed) {
            if (this.properties.linkSerial == null || this.properties.linkSerial == '')
            onFailed("组件数据缺少,未能保存,请完善。");
            return false;
        }
    }
});
