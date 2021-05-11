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
