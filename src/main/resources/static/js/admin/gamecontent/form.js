window.addEventListener("DOMContentLoaded", function() {
    const originalPrice = document.getElementById("originalPrice");
    const discountRate = document.getElementById("discountRate");
    const salePrice = document.getElementById("salePrice");
    originalPrice.addEventListener("blur", function() {
        salePrice.value = originalPrice.value * (100 - discountRate.value) / 100;
    });
    discountRate.addEventListener("blur", function() {
            salePrice.value = Math.round((originalPrice.value * (100 - discountRate.value) / 100) / 10) * 10 ;
    });
});
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