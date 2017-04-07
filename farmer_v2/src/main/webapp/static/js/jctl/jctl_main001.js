/*
 *私有js  扩充jquery
 *
 *  */
$.extend({
    //弹出js
    jc_detail: function () {
        layer.open({
            title: "详情",
            type: 2,
            area: ['auto', 'auto'],
            fixed: false, //不固定
            maxmin: true,
            content: $("#detail")
        });
    },

    //正则验证
    jc_regex: function (regex, var1) {
        var regex = regex;
        if (!regex.exec(var1)) return false;
        return true
    },

    //验证手机号码
    jc_regexMobile: function (var1) {
        var regex = /^[\d]{5,20}$/;
        if (!regex.exec(var1)) return false;
        return true
    },jc_regexEmail: function (var1) {
        var regex = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
        if (regex.test(var1))  return true;
        return false;
    }
});