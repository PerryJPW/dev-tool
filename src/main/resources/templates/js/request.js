function getKeys(){
    var $ = layui.$
    var getKeysUrl = prefix + '/get-keys';
    var keysData = [{
        title: 'string'
        , id: -1
    }, {
        title: 'hash'
        , id: -2
    }, {
        title: 'list'
        , id: -3
    }, {
        title: 'set'
        , id: -4
    }, {
        title: 'zset'
        , id: -5
    }];
    $.ajax({
        url: getKeysUrl,
        type: 'GET',
        async: false,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 2000,//TODO 超时处理
        success: function (data) {
            // console.log(data)
            if (data.state == 10000) {
                keysData = data.data
            } else {
                layer.msg("get keys error:" + data.msg)
            }
        }
    })
    return keysData
}

function getDBCount(){
    var $ = layui.$
    var getUrl = prefix + '/get-database';
    var resData = [{
        title: 'db0'
        , id: -1
    }, {
        title: 'db1'
        , id: -2
    }, {
        title: 'db2'
        , id: -3
    }, {
        title: 'db3'
        , id: -4
    }, {
        title: 'db4'
        , id: -5
    }];
    $.ajax({
        url: getUrl,
        type: 'GET',
        async: false,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 2000,//TODO 超时处理
        success: function (data) {
            // console.log(data)
            if (data.state == 10000) {
                resData = data.data
            } else {
                layer.msg("get keys error:" + data.msg)
            }
        }
    })
    return resData
}

function getString(key){
    var $ = layui.$
    var getStringUrl = prefix + '/get-string?key='+key;
    var stringData;
    $.ajax({
        url: getStringUrl,
        type: 'GET',
        async: false,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 2000,//TODO 超时处理
        success: function (data) {
            if (data.state == 10000) {
                stringData = data.data
            } else {
                layer.msg("get keys error:" + data.msg)
            }
        }
    })
    return stringData
}

function getValueByKey(key,type){
    var $ = layui.$
    var getUrl = prefix + '/get-value?key='+key;
    var stringData;
    $.ajax({
        url: getUrl,
        type: 'GET',
        // async: false,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 2000,//TODO 超时处理 
        success: function (data) {
            console.log(data)
            if (data.state == 10000) {
                stringData = data.data
            } else {
                layer.msg("get keys error:" + data.msg)
            }
        }
    })
    return stringData
}