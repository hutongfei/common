js 中插件封装（1）

```js
 //前面加;是防止跟其他js压缩时报错
;(function(global){
    //开启严格模式
    "use strict";
    //构造函数定义一个类    传参数
    function Scroll(el,options) {
        //some code
 
    };
 
 
    //原型上提供方法
    Scroll.prototype = {
        //定义方法
        show: function() {
            //some code
        }
        
    };
   
    if (typeof module !== 'undefined' && module.exports) {    //兼容CommonJs规范 
        module.exports = Scroll;
    }else if (typeof define === 'function'){   //兼容AMD/CMD规范
        define(function () {
            return Scroll
        })
    }else {    //注册全局变量，兼容直接使用script标签引入插件
        global.Scroll = Scroll;
    }
    
    
})(this);
```

使用

```javascript
var scroll = new Scroll("#demo",{});
scroll.show();
```

js 中插件封装（2）

```js
(function ($) {
    //1.得到$.ajax的对象
    var _ajax = $.ajax;
    $.ajax = function (options) {
        //2.每次调用发送ajax请求的时候定义默认的error处理方法
        var fn = {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                toastr.error(XMLHttpRequest.responseText, '错误消息', { closeButton: true, timeOut: 0, positionClass: 'toast-top-full-width' });
            },
            success: function (data, textStatus) { },
            beforeSend: function (XHR) { },
            complete: function (XHR, TS) { }
        }
        //3.如果在调用的时候写了error的处理方法，就不用默认的
        if (options.error) {
            fn.error = options.error;
        }
        if (options.success) {
            fn.success = options.success;
        }
        if (options.beforeSend) {
            fn.beforeSend = options.beforeSend;
        }
        if (options.complete) {
            fn.complete = options.complete;
        }
        //4.扩展原生的$.ajax方法，返回最新的参数
        var _options = $.extend(options, {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success: function (data, textStatus) {
                fn.success(data, textStatus);
            },
            beforeSend: function (XHR) {
                fn.beforeSend(XHR);
            },
            complete: function (XHR, TS) {
                fn.complete(XHR, TS);
            }
        });
        //5.将最新的参数传回ajax对象
        _ajax(_options);
    };
})(jQuery);	
```

3 使用闭包,将数据一次性挂载到window对象下

```js
(function(obj) {
    // var obj={};
    obj.a=function () {
        alert("闭包调用")
    }
    return obj;
 
})(window)
```



https://blog.csdn.net/Debug_feng/article/details/59122430?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param



继承 https://www.cnblogs.com/guojbing/p/10405862.html