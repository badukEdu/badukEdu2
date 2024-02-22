function callbackFileUpload(files) {
    if (files == null || files.length == 0) return;

    const file = files[0];

    const img = new Image();
    img.src = file.fileUrl;

    const thumbEl = document.getElementById("thumbnail_img");
    thumbEl.innerHTML = "";
    thumbEl.appendChild(img);

    const aLink = document.createElement("a");
    aLink.innerHTML = "[삭제]"
    aLink.href=`/file/delete/${file.seq}`;
    aLink.target="ifrmProcess";
    aLink.addEventListener("click", function(e) {
        if (!confirm("정말 삭제하시겠습니까?")) {
            e.preventDefault();
        }
    });

    thumbEl.appendChild(aLink);

}

function callbackFileDelete(seq) {
    const thumbEl = document.getElementById("thumbnail_img");
    thumbEl.innerHTML = "";
}