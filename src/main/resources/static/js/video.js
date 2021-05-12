'use strict'
const stringUrl = window.location.href;
const idx1 = stringUrl.lastIndexOf('?');
const path = stringUrl.substring(0, idx1);
const url = new URL(window.location.href);
const id = url.searchParams.get("id");

const video = document.getElementById("video");
const source = document.createElement("source");
const span = document.getElementById("detailsInfo")

const request = new XMLHttpRequest;
const text = span.textContent;
const idx2 = text.indexOf(" ");
let views = parseInt(text.substring(0, idx2));
const data = text.substring(idx2, text.length);

let flag = true;
video.addEventListener("play", () => {
    if (flag) {
        setTimeout(() => {
            request.open("POST", "/video?id=" + id);
            request.send();
            views++;
            document.getElementById("detailsInfo").textContent = views + data;
            flag = false;
        }, 3000);
    }
});

source.setAttribute('src', path+"/stream?id="+id);
source.setAttribute('type', 'video/mp4');
video.appendChild(source);

const visibleDescription = document.getElementById("description");
const wholeDescription = visibleDescription.textContent;
const descriptionLength = wholeDescription.length;
if (descriptionLength <= 300) {
    document.getElementById("dots").hidden = true;
    document.getElementById("description_btn").hidden = true;
} else {
    document.getElementById("description").textContent = wholeDescription.substring(0, 299);
    document.getElementById("more_description").textContent = wholeDescription.substring(299);
    document.getElementById("more_description").hidden = true;
}

let isShownWhole = false;
function showDescription() {
    if (isShownWhole) {
        document.getElementById("dots").hidden = false;
        document.getElementById("more_description").hidden = true;
        document.getElementById("description_btn").textContent = "Show more";
        isShownWhole = false;
    } else {
        document.getElementById("dots").hidden = true;
        document.getElementById("more_description").hidden = false;
        document.getElementById("description_btn").textContent = "Show less";
        isShownWhole = true;
    }
}
