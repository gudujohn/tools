//表情配置文件
var faceMap = [
  'weixiao,微笑',
  'piezui,撇嘴',
  'se,色',
  'fadai,发呆',
  'deyi,得意',
  'liulei,流泪',
  'haixiu,害羞',
  'bizui,闭嘴',
  'shui,睡',
  'daku,大哭',
  'ganga,尴尬',
  'fanu,发怒',
  'tiaopi,调皮',
  'ciya,呲牙',
  'jingya,惊讶',
  'nanguo,难过',
  'ku,酷',
  'lenghan,冷汗',
  'zhuakuang,抓狂',
  'tu,吐',
  'touxiao,偷笑',
  'keai,可爱',
  'baiyan,白眼',
  'aoman,-傲慢',
  'jie,饥饿',
  'kun,困',
  'jingkong,惊恐',
  'liuhan,流汗',
  'hanxiao,憨笑',
  'dabing,大兵',
  'fendou,奋斗',
  'zhouma,咒骂',
  'yiwen,疑问',
  'xu,嘘',
  'yun,晕',
  'zhemo,折磨',
  'shuai,衰',
  'kulou,骷髅',
  'qiaoda,敲打',
  'zaijian,再见',
  'cahan,擦汗',
  'koubi,抠鼻',
  'guzhang,鼓掌',
  'qiudale,糗大了',
  'huaixiao,坏笑',
  'zuohengheng,左哼哼',
  'youhengheng,右哼哼',
  'haqian,哈欠',
  'bishi,鄙视',
  'weiqu,委屈',
  'kuaikule,快哭了',
  'yinxian,阴险',
  'qinqin,亲亲',
  'xia,吓',
  'kelian,可怜',
  'caidao,菜刀',
  'xigua,西瓜',
  'pijiu,啤酒',
  'lanqiu,篮球',
  'pingpang,乒乓',
  'kafei,咖啡',
  'fan,饭',
  'zhutou,猪头',
  'meigui,玫瑰',
  'diaoxie,凋谢',
  'shiai,示爱',
  'aixin,爱心',
  'xinsui,心碎',
  'dangao,蛋糕',
  'shandian,闪电',
  'zhadan,炸弹',
  'dao,刀',
  'zuqiu,足球',
  'piaochong,瓢虫',
  'bianbian,便便',
  'yueliang,月亮',
  'taiyang,太阳',
  'liwu,礼物',
  'yongbao,拥抱',
  'qiang,强',
  'ruo,弱',
  'woshou,握手',
  'shengli,胜利',
  'baoquan,抱拳',
  'gouyin,勾引',
  'quantou,拳头',
  'chajin,差劲',
  'aini,爱你',
  'no,NO',
  'ok,OK'
];

function generatorFace(faceName) {
  var faceHtml;
  faceMap.forEach(function (item) {
    var tempItemObject = item.split(',');
    if (tempItemObject[1] == faceName) {
      var src = "/img/" + tempItemObject[0] + '.gif';
      faceHtml = '<img src="' + src + '"/>';
    }
  });
  return faceHtml;
}

module.exports = {
  getFaceHtml(msg) {
    var faceBeginArr = msg.split('[:');
    if (msg.indexOf('[:') >= 0) {
      var faceNameHistory = [];
      var time = 0;
      faceBeginArr.forEach(function (item) {
        if (item.indexOf(']') >= 0) {
          var faceName = item.split(']')[0];
          if (!faceNameHistory.includes(faceName)) {
            faceNameHistory.push(faceName);
          }
          time ++;
        }
      });
      faceNameHistory.forEach(function (item) {
        var faceHtml = generatorFace(item);
        var repStr = "[:" + item + "]";
        for (var i=0; i < time; i++){
          msg = msg.replace(repStr, faceHtml);
        }
      });
      return msg;
    }
    return msg;
  }
};