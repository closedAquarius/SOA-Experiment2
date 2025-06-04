$(function (){
    var isOverInfo = false;
    $('#userAddresses').on('click','.Button',function(){
        let addressId = $(this).data('addrid');
        let id = $(this).attr('id');
        let $div = $(this).closest('div');
        console.log($div);
        if(id==='choose'){
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8090/order/chooseAddress?addressId='+addressId,
                success: function(data){

                        $('#firstname').val(data.firstName);
                        $('#lastname').val(data.lastName);
                        $('#address1').val(data.address1);
                        $('#address2').val(data.address2);
                        $('#city').val(data.city);
                        $('#state').val(data.state);
                        $('#zip').val(data.zip);
                        $('#country').val(data.country);
                        console.log(data)
                }
            });
        }else if(id==='delete') {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8090/order/deleteAddress?addressId=' + addressId,
                success: function (data) {
                    if (data == 'delete success') {
                        alert('delete success');
                        $div.remove();
                    }
                }
            });
        }else if(id==='setMain'){
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8090/order/setAddress?addressId=' + addressId,
                success: function (data) {
                    console.log(data);
                    if (data == 'set main success') {
                        alert('set main success');
                    }
                }
            });
        }
    });

    /*$('#userAddresses').on('mousemove','#address',function(event){
        let AutoAddress=$('#AutoAddress');
        if (AutoAddress.css('display') === 'block') {
            AutoAddress.css({
                left: event.pageX + 10,
                top: event.pageY + 10
            });
        }
    });*/

    $('#AutoAddress').on('mouseenter', function() {
        $(this).css('display', 'block'); // 确保 info 显示
        isOverInfo = true;
    });
    $('#AutoAddress').on('mouseleave', function(event) {
        let info=$('#info')
        if (info.css('display') === 'block')
        {
            let parentContainer = $('#userAddresses');  //父容器的选择器

            //获取父容器相对于页面的偏移量
            let parentOffset = parentContainer.offset(); // { top: 父容器的top, left: 父容器的left }

            //计算鼠标在父容器中的相对位置
            let relativeX = event.pageX - parentOffset.left;
            let relativeY = event.pageY - parentOffset.top;

            info.css({
                left: 360,
                top: relativeY + 10,
                animation: 'collapse 0.3s forwards'
            });
        }
        isOverInfo = false;
    });

    $('#userAddresses').on('mouseenter','#address',function (event) {
        let addressId = $(this).data('addrid');
        let id = 'getDetails';
        let AutoAddress=$('#AutoAddress');
        AutoAddress.html('');
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/mypetstore/changeAddress?addressId='+addressId+'&isDelete='+id,
            success: function (data) {
                let parentContainer = $('#userAddresses');  //父容器的选择器

                //获取父容器相对于页面的偏移量
                let parentOffset = parentContainer.offset(); // { top: 父容器的top, left: 父容器的left }

                //计算鼠标在父容器中的相对位置
                let relativeX = event.pageX - parentOffset.left;
                let relativeY = event.pageY - parentOffset.top;

                AutoAddress.css({
                    display: 'block',
                    left: 360,
                    top: relativeY + 10,
                    animation: 'expand 0.6s forwards'
                });
                let AutoAddressHTML='<li class="Auto">'
                    +data.firstName+' '+data.lastName
                    +'</li>'
                    +'<li class="Auto">'
                    +data.address1+' | '+data.address2
                    +'</li>'
                    +'<li class="Auto">'
                    +data.city+' | '+data.state+' | '+data.zip+' | '+data.country
                    +'</li>';
                AutoAddress.html(AutoAddressHTML);
                setTimeout(function () {
                    fadeInText(AutoAddress.find('li')); // 调用淡入函数
                }, 600); // 延迟时间与提示框动画时间一致
            }
        });
    });

    $('#userAddresses').on('mouseleave','#address',function () {
        let AutoAddress=$('#AutoAddress');
        if (!isOverInfo){
            AutoAddress.css({
                display: 'none',
            });
            AutoAddress.hide();
        }
    });

    // 淡入文字的函数
    function fadeInText(elements) {
        elements.each(function (index, element) {
            let opacity = 0;
            let interval = setInterval(function () {
                opacity += 0.1; // 每次增加 0.1 的透明度
                $(element).css('opacity', opacity); // 设置透明度
                if (opacity >= 1) {
                    clearInterval(interval); // 当透明度达到 1 时，停止递增
                }
            }, 30); // 每30毫秒更新一次
        });
    }
});