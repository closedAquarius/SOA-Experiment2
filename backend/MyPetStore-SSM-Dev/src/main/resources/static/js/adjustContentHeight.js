function adjustContentHeight()
{
    const content = document.getElementById('Content');
    const footerHeight = 74; // 底部导航栏的高度
    const elements = document.querySelectorAll('#Content *'); // 选择 #Content 中的所有内容

    // 获取浏览器高度和 #Content 底部位置
    const viewportHeight = window.innerHeight;
    const contentRect = content.getBoundingClientRect();

    // 判断是否有元素的 `top` 值在浏览器底部 74px 范围内
    let isOverlappingFooter = false;
    let maxElementTop = 0;

    elements.forEach((element) =>
    {
        const rect = element.getBoundingClientRect();
        maxElementTop = Math.max(maxElementTop, rect.top);
        if (rect.top > viewportHeight - footerHeight && rect.top < viewportHeight)
        {
            isOverlappingFooter = true;
        }
    });

    let newHeight;
    // 动态调整 #Content 的高度
    if (isOverlappingFooter && maxElementTop > (viewportHeight * 0.8 + footerHeight))
    {
        newHeight = `${maxElementTop + footerHeight}px`
    }
    else if (isOverlappingFooter)
    {
        newHeight = `calc(80% + ${footerHeight}px)`;
    }
    else
    {
        newHeight = '80%';
    }

    content.style.height = newHeight;
}

// 初始检查
adjustContentHeight();

// 监听窗口大小变化
window.addEventListener('resize', adjustContentHeight);