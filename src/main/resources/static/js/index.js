const button = document.querySelector('.show-more-btn');
const videosList = document.querySelector('.videos');
let rowCounter = 0;

const getMoreVideos = async size => {
    fetch(`/more_videos?row=${rowCounter}&size=${size}`)
        .then(response => {
            response.text()
                .then(text => {
                    videosList.innerHTML += text;
                    videosList.style.gridTemplateRows += '1fr';
                });
        });
}

button.onclick = async () => {
    getMoreVideos(5).then(() => rowCounter++)
};

getMoreVideos(5).then(() => rowCounter++);
