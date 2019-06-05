// 遍历一位数组
$.each(arr,function(index,value){
    alert(i+"..."+value);
});

// 遍历二维数组
$(function () {
    $.each([["aaa", "bbb", "ccc"], ["ddd", "eee", "fff"], ["ggg", "hhh", "iii"]], function (index, item) {
        alert(index + "..." + item);
        //输出0...aaa,bbb,ccc  1...ddd,eee,fff  2...ggg,hhh,iii   这时的index为数组下标,item相当于取这二维数组中的每一个数组
        $.each(item, function (index, itemobj) {
            alert(index + "....." + itemobj);
        });
    });
    //输出0...aaa,bbb,ccc  0...aaa 1...bbb 2...cccc  1...ddd,eee,fff  0...ddd 1...eee 2...fff  2...ggg,hhh,iii 0...ggg 1...hhh 2...iii
});

// 遍历map
$.each(map,function(key,values){//每一行
    console.log("key = " + key);
    var obj = values;
    console.log("obj.title = " + obj.title);
});