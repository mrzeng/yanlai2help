/*
 *
 * login-register modal
 * Autor: Creative Tim
 * Web-autor: creative.tim
 * Web script: #
 * 
 */
function showRegisterForm() {
    alertInfo("消息提醒：请联系运维人员！")
}

function showLoginForm() {
    $('#loginModal .registerBox').fadeOut('fast', function () {
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast', function () {
            $('.login-footer').fadeIn('fast');
        });

        $('.modal-title').html('Login with');
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function openLoginModal() {
    showLoginForm();
    setTimeout(function () {
        $('#loginModal').modal('show');
    }, 230);

}

openLoginModal();

function openRegisterModal() {
    showRegisterForm();
    setTimeout(function () {
        $('#loginModal').modal('show');
    }, 230);

}

function loginAjax() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (null == username || null == password || "" == username || "" == password) {
        alertWarning("消息提醒：用户名密码是必输项!")
    }
    var key = CryptoJS.enc.Utf8.parse(ALLENCRYPTCODE);
    // Encrypt
    //把私钥转换成16进制的字符串
    var encrypted = CryptoJS.AES.encrypt(password, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    password = encrypted.toString();
    $.ajax({
        type: "POST",
        url: "app/loginIn",
        contentType: "application/json",
        data: JSON.stringify({
            "password": password,
            "username": username
        }),
        success: function (data, status) {
            var jsonObj = JSON.parse(data);
            if (status == "success" && jsonObj.code == "1000") {
                alertSuccess('提示信息：登陆成功！');
                $('#loginModal').modal('hide');
                window.location.href = "/helpcenter/index";
            } else {
                alertDanger('提示信息：登陆失败，请检查您的用户名或密码是否输入正确！');
                // shakeModal();
            }
        },
        error: function () {
            alertDanger('提示信息：发生系统错误,请联系运维人员进行排查。！');
        },
        complete: function () {
        }
    });


    /*   Simulate error message from the server   */

}

function loginOutAjax() {
    $.ajax({
        type: "POST",
        url: "app/loginOut",
        contentType: "application/json",
        data: JSON.stringify({
            "userid": "null"
        }),
        success: function (data, status) {
            var jsonObj = JSON.parse(data);
            if (status == "success" && jsonObj.code == "1000") {
                alertSuccess('提示信息：退出成功！');
                $('#loginModal').modal('show');
                window.location.href = "/helpcenter";
            } else {
                window.location.href = "/helpcenter/";
            }
        },
        error: function () {
            alertDanger('提示信息：发生系统错误,请联系运维人员进行排查。！');
        },
        complete: function () {
        }
    });
}

$('#updPasswordDiv').modal('show');

function loginUpdAjax() {
    $('#updPasswordDiv').modal('show');
    $("#updpassword").val("");
    $("#updNewpassword").val("");
}

function confirmSave() {
    var updPassword = $("#updPassword").val();
    var updNewpassword = $("#updNewpassword").val();
    if (null == updPassword || null == updNewpassword || "" == updPassword || "" == updNewpassword) {
        alertWarning('提示信息：新旧密码均为必输项！');
        return;
    }
    if(updNewpassword === updPassword){
        alertWarning('提示信息：新密码与旧密码相同！');
        return;
    }
    var key = CryptoJS.enc.Utf8.parse(ALLENCRYPTCODE);
    // Encrypt
    //把私钥转换成16进制的字符串
    var encrypted = CryptoJS.AES.encrypt(updNewpassword, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    updNewpassword = encrypted.toString();

    var encrypted = CryptoJS.AES.encrypt(updPassword, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    updPassword = encrypted.toString();
    $.ajax({
        type: "POST",
        url: "app/updUser",
        contentType: "application/json",
        data: JSON.stringify({
            "password": updPassword,
            "newpassword": updNewpassword
        }),
        success: function (data, status) {
            var jsonObj = JSON.parse(data);
            if (status == "success" && jsonObj.code == "1000") {
                alert("修改成功!");
                /* 弹出框关闭*/
                $('#updPasswordDiv').modal('hide');
                window.location.href = "/helpcenter";
            } else {
                alertSuccess('提示信息：修改失败！');
            }
        },
        error: function () {
            alertDanger('提示信息：发生系统错误,请联系运维人员进行排查。！');
        },
        complete: function () {
        }
    });

}


function shakeModal() {
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html("用户名或密码输入不正确");
    $('input[type="password"]').val('');
    setTimeout(function () {
        $('#loginModal .modal-dialog').removeClass('shake');
    }, 1000);
}

