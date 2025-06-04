$(function (){
    $('input[type="text"]').on('blur', function() {
        let $input = $(this);
        let quantity = $(this).val();
        let itemId = $(this).attr('data-itemId'); // 使用 .attr() 方法获取属性值
        let $row = $(this).closest('tr');
        // 在该 tr 元素中找到类名为 'total' 的 div
        let $totalDiv = $row.find('div.total');
        console.log(quantity);
        console.log(itemId);
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8090/user/updateCartItem',
            data: { itemId: itemId, quantity: quantity }, // 使用 data 对象传递参数
            success:function (data){
                if(data['isOk'] == null){
                    if(quantity == 0){
                        $row.remove();
                        alert('delete success');
                    }else{
                        $totalDiv.text(data['cartItemTotal']);
                        alert('update success')
                    }
                    $('#subTotal').text('Sub Total:'+data['subTotal']);
                }else{
                    console.log(data['isOk']);
                    $input.val(data['isOk']);
                    alert('Not enough stock')
                }
            }
        });
    });

})