export function parseDescription(raw: string) {
    console.log('原始description数据:', raw); // 调试日志
    let image = raw.match(/<image\s+src="(.*?)"/)?.[1] || '';
    console.log('提取的图片路径:', image); // 调试日志
    
    if (image.startsWith('../images/')) {
        // 从 ../images/bird2.gif 转换为 /images/bird2.gif
        image = '/images/' + image.replace('../images/', '');
    } else if (image.startsWith('images/')) {
        image = '/' + image;
    } else if (image && !image.startsWith('/')) {
        // 如果路径不是绝对路径，添加/images/前缀
        image = '/images/' + image;
    }
    
    // 确保路径指向前端服务器，而不是后端网关
    // 图片应该直接从 http://localhost:3000/images/ 加载
    console.log('最终图片路径:', image); // 调试日志
    
    return {
        image,
        text: raw.match(/<span\s+id="itemDescription">(.*?)<\/span>/)?.[1]?.trim() || '',
    };
}