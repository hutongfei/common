

处理前端js 时间误差的问题

 1 首次进入记录js 前端客户端当前时间  frontStartDate = new Date()

2 设置计数器为 count 为 0

3 动态改变服务器地址时 

必须用setTimeOut 代替setInterVal  

每次计算器 执行的程序

count ++ 

的时间长，offset = new Date().getTimer - (首次进入页面的时间.getTimer +count*1000)

下次多少秒后执行 nextTimer = 1000 - offset



初始化服务器时间

```js
            intiServerDate() {
                let that = this
                this.$http.get('/api/v1/get_server_date.json').then(function (res) {
                    that.serveDate = res.serveDate
                    that.serverStamp = res.serverStamp

                    that.frontStartDate = new Date().getTime()
                    that.serverTimer =  setTimeout(that.getDynamicServerDate,that.interval)
                    that.dynamicTimer = setInterval(that.getDynamicDateDiff,that.interval)
                })
            },	
```



```js
            getDynamicServerDate() {
                this.count ++
                var offset = new Date().getTime() - (this.frontStartDate + this.count * this.interval);
                var nextTime = this.interval - offset;
                if (nextTime < 0) {
                    nextTime = 0
                };
                console.log("误差：" + offset + "ms，下一次执行：" + nextTime );
                this.serveDate = moment(new Date(new Date(this.serveDate).getTime() + 1000)).format('YYYY/MM/DD HH:mm:ss')
                this.serverTimer =  setTimeout(this.getDynamicServerDate,nextTime)
            },
```

