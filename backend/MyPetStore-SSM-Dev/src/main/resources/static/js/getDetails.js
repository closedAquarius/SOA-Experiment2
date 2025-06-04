function greet() {
    const hour = new Date().getHours(); // 获取当前小时数
    if (hour >= 4 && hour < 11) {
        return "Good morning";
    }else if (hour >= 11 && hour < 14) {
        return "Good noon!";
    } else if (hour >= 14 && hour < 18) {
        return "Good afternoon";
    } else if (hour >= 18 || hour < 4) {
        return "Good evening";
    } else {
        console.log("时间不在下午或晚上，请重新输入");
    }
}
$(function (){
    /*$('#MainImageContent').on('mousemove', function(event) {
        if (!$('#info').is(':hover')){
            let info=$('#info')
            if (info.css('display') === 'block') {
                info.css({
                    left: event.pageX + 10,
                    top: event.pageY + 10
                });
            }
        }

    });*/

    $('#WelcomeText').text(greet());

    $('#info').on('click','.productAutoItem',function(){
        const productId = $(this).data('productid');
        console.log(productId);
        window.location.href="http://localhost:8090/catalog/viewProduct?productId="+productId;
        $('#productAutoComplete').hide();
    });
    $('#info').on('click','.itemAutoItem',function(){
        const itemId = $(this).data('itemid');
        console.log(itemId);
        window.location.href="http://localhost:8090/catalog/viewItem?itemId="+itemId;
        $('#productAutoComplete').hide();
    });

    let isOverInfo = false;
    //添加 mouseenter 和 mouseleave 事件处理程序到 info 元素
    $('#info').on('mouseenter', function()
    {
        $(this).css('display', 'block'); // 确保 info 显示
        isOverInfo = true;
    });
    $('#info').on('mouseleave', function()
    {
        let info=$('#info')

        let parentContainer = $('#MainImage');  //父容器的选择器

        //获取父容器相对于页面的偏移量
        let parentOffset = parentContainer.offset(); // { top: 父容器的top, left: 父容器的left }

        //计算鼠标在父容器中的相对位置
        let relativeX = event.pageX - parentOffset.left;
        let relativeY = event.pageY - parentOffset.top;

        if (info.css('display') === 'block') {
            info.css({
                /*
                left: event.pageX + 10,
                top: event.pageY + 10*/
                left: relativeX + 10,
                top: relativeY + 10,
                animation: 'collapse 0.3s forwards'
            });
        }
        isOverInfo = false;
    });

    $('map area').on('mouseleave', function(event) {
        let info=$('#info');
        if (!isOverInfo){
            info.hide();
        }

    });

    $('map area').on('mouseenter',function (event){
        let info=$('#info')
        info.html('');
        let categoryId=this.id;
        console.log(categoryId);
        if(categoryId=='BirdsBig'||categoryId=='BirdsSmall'){
            categoryId='Birds';
        }
        $.ajax({
            type:    'GET',
            url:     'http://localhost:8090/catalog/getDetails?categoryId='+categoryId,
            success :function (data){
                if(data !== null){
                    console.log(data);
                    let parentContainer = $('#MainImage');  //父容器的选择器

                    //获取父容器相对于页面的偏移量
                    let parentOffset = parentContainer.offset(); // { top: 父容器的top, left: 父容器的left }

                    //计算鼠标在父容器中的相对位置
                    let relativeX = event.pageX - parentOffset.left;
                    let relativeY = event.pageY - parentOffset.top;

                    info.css({
                        display: 'block',
                        /*left: event.pageX + 10,
                        top: event.pageY + 10*/
                        left: relativeX + 10,
                        top: relativeY + 10,
                        animation: 'expand 0.6s forwards'
                    });
                    let infoHTML='';
                    for(let i=0;i<data.length;i++){
                        infoHTML += '<li class="Auto"><span class=\"productAutoItem\" data-productId="'
                            +data[i].productId
                            +'">'
                            +data[i].productId
                            +'</span>'
                            +' | '
                            +'<span class=\"itemAutoItem\" data-itemId="'
                            +data[i].itemId
                            +'">'
                            +data[i].attribute1+'</span></li>';
                    }
                    info.html(infoHTML);

                    //info.show();

                    // 延迟淡入文字
                    setTimeout(function () {
                        fadeInText(info.find('li')); // 调用淡入函数
                    }, 600); // 延迟时间与提示框动画时间一致
                }
            }

        });
    });

    $(document).on('click','.productAutoItem',function()
    {
        const productId = $(this).data('productid');
        $('#productAutoComplete').hide();
        $('#keyword').val('');
        window.location.href="http://localhost:8090/catalog/viewProduct?productId="+productId;
    });
    $(document).on('click','.itemAutoItem',function()
    {
        const productId = $(this).data('itemid');
        $('#productAutoComplete').hide();
        $('#keyword').val('');
        window.location.href="http://localhost:8090/catalog/viewItem?itemId="+productId;
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
})