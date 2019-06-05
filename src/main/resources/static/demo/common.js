// 定义对象
var user = {
    id : 1,
    name : "tangxi",
}
// 对象数组
var userArr = [];

var _fn = {
    jump : function (url) {
        location.href = url;
    },
    myAjax :function(url,param){
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            async:true,
            data:param,
            xhrFields: {// 跨域
                withCredentials: true
            },
            success: function (json) {
                if (json.code == 'success') {
                }
            },
            error: function (err, textStatus, errorThrown) {
                console.log("err = " + JSON.stringify(err));
                console.log("errorThrown = " + JSON.stringify(errorThrown));
            }
        });
    },
}
