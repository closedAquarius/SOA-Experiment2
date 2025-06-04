const submitElement = document.getElementById('submit');
const backLinkElement = document.getElementById('BackLink');
const contentElement = document.getElementById('Content');

function adjustBackLinkTop()
{
    const submitRect = submitElement.getBoundingClientRect();
    const submitTop = submitRect.top;
    const contentRect = contentElement.getBoundingClientRect();
    const contentTop = contentRect.top;

    //计算#BackLink的top值
    const backLinkTop = submitTop - contentTop;
    backLinkElement.style.top = `${backLinkTop}px`;
}

adjustBackLinkTop();
