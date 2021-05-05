const stringUrl = window.location.href;
const idx = stringUrl.lastIndexOf('?');
const path = stringUrl.substring(0, idx);
const url = new URL(window.location.href);
const id = url.searchParams.get("id");

const video = document.getElementById("video");
const source = document.createElement("source");

source.setAttribute('src', path+"/stream?id="+id);
source.setAttribute('type', 'video/mp4');
video.appendChild(source);
