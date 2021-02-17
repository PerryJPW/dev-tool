initNav()
initTab()
setConnectionSettings()
var showKey = null

function initTree() {
    //树形组件
    layui.use(['tree', 'util', 'jquery'], function () {
        var $ = layui.$
        var tree = layui.tree,
            layer = layui.layer,
            util = layui.util
            //模拟数据1
            , data2 = [{
                title: 'db0',
                id: 1,
                checked: true
            }, {
                title: 'db1(0)',
                id: 1001
            }, {
                title: 'db2(0)',
                id: 1002
            }, {
                title: 'db3(0)',
                id: 2
            }]
        tree.render({
            elem: '#keys-tree',
            data: getKeys(),
            showLine: true //是否开启连接线
            ,
            accordion: true,
            click: function (obj) {
                var data = obj.data; //获取当前点击的节点数据
                showKey = null

                // layer.msg('状态：' + obj.state + '<br>节点数据：' + JSON.stringify(data));
                if (data.title == '(null)' && data.id == -1) {
                    $("#input-key").val('')
                    $("#input-ttl").val('')
                    layer.msg("no data")
                }
                if (data.type != null) {
                    for (let i = 0; i < 5; i++) {
                        let tmpId = '#view-' + i;
                        $(tmpId).attr('hidden', true)
                    }
                    $("#view-no-data").attr('hidden', true)
                    if (data.type == 'string') {
                        $("#view-0").removeAttr('hidden')
                        var s = getString(data.title) //TODO
                        $("#input-key").val(s.key)
                        $("#input-ttl").val(s.ttl)
                        $("#string-value-id").val(s.value)
                        showKey = data.title
                    } else if (data.type == "hash") {
                        $("#view-1").removeAttr('hidden')
                        initHashTable(data.title);
                        showKey = data.title
                    } else if (data.type == "list") {
                        $("#view-2").removeAttr('hidden')
                        initListTable(data.title)
                        showKey = data.title
                    } else if (data.type == "set") {
                        $("#view-3").removeAttr('hidden')
                        initSetTable(data.title)
                        showKey = data.title
                    } else if (data.type == "zset") {
                        $("#view-4").removeAttr('hidden')
                        initZsetTable(data.title)
                        showKey = data.title
                    } else if (data.type == "root" ||data.type == "namespace") {
                        $("#input-key").val('')
                        $("#input-ttl").val('')
                    } else {
                        layer.msg("系统错误，非法点击")
                    }


                } else {
                    $("#input-key").val("")
                    $("#input-ttl").val("")
                    $("#view-no-data").removeAttr('hidden')
                    for (let i = 0; i < 5; i++) {

                        let tmpId = '#view-' + i;
                        $(tmpId).attr('hidden', true)

                    }
                }

            }
        });

        tree.render({
            elem: '#database-tree'
            , data: getDBCount()
            , showLine: false //是否开启连接线
            , showCheckbox: true
            , oncheck: function (obj) {
                console.log(obj.data); //得到当前点击的节点数据
                console.log(obj.checked);
                console.log(obj.elem); //得到当前节点元素
            }
        });
    });
}

function initNav() {
    //顶部导航、选项卡
    layui.use('element', function () {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function (elem) {
            //console.log(elem)
            // layer.msg(elem.text());
        });
    });
}
function initTab() {
    layui.use('element', function () {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        element.on('tab(tab-bar)', function (data) {
            if (data.index == 0) {
                setConnectionSettings()
            } else if (data.index == 1) {
                initTree()
            } else if (data.index == 2) {

            } else {
                layer.msg("error!")
            }

        });
    });
}


//数据管理表单
layui.use(['form', 'jquery'], function () {
    var form = layui.form;
    var $ = layui.$
    var loading = null
    //监听提交
    form.on('submit(connectionRedis)', function (data) {

        var connectUrl = prefix + "connect-redis"
        // var connectUrl = prefix + "connect-redis?host=" + data.field.connectionIP +
        //     "&port=" + data.field.connectionPort + "&password=" + data.field.connectionPassword +
        //     "&name=" + data.field.connectionName
        var dataStr = {
            "host": data.field.connectionIP,
            "port": data.field.connectionPort,
            "password": data.field.connectionPassword,
            "name": data.field.connectionName

        }
        var connectionData = JSON.stringify(dataStr);
        $.ajax({
            url: connectUrl,
            type: 'POST',
            // async: false,
            data: connectionData,
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            processData: false,
            cache: false,
            // timeout: 2000, 超时处理
            beforeSend: function () {
                loading = layer.load(1)
            },
            success: function (data) {
                console.log(data)
                if (data.state == 10000) {
                    setConnectionSettings()
                    layer.msg("连接成功")
                } else {
                    form.val("settings-form", {
                        "connectionCheckbox": false
                    });

                    layer.msg(data.msg)
                }
            },
            complete: function () {
                layer.close(loading)
            }

        })
        return false;
    });
    $("list-rpush").click(function () {
        layer.msg('rpush')
        return false //解决点击后会刷新页面的问题
    });
    $("list-lpush").click(function () {
        layer.msg('rpush')
        return false //解决点击后会刷新页面的问题
    });

});

function setConnectionSettings() {
    layui.use(['form', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.$
        var getConnectUrl = prefix + "get-connection"
        $.ajax({
            url: getConnectUrl,
            type: 'GET',
            // async: false,
            contentType: false,
            processData: false,
            cache: false,
            timeout: 2000,//TODO 超时处理
            success: function (data) {
                if (data.state == 10000) {
                   
                    form.val("settings-form", {
                        "connectionName": data.data.name
                        , "connectionIP": data.data.host
                        , "connectionPort": data.data.port
                        , "connectionCheckbox": data.data.connection
                    });

                } else {
                    layer.msg("error:" + data.msg)
                }
            }
        })
    });
}

//数据添加表单
layui.use(['form', 'jquery'], function () {
    var form = layui.form;
    var $ = layui.$

    //监听提交
    form.on('submit(dataSubmit)', function (data) {
        layer.msg(JSON.stringify(data.field));
        return false;
    });
    form.on('select(selectType)', function (data) {
        layer.msg(data.value);
        for (let i = 0; i < 5; i++) {
            let tmpId = '#input-' + i;
            if (i == data.value) {
                $(tmpId).removeAttr('hidden')
            } else {
                $(tmpId).attr('hidden', true)
            }
        }

    });

    $("#data-reset").click(function () {
        for (let i = 0; i < 5; i++) {
            let tmpId = '#input-' + i;
            if (i == 0) {
                $(tmpId).removeAttr('hidden')
            } else {
                $(tmpId).attr('hidden', true)
            }
        }
        $("#addForm")[0].reset()
        return false //解决点击后会刷新页面的问题
    });

});

//数据管理button 点击
layui.use('jquery', function () {
    var $ = layui.$
    $("#renameKey").click(function () {
        var newKey = $("#input-key").val()
        var renameKeyUrl = prefix + 'rename-key?oldKey=' + showKey + '&newKey=' + newKey
        console.log(renameKeyUrl)
        if (showKey != newKey) {
            $.get(renameKeyUrl, function (data) {
                if (data.state == 10000) {
                    showKey = newKey
                    // initTree()
                    $("#refreshKey").click()
                } else {
                    layer.msg(data.msg)
                }
            })
        } else {
            layer.msg("无更新")
        }
        return false //解决点击后会刷新页面的问题
    });
    $("#refreshKey").click(function () {
        initTree()
        // layer.msg("refreshKey点击事件");
        var selector1 = "div[data-id='" + showKey + "'] span:last"
        var selector2 = "div[data-id='" + showKey + "']"
        $(selector1).click()
        //TODO 刷新处理
        // $(selector2).parent().attr("style","display: block;");

        $(selector2).parent().prev().children().click()

        return false
    });
    $("#deleteKey").click(function () {
        layer.msg("deleteKey点击事件");
        return false
    });
    $("#setTTL").click(function () {
        layer.msg("setTTL点击事件");
        return false
    });
});

function initHashTable(key) {

    // hash table
    layui.use('table', function () {
        var table = layui.table;
        var $ = layui.$;
        var hashUrl = prefix + '/get-value?key=' + key + '&type=hash';
        //监听单元格编辑
        table.on('edit(hash-data-table)', function (obj) {
            var value = obj.value //得到修改后的值
                ,
                data = obj.data //得到所在行所有键值
                ,
                field = obj.field; //得到字段
            layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
        });
        table.render({
            elem: '#hash-data-table',
            url: hashUrl,
            method: 'get',
            parseData: function (res) { //res 即为原始返回的数据
                $("#input-key").val(res.data.key)
                $("#input-ttl").val(res.data.ttl)

                return {
                    "code": res.data.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.count, //解析数据长度
                    "data": res.data.values //解析数据列表
                };
            },
            cols: [
                [{
                    align: 'left',
                    title: 'value(hash)',
                    colspan: 4
                } //colspan即横跨的单元格数，这种情况下不用设置field和width
                ],
                [{
                    field: 'id',
                    title: 'row',
                    width: 100
                }, {
                    field: 'field',
                    title: 'field'
                }, {
                    field: 'value',
                    title: 'value',
                    edit: 'text'
                }, {
                    fixed: 'right',
                    width: 150,
                    align: 'center',
                    toolbar: '#hash-table-bar'
                }]
            ]
            // ,done: function(res, curr, count){
            //     //如果是异步请求数据方式，res即为你接口返回的信息。
            //     //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            //     console.log(res);

            //     //得到当前页码
            //     console.log(curr); 

            //     //得到数据总量
            //     console.log(count);
            //   }


        });
        //监听工具条
        table.on('tool(hash-data-table)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.msg('ID：' + data.id + ' 的查看操作');
            } else if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.alert('编辑行：<br>' + JSON.stringify(data))
            }
        });
    });
}

function initListTable(key) {
    var $ = layui.$;
    var listUrl = prefix + '/get-value?key=' + key + '&type=list';

    // list table
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#list-data-table',
            url: listUrl,
            parseData: function (res) { //res 即为原始返回的数据
                $("#input-key").val(res.data.key)
                $("#input-ttl").val(res.data.ttl)
                console.log(res.data.values)
                return {
                    "code": res.data.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.count, //解析数据长度
                    "data": res.data.values //解析数据列表
                };
            },
            cols: [
                [{
                    align: 'left',
                    title: 'value(list)',
                    colspan: 3
                } //colspan即横跨的单元格数，这种情况下不用设置field和width
                ],
                [{
                    field: 'id',
                    title: 'row'
                }, {
                    field: 'value',
                    title: 'value',
                    edit: 'text'
                }, {
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#list-table-bar'
                }]
            ],
            page: true,
            limit: 10
        });
        //监听工具条
        table.on('tool(list-data-table)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.msg('ID：' + data.id + ' 的查看操作');
            } else if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.alert('编辑行：<br>' + JSON.stringify(data))
            }
        });
    });
}

function initSetTable(key) {

    var $ = layui.$;
    var setUrl = prefix + '/get-value?key=' + key + '&type=set';
    // set table
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#set-data-table',
            url: setUrl,
            parseData: function (res) { //res 即为原始返回的数据
                $("#input-key").val(res.data.key)
                $("#input-ttl").val(res.data.ttl)
                return {
                    "code": res.data.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.count, //解析数据长度
                    "data": res.data.values //解析数据列表
                };
            },
            cols: [
                [{
                    align: 'left',
                    title: 'value(set)',
                    colspan: 3
                } //colspan即横跨的单元格数，这种情况下不用设置field和width
                ],
                [{
                    field: 'id',
                    title: 'row'
                }, {
                    field: 'value',
                    title: 'value',
                    edit: 'text'
                }, {
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#set-table-bar'
                }]
            ]

            , page: true,
            limit: 10
        });
        //监听工具条
        table.on('tool(set-data-table)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.msg('ID：' + data.id + ' 的查看操作');
            } else if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.alert('编辑行：<br>' + JSON.stringify(data))
            }
        });
    });
}

function initZsetTable(key) {
    var $ = layui.$;
    var zsetUrl = prefix + '/get-value?key=' + key + '&type=zset';
    // zset table
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#zset-data-table',
            url: zsetUrl,
            parseData: function (res) { //res 即为原始返回的数据
                $("#input-key").val(res.data.key)
                $("#input-ttl").val(res.data.ttl)
                return {
                    "code": res.data.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.count, //解析数据长度
                    "data": res.data.values //解析数据列表
                };
            },
            initSort: {
                field: 'score', //排序字段，对应 cols 设定的各字段名
                type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
            },
            cols: [
                [{
                    align: 'left',
                    title: 'value(zset)',
                    colspan: 4
                } //colspan即横跨的单元格数，这种情况下不用设置field和width
                ],
                [{
                    field: 'id',
                    title: 'row'
                }, {
                    field: 'score',
                    width: 100,
                    title: 'score',
                    edit: 'text',
                    sort: true
                }, {
                    field: 'value',
                    title: 'value',
                    edit: 'text'
                }, {
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#zset-table-bar'
                }]
            ],
            page: true,
            limit: 10
        });
        //监听工具条
        table.on('tool(zset-data-table)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.msg('ID：' + data.id + ' 的查看操作');
            } else if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.alert('编辑行：<br>' + JSON.stringify(data))
            }
        });
    });
}