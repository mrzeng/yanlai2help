
//初始化上传按钮
// ../../helpcenter/videosAndBooks
$(function () {
    //上传
    $("#file_self").ace_file_input({
        no_file: '请选择文件 ...',
        btn_choose: '选择',
        btn_change: '更改',
        droppable: false,
        onchange: null,
        thumbnail: false, //| true | large
        whitelist: 'txt|doc|pdf|mp4|avi|3gp'
    });

});

function getSize(obj) {
    var fileSize = 0;
    var fileMaxSize = 1024;//1G
    var filePath = obj.value;
    var size = 0;
    if (filePath) {
        fileSize = obj.files[0].size;
        size = fileSize / 1024;
        size = size / 1024;
        $("#detail_fil_size").val("");
        if (size > fileMaxSize) {
            alertWarning("文件大小不能大于1G！");
            file.value = "";
            return false;
        } else if (size <= 0) {
            alertWarning("文件大小不能为0M！");
            file.value = "";
            return false;
        }
        var fileSize = size.toString();
        if (size.toString().length > 4) {
            fileSize = fileSize.substring(0, 4);
        }
        $("#detail_fil_size").val(fileSize);
        return true;
    } else {
        return false;
    }
}

function getPath(obj) {
    if (obj) {
        if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
            obj.select();
            return document.getSelection().createRange().text;
        }
        else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
            if (obj.files) {
                return window.URL.createObjectURL(obj.files[0]);
            }
            return obj.value;
        }
        return obj.value;
    }
}


//过滤类型
function fileType(obj) {
    var fileType = obj.value.substr(obj.value.lastIndexOf("."))
        .toLowerCase();//获得文件后缀名

    if (fileType != '.WMV' && fileType != '.wmv' && fileType != '.AVI' && fileType != '.mov' && fileType != '.rmvb' && fileType != '.rm'
        && fileType != '.FLV' && fileType != '.mp4' && fileType != '.3GP' && fileType != '.avi'
        && fileType != '.MOV' && fileType != '.RMVB' && fileType != '.RM' && fileType != '.flv'
        && fileType != '.MP4' && fileType != '.3gp' && fileType != '.TXT' && fileType != '.DOC' && fileType != '.PDF' && fileType != '.pdf'
        && fileType != '.XLS' && fileType != '.PPT' && fileType != '.DOCX' && fileType != '.XLSX'
        && fileType != '.PPTX' && fileType != '.txt' && fileType != '.doc' && fileType != '.xls'
        && fileType != '.ppt' && fileType != '.docx' && fileType != '.xlsx' && fileType != '.pptx') {
        $("#file_self").val('');
        document.getElementById("file_self").files[0] = '请选择文件';
        $("#detail_fil_type").val("");
        alertSixInfo("格式不符合规范！提示[视频：mp4、3gp、avi等；手册：doc、pdf等]");
    } else {
        getSize(obj);
        $("#detail_fil_type").val(fileType + "");
    }
}
