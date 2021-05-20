const button = document.querySelector('.show-more-btn');
const videosList = document.querySelector('.videos');
const videosListItems = videosList.childNodes;

let rowCounter = 0;
let rowSize = 5;

const getMoreVideos = async () => {
    fetch(`/more_videos?row=${rowCounter}&size=${rowSize}`)
        .then(response => {
            response.text()
                .then(text => {
                    videosList.innerHTML += text;
                    videosList.style.gridTemplateRows += '1fr';
                });
        });
}

button.onclick = () => {
    getMoreVideos().then(() => rowCounter++)
};

const mediaQueries = [
    window.matchMedia('(max-width:480px)'),
    window.matchMedia('(min-width:480px) and (max-width:768px)'),
    window.matchMedia('(min-width:768px) and (max-width:1200px)'),
    window.matchMedia('(min-width:1200px) and (max-width:1680px)')
]

const handleWidthChange = () => {
    if (mediaQueries[0].matches) {
        rowSize = 1;
    }
    else if (mediaQueries[1].matches) {
        rowSize = 2;
    }
    else if (mediaQueries[2].matches) {
        rowSize = 3;
    }
    else if (mediaQueries[3].matches) {
        rowSize = 4;
    }
    else {
        rowSize = 5;
    }
    rowCounter = Math.floor( videosListItems.length / rowSize);
    const excessAmount = videosListItems.length % rowSize;
    if (excessAmount > 0) {
        for (let i = 0; i < excessAmount; i++) {
            videosList.lastChild.remove();
        }
        getMoreVideos().then(() => rowCounter++);
    }
}

for (const mediaQuery of mediaQueries) {
    mediaQuery.addEventListener('change', handleWidthChange)
}
handleWidthChange();

getMoreVideos().then(() => rowCounter++);
