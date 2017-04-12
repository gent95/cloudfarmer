var http = require('http');

http.createServer(function (request, response) {

    // 发送 HTTP 头部
    // HTTP 状态值: 200 : OK
    // 内容类型: text/plain
    response.writeHead(200, {'Content-Type': 'text/plain'});

    // 发送响应数据 "Hello World"
    response.end("aiasdasd");
}).listen(8088);




/**
 * @api {post} /login/doLogin 登录接口
 * @apiName 登录接口
 * @apiGroup doLogin
 * @apiParam  loginName  password
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
    {
        "flag": 1,
        "userId": "18"
    }
 or
     {
        "flag": 0,
        "msg": "账号或密码错误！"
    }
 or
     {
        "flag": 0,
        "msg": "用户被锁定，请联系管理员！"
    }
 or
     {
           "flag": 0,
           "msg": "操作失败！"
       }
 */


/**
 * @api {post} /register 注册接口
 * @apiName 注册接口
 * @apiGroup register
 * @apiParam  loginName  mobile  name  password companyName
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
     {
         "flag": 1
     }
or
     {
         "flag": "0",
         "msg": "该手机已被注册！"
     }
or
     {
        "flag": "0",
        "msg": "操作失败！"
    }
 */


/**
 * @api {post}  /help/search    帮助信息查询接口
 * @apiName 帮助信息查询接口
 * @apiGroup search
 * @apiParam  question
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
     {
     "flag": 1,
     "info": [
         {
             "id": "1",
             "answer": "我哪知道",
             "question": "天上星星有多少？"
         },
         {
             "id": "2",
             "answer": "一茬，每年七八月份种植。",
             "question": "小麦一年几茬？"
         }
     ]
    }
or
     {
        "flag": "0",
        "msg": "操作失败！"
    }
 */

/**
 * @api {post}  /updatePassword    密码修改接口
 * @apiName 帮助信息查询接口
 * @apiGroup updatePassword
 * @apiParam  loginName  password
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
     {
     "flag": 1
    }
or
     {
        "flag": "0",
        "msg": "操作失败！"
    }
 */


/**
 * @api {post}  /sendSmsCode    发送短信接口
 * @apiName 帮助信息查询接口
 * @apiGroup sendSmsCode
 * @apiParam  mobile
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
     {
        "flag": 1,
        "code": 123456
    }
or
     {
        "flag": "0",
        "msg": "操作失败！"
    }
 */

/**
 * @api {post}  /user/update    用户资料修改接口
 * @apiName 用户资料修改
 * @apiGroup update
 * @apiParam  mobile
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
     {
        "flag": 1,
    }
or
     {
        "flag": "0",
        "msg": "操作失败！"
    }
 */

//-------------------农场管理-----------------------------
/**
 * @api {post}  /farmer/list    农场信息查询接口
 * @apiName 农场信息查询接口
 * @apiGroup search
 * @apiParam  farmer userId
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明
 * @apiSuccessExample Success-Response:
 *
 {
 "flag": 1,
 "info": [
     {
         "id": "1",
         "name": "农场一",
         "addr": "北京"，
         "farmlandNumber":"10",
         "plantVariety":"大豆"
     },
     {
         "id": "2",
         "name": "农场二",
         "addr": "山东"，
         "farmlandNumber":"10",
         "plantVariety":"水稻"
     }
 ]
}
 or
 {
    "flag": "0",
    "msg": "操作失败！"
}
 or{
    "flag":"0",
    "msg":"抱歉，没有该农场信息!"
 }
 */
/**
 * @api {post}  /farmer/saveOrUpdate    农场添加修改接口
 * @apiName 农场添加修改接口
 * @apiGroup saveOrUpdate
 * @apiParam  farmer
 * @apiSuccess {String} flag 状态1表示成功，0表示失败.
 * @apiSuccess {String} msg  失败原因说明
 * @apiSuccessExample Success-Response:
 * {
 *  "flag":"0",
 *  "msg":"添加失败"
 * }
 * or{
 *  "flag":"1",
 *  "msg":"添加成功"
 *  }
 */
/**
 * @api{post} /farmer/get 农场详情接口
 * @apiName 农场详情接口
 * @apiGroup seach
 * @apiParam id
 * @apiSuccess flag 状态1表示成功，0表示失败
 * @apiSuccess msg 原因说明
 * @apiSuccessExample Success-Response:
 *  {
 *  "flag":"0",
 *  "msg":"操作失败"
 * }
 * or{
 *  "flag":"1",
 *  "info":"[{
 *         "id","1",
 *         "name","农场一",
 *         "farmerNum","101",
 *         "addr","北京",
 *         "area","500",
 *         "plantVariety","大豆",
 *         "user.name","jack",
 *         "farmlandNumber","2",
 *         "relayNumber","1"
 *      }]"
 *  }
 */
// /**
//  * @api {post}  /farmer/update    农场修改接口
//  * @apiName 农场修改接口
//  * @apiGroup update
//  * @apiParam  farmer
//  * @apiSuccess {String} flag 状态1表示成功，0表示失败.
//  * @apiSuccess {String} msg  失败原因说明
//  * @apiSuccessExample Success-Response:
//  * {
//  *  "flag":"0",
//  *  "msg":"修改失败"
//  * }
//  * or{
//  *  "flag":"1",
//  *  "msg":"修改成功"
//  *  }
//  */
/**
 * @api {post}  /farmer/delete  农场删除接口
 * @apiName 农场删除接口
 * @apiGroup delete
 * @apiParam  farmer
 * @apiSuccess {String} flag 状态1表示成功，0表示失败.
 * @apiSuccess {String} msg  失败原因说明
 * @apiSuccessExample Success-Response:
 * {
 *  "flag":"0",
 *  "msg":"删除失败"
 * }
 * or{
 *  "flag":"1",
 *  "msg":"删除成功"
 *  }
 */
//------------------------农田管理--------------------------------

/**
 * @api {post}  /farmland/list    农田信息查询接口
 * @apiName 农田信息查询接口
 * @apiGroup search
 * @apiParam  farmland userId
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  原因说明
 * @apiSuccessExample Success-Response:
 *
 {
 "flag": 1,
 "info": [
 {
            "area":"500",
            "plantVaritety":"大豆",
            "nodeNumber":"5",
            "alias":"第一个田",
            "id":"1"
            },
 {
            "area":"100",
            "plantVaritety":"1",
            "nodeNumber":"2",
            "alias":"大兴农场",
            "id":"3"
 }
 ]
}
 or
 {
    "flag": "0",
    "msg": "操作失败！"
}
 or{
    "flag":"0",
    "msg":"抱歉，没有该农田信息!"
 }
 */
/**
 * @api {post}  /farmland/save    农田添加接口
 * @apiName 农田添加接口
 * @apiGroup save
 * @apiParam  farmland
 * @apiSuccess {String} flag 状态1表示成功，0表示失败.
 * @apiSuccess {String} msg  失败原因说明
 * @apiSuccessExample Success-Response:
 * {
 *  "flag":"0",
 *  "msg":"添加失败"
 * }
 * or{
 *  "flag":"1",
 *  "msg":"添加成功"
 *  }
 */
/**
 * @api{post} /farmland/get 农田详情接口
 * @apiName 农田详情接口
 * @apiGroup seach
 * @apiParam id
 * @apiSuccess flag 状态1表示成功，0表示失败
 * @apiSuccess msg 原因说明
 * @apiSuccessExample Success-Response:
 *  {
 *  "flag":"0",
 *  "msg":"操作失败"
 * }
 * or{
 *  "flag":"1",
 *  "info":"[
 *  {
 *              "area":"200",
 *              "farmlandNum":"11165",
 *              "relay.relayNum":"323430303034",
 *              "plantVaritety":"大豆",
 *              "nodeNumber":"2",
 *              "alias":"第一个田",
 *              "user.name":"系统管理员",
 *              "id":"1",
 *              "landType":"1",
 *              "usedName":"jack",
 *              "assigneTime":"2017-05-26"
 *              }
 *              ]
 *  }
 */
/**
 * @api {post}  /farmland/update    农田修改接口
 * @apiName 农田修改接口
 * @apiGroup update
 * @apiParam  farmland
 * @apiSuccess {String} flag 状态1表示成功，0表示失败.
 * @apiSuccess {String} msg  失败原因说明
 * @apiSuccessExample Success-Response:
 * {
 *  "flag":"0",
 *  "msg":"修改失败"
 * }
 * or{
 *  "flag":"1",
 *  "msg":"修改成功"
 *  }
 */
/**
 * @api {post}  /farmland/delete  农田删除接口
 * @apiName 农田删除接口
 * @apiGroup delete
 * @apiParam  farmland
 * @apiSuccess {String} flag 状态1表示成功，0表示失败.
 * @apiSuccess {String} msg  失败原因说明
 * @apiSuccessExample Success-Response:
 * {
 *  "flag":"0",
 *  "msg":"删除失败"
 * }
 * or{
 *  "flag":"1",
 *  "msg":"删除成功"
 *  }
 */
//------------------------新闻管理---------------
/**
 * @api /news/list 新闻查询管理
 * @apiName 新闻管理接口
 * @apiGroup select
 * @apiParam
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "info":"[
 *{"id":1,
 * "title":"中国农业展的进程",
 * "content":"　上周（2017年3月13日—2017年03月19日）清吉市场气温回升，蔬菜价格走势整体平稳。上周市场监测的28个品种中，日平均价格为10.3元/公斤，日交易量为712吨，市场货源充足，交易比较活跃。上上周部分蔬菜有了小微上涨，上",
 * "dataTime:"2017-03-27"
 * }，
 *{"id":2,
 * "title":"dfawer",
 * "content":"　上周（2017年3月13日—2017年03月19日）清吉市场气温回升，蔬菜价格走势整体平稳。上周市场监测的28个品种中，日平均价格为10.3元/公斤，日交易量为712吨，市场货源充足，交易比较活跃。上上周部分蔬菜有了小微上涨，上",
 * "dataTime:"2017-03-27"
 * }
 * ]"
 * }
 * or{
 * "flag":"0",
 * "msg":"抱歉，没有查询到该数据"
 * }
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */
//----------------------节点-------------------------
/**
 * @api /node/list 农田所属下的节点查询管理
 * @apiName 农田所属下的节点查询管理接口
 * @apiGroup select
 * @apiParam  node userId
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "info":"[
 * {
 * "farmlandName":"一号棚",
 * "nodeNum":"246C8E93",
 * "id":"35",
 * "power":"",
 * "usedName":"rose"}
 * ]
 * or{
 * "flag":"0",
 * "msg":"抱歉，没有查询到该数据"
 * }
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */

/**
 * @api /node/detailList 节点详情数据管理
 * @apiName 节点详情数据管理
 * @apiGroup select
 * @apiParam   nodeNum
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "info":"[
 *{
 * "airTemperature":-3.55,
 * "openFlag":"0",
 * "addTime":1489139954000,
 * "co2":65535.0,
 * "airHumidity":-3.55,
 * "nodeMac":"246C8E72",
 * "power":56
 * },{
 * "airTemperature":17.51,
 * "openFlag":"1",
 * "addTime":1490583624000,
 * "co2":840.0,
 * "airHumidity":30.77,
 * "nodeMac":"246C8E93",
 * "power":100
 * }
 * or{
 * "flag":"0",
 * "msg":"抱歉，没有查询到该数据"
 * }
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */
/**
 * @api /node/get 节点详情管理
 * @apiName 节点详情管理接口
 * @apiGroup select
 * @apiParam   id
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "info":"[
 * {"farmlandName":"一号棚",
 * "nodeNum":"246C8E93",
 * "addTime":1489130279000,
 * "user.name":"mark",
 * "relayName":"934483304950349",
 * "id":"35",
 * "power":"",
 * "type":"",
 * "usedName":"rose",
 * "cycle":""}
 * ]}
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */
/**
 * @api /node/update 节点修改管理
 * @apiName 节点修改管理接口
 * @apiGroup update
 * @apiParam   node
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "msg":"修改成功"}
 * or{
 * "flag":"0",
 * "msg":"修改失败"
 * }
 */

/**
 * @api /node/HandOpenClose 开关控制管理
 * @apiName 开关控制管理接口
 * @apiGroup update
 * @apiParam   nodeMac ,status
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "msg":"成功关闭"
 * }
 * or {
 * "flag":"1",
 * "msg":"成功打开"
 * }
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */
/**
 * @api /node/autoMsg 智能控制管理
 * @apiName 智能控制管理接口
 * @apiGroup add
 * @apiParam   waringCycle
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "msg":"设置成功"
 * }
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */
/**
 * @api /node/cycleMsg 周期控制管理
 * @apiName 周期控制管理接口
 * @apiGroup add
 * @apiParam   nodeCollectionCycle ,off ,on
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:
 * {
 * "flag":"1",
 * "msg":"设置成功"
 * }
 * or{
 * "flag":"0",
 * "msg":"操作失败"
 * }
 */
//---------------------市场动态-----------------------------
/**
 * @api{post} /aAnlisDocController/getMktDyns 市场动态接口
 * @apiName 获取市场动态即农产品价格
 * @apiParam areaId 区域ID 取值范围:(1-7)  1:华北 2:华东 3:华南 4:华中 5:西北 6:西南 7:东北
 * @apiSuccess String{flag} 状态1表示成功，0表示失败.
 * @apiSuccessExample Success-Response:/aAnlisDocController/getMktDyns?areaId=1
 *{
    "flag": 1,
    "mktDyns": [
        {
            "id": 0,
            "productName": "标准粉",
            "price": "3",
            "marketName": "石家庄桥西",
            "releaseDate": "2017-03-27"
        },
        {
            "id": 0,
            "productName": "菜花",
            "price": "2.5",
            "marketName": "晋太原河西",
            "releaseDate": "2017-03-27"
        },
        {
            "id": 0,
            "productName": "菠萝",
            "price": "3.2",
            "marketName": "晋太原河西",
            "releaseDate": "2017-03-27"
        },
        {
            "id": 0,
            "productName": "菠菜",
            "price": "1.35",
            "marketName": "晋太原河西",
            "releaseDate": "2017-03-27"
        },
        {
            "id": 0,
            "productName": "白萝卜",
            "price": "0.85",
            "marketName": "晋太原河西",
            "releaseDate": "2017-03-27"
        },
        {
            "id": 0,
            "productName": "白萝卜",
            "price": "1.2",
            "marketName": "天津红旗",
            "releaseDate": "2017-03-27"
        },
        {
            "id": 0,
            "productName": "菠菜",
            "price": "2",
            "marketName": "天津红旗",
            "releaseDate": "2017-03-27"
        }
    ]
}

 */

/**
 * @api {post} /relay/list 中继数据接口
 * @apiName 中继数据接口
 * @apiGroup list
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
 {
     "flag": 1,
     "userId": "18"
 }
 or
 {
    "flag": 1,
    "data": [
        {
            "id": "33",
            "farmerName": "暂未绑定到农场！",
            "nodeNum": 0,
            "powerSupply": "",
            "relayNum": "12312312311"
        }
    ]
}
 */


/**
 * @api {post} /relay/info 中继详情接口
 * @apiName 中继详情接口
 * @apiGroup info
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
 {
     "flag": 1,
     "userId": "18"
 }
 or
 {
    "flag": 1,
    "data": {
        "id": "24",
        "isNewRecord": false,
        "addTime": "2017-03-10 15:17:56",
        "relayNum": "353430303034",
        "bindingTime": "2017-03-10 15:17:56",
        "log": "11.00",
        "lat": "111.00",
        "area": "",
        "user": {
            "id": "cbaf1cd8e5374bcdbdcc07adb0265edc",
            "isNewRecord": false,
            "loginFlag": "1",
            "admin": false,
            "roleNames": ""
        },
        "used": {
            "id": "-1",
            "isNewRecord": false,
            "loginFlag": "1",
            "admin": false,
            "roleNames": ""
        },
        "powerSupply": "89%",
        "farmer": {
            "id": "2",
            "isNewRecord": false,
            "name": "云南农场园"
        }
    }
}
 */




/**
 * @api {post} /user/farmers 农场下所有农户列表接口
 * @apiName 农户列表接口
 * @apiGroup farmers
 * @apiSuccess {String} flag 状态1表示发送成功，0表示发送失败.
 * @apiSuccess {String} msg  失败原因说明(发送成功无此节点).
 * @apiSuccessExample Success-Response:
 *
 {
    "flag": 1,
    "data": [
        {
            "id": "902bd6bfbfd042638647e5b48c3406c3",
            "phone": "15510620136",
            "name": "Davi"
        },
        {
            "id": "fda9877fa50c4e3bb399c296944b1e53",
            "phone": "15510620135",
            "name": "rose"
        }
    ]
}
 or
 {
    "flag": 0,
    "userId": "操作失败！"
}
 */