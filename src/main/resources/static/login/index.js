function configType(value) {
  var headOption = "";
  if ("00001" == value) {
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00002" == value) {
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00003" == value) {
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00004" == value) {
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00005" == value) {
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00006" == value) {
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00007" == value) {
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00008" == value) {
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00009" == value) {
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00010" == value) {
      headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  } else if ("00013" == value) {
    headOption = headOption + "<option value='00013'>培训班结业</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
  } else {
    headOption = headOption + "<option value=''>请选择</option>";
    headOption = headOption + "<option value='00010'>最大兑换金额</option>";
    headOption = headOption + "<option value='00001'>系统登陆</option>";
    headOption = headOption + "<option value='00002'>课程学习</option>";
    headOption = headOption + "<option value='00003'>课程评论</option>";
    headOption = headOption + "<option value='00004'>恶意评论</option>";
    headOption = headOption + "<option value='00005'>问卷调查</option>";
    headOption = headOption + "<option value='00006'>分享课程</option>";
    headOption = headOption + "<option value='00007'>作业精华</option>";
    headOption = headOption + "<option value='00008'>问题点赞</option>";
    headOption = headOption + "<option value='00009'>最大兑换人数</option>";
    headOption = headOption + "<option value='00013'>培训班结业</option>";
  }
  // headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
  var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:160px;" disabled="disabled">' +
    headOption + '</select>';
  return option;
}

function configStatusType(value) {
  var headOption = "";
  if ("1" == value) {
    headOption = headOption + "<option value='1'>无效</option>";
    headOption = headOption + "<option value='0'>有效</option>";
  } else if ("0" == value) {
    headOption = headOption + "<option value='0'>有效</option>";
    headOption = headOption + "<option value='1'>无效</option>";
  } else {
    headOption = headOption + "<option value=''>请选择</option>";
    headOption = headOption + "<option value='0'>有效</option>";
    headOption = headOption + "<option value='1'>无效</option>";
  }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
  var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
    headOption + '</select>';
  return option;
}


function configAssess(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已评价</option>";
        headOption = headOption + "<option value='0'>未评价</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未评价</option>";
        headOption = headOption + "<option value='1'>已评价</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未评价</option>";
        headOption = headOption + "<option value='1'>未评价</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}


function configComment(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已评论</option>";
        headOption = headOption + "<option value='0'>未评论</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未评论</option>";
        headOption = headOption + "<option value='1'>已评论</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未评论</option>";
        headOption = headOption + "<option value='1'>未评论</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}


function configLearn(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已学习</option>";
        headOption = headOption + "<option value='0'>未学习</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未学习</option>";
        headOption = headOption + "<option value='1'>已学习</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未学习</option>";
        headOption = headOption + "<option value='1'>未学习</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}

function configShare(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已分享</option>";
        headOption = headOption + "<option value='0'>未分享</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未分享</option>";
        headOption = headOption + "<option value='1'>已分享</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未分享</option>";
        headOption = headOption + "<option value='1'>未分享</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}

function configGraduation(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已结业</option>";
        headOption = headOption + "<option value='0'>未结业</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未结业</option>";
        headOption = headOption + "<option value='1'>已结业</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未结业</option>";
        headOption = headOption + "<option value='1'>未结业</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}


function configScore(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已得分</option>";
        headOption = headOption + "<option value='0'>未得分</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未得分</option>";
        headOption = headOption + "<option value='1'>已得分</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未得分</option>";
        headOption = headOption + "<option value='1'>未得分</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}

function ifEssence(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已精华</option>";
        headOption = headOption + "<option value='0'>未精华</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未精华</option>";
        headOption = headOption + "<option value='1'>已精华</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未精华</option>";
        headOption = headOption + "<option value='1'>未精华</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}

function questionThumbsUp(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已得分</option>";
        headOption = headOption + "<option value='0'>未得分</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未得分</option>";
        headOption = headOption + "<option value='1'>已得分</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未得分</option>";
        headOption = headOption + "<option value='1'>未得分</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}

function configDaySucc(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>失败</option>";
        headOption = headOption + "<option value='0'>成功</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>成功</option>";
        headOption = headOption + "<option value='1'>失败</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>成功</option>";
        headOption = headOption + "<option value='1'>成功</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}



function ifRelease(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>已发布</option>";
        headOption = headOption + "<option value='0'>未发布</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>未发布</option>";
        headOption = headOption + "<option value='1'>已发布</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>未发布</option>";
        headOption = headOption + "<option value='1'>已发布</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}

function ifCommon(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>常见</option>";
        headOption = headOption + "<option value='0'>不常见</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>不常见</option>";
        headOption = headOption + "<option value='1'>常见</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>不常见</option>";
        headOption = headOption + "<option value='1'>常见</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:100px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}


function visualRange(value) {
    var headOption = "";
    if ("1" == value) {
        headOption = headOption + "<option value='1'>仅当前学员自己可见</option>";
        headOption = headOption + "<option value='0'>平台所有人可见</option>";
    } else if ("0" == value) {
        headOption = headOption + "<option value='0'>平台所有人可见</option>";
        headOption = headOption + "<option value='1'>仅当前学员自己可见</option>";
    } else {
        headOption = headOption + "<option value=''>请选择</option>";
        headOption = headOption + "<option value='0'>平台所有人可见</option>";
        headOption = headOption + "<option value='1'>仅当前学员自己可见</option>";
    }
// headOption = headOption + "<option value='"+obj.registerId+"'>"+obj.registerNumber+"</option>";
    var option = '<select class="form-control" id="selNumber"' + value + ' name="registerName" style="height:33px;width:200px;" disabled="disabled">' +
        headOption + '</select>';
    return option;
}






