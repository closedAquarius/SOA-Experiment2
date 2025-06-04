function checkUsername(username){
    if(username === null || username === "" || username.length === 0){
        return '用户名不能为空';
    }
    if(username.length <=3 ){
        return '用户名长度必须大于3';
    }
    return '';
}

function checkPassword(password) {
    if (password === null || password === "" || password.length === 0) {
        return '密码不能为空';
    }
    if (password.length <= 3) {
        return '密码长度必须大于3';
    }
    return '';
}

function adjustPosition()
{
    let username = $('#username');
    let usernameOffset = username.offset();

    let password = $('#password');
    let passwordOffset = password.offset();

    let repeatPassword = $('#repeatPassword');
    let repeatPasswordOffset = repeatPassword.offset();

    let email = $('#email');
    let emailOffset = email.offset();

    let registerForm = $('#registerForm');
    let registerFormOffset = registerForm.offset();

    let usernameFeedback = $('#usernameFeedback');
    let passwordFeedback=$('#passwordFeedback');
    let repeatPasswordFeedback=$('#repeatPasswordFeedback');
    let emailFeedback=$('#emailFeedback');

    usernameFeedback.css(
        {
            top: usernameOffset.top - registerFormOffset.top,
            left: usernameOffset.left - registerFormOffset.left + 265,
        }
    )
    passwordFeedback.css(
        {
            top: passwordOffset.top - registerFormOffset.top,
            left: passwordOffset.left - registerFormOffset.left + 265,
        }
    )
    repeatPasswordFeedback.css(
        {
            top: repeatPasswordOffset.top - registerFormOffset.top,
            left: repeatPasswordOffset.left - registerFormOffset.left + 265,
        }
    )
    emailFeedback.css(
        {
            top: emailOffset.top - registerFormOffset.top,
            left: emailOffset.left - registerFormOffset.left - 250,
        }
    )
}

$(function (){

    adjustPosition();

    $('#username').on('blur',function (){
        let usernameFeedback = $('#usernameFeedback');
        usernameFeedback.text('');

        const username = $(this).val();
        let result = checkUsername(username);

        if(result !== ''){
            usernameFeedback.text(result);
        }else{
            $.ajax({
                type    :'GET',
                url     :'http://localhost:8090/user/examination?username='+username,
                success :function (data){
                    console.log(data);
                    if(data === "true"){
                        usernameFeedback.text('用户名已存在');
                    }else{
                        usernameFeedback.text('');
                    }
                },
                error   : function (errorMsg) {
                    console.log(errorMsg);
                }
            });
        }
    });

    $('#password').on('blur',function (){
        let passwordFeedback=$('#passwordFeedback');
        passwordFeedback.text('');
        const password = $(this).val();
        let result = checkPassword(password);
        if(result !== ''){
            passwordFeedback.text(result);
        }
    });

    $('#repeatPassword').on('blur',function ()
    {
        let repeatPasswordFeedback=$('#repeatPasswordFeedback');
        repeatPasswordFeedback.text('');
        const repeatPassword = $(this).val();
        const password = $('#password').val();
        if(repeatPassword !== password){
            repeatPasswordFeedback.text('两次密码不一致');
        }
    });

    $('#email').on('blur',function ()
    {
        const email=$(this).val();
        console.log(email);
        //正则表达式来验证电子邮件格式
        const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if(regex.test(email)){
            $('#emailFeedback').text('');
        }else{
            $('#emailFeedback').text('邮箱格式不正确');
        }
    });
})