$(function (){
    $('#keyword').on('keyup',function (){
        let keyword = $(this).val();
        console.log(keyword);
        if(keyword !== ''&&keyword !== null && keyword.length !== 0){
            $.ajax({
                type    :'GET',
                url     :'http://localhost:8090/catalog/productAuto?keyword='+keyword,
                success :function (data){
                    console.log(data);
                    let productListHTML = '';
                    for(let i=0;i<data.length;i++){
                        productListHTML +=
                            '<li class=\"productAutoItem\" data-productId="'
                            +data[i].productId
                            +'">'
                            +data[i].categoryId+' | '
                            +data[i].name+'</li>';
                    }
                    $('#productAutoList').html(productListHTML);
                    $('#productAutoComplete').show();
                },
                error   : function (errorMsg)
                {
                    console.log(errorMsg);
                }
            })
        }
        else{
            $('#productAutoComplete').hide();
        }
    });

    /*$('.productAutoItem').on('click',function (){

    })*/
    //动态绑定,因为li是动态生成的
    $(document).on('click','.productAutoItem',function()
    {
        const productId = $(this).data('productid');
        $('#productAutoComplete').hide();
        $('#keyword').val('');
        window.location.href="http://localhost:8080/mypetstore/productForm?productId="+productId;
    });

    $('#productAutoComplete').on('mouseleave',function ()
    {
        $(this).hide();
        $('#keyword').val('');
    })
})