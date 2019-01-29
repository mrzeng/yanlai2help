var realPath = "";
var ALLENCRYPTCODE = "xzxzxzxzxzxzxzxz";
function getRealPath() {
  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
  var curWwwPath = window.document.location.href;
  //获取主机地址之后的目录，如： myproj/view/my.jsp
  var pathName = window.document.location.pathname;
  var pos = curWwwPath.indexOf(pathName);
  //获取主机地址，如： http://localhost:8083
  var localhostPaht = curWwwPath.substring(0, pos);
  //获取带"/"的项目名，如：/myproj
  var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
  //得到了 http://localhost:8083/myproj
  realPath = localhostPaht + projectName;
}
getRealPath();

function isNumber(value) {
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false
    } else {
        return true
    }
}




