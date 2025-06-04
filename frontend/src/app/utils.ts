export function parseDescription(raw: string) {
    let image = raw.match(/<image\s+src="(.*?)"/)?.[1] || '';
    if (image.startsWith('../')) {
        image = image.slice(2);
    } 
    return {
        image,
        text: raw.match(/<span\s+id="itemDescription">(.*?)<\/span>/)?.[1]?.trim() || '',
    };
}