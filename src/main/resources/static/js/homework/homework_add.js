window.addEventListener("DOMContentLoaded", function () {
    const studyGroup = document.getElementById("studyGroup");
    studyGroup.addEventListener("change", function() {
        const selectedIndex = studyGroup.selectedIndex;
        const selectedOption = studyGroup.options[selectedIndex];
        const maxLevel = selectedOption.getAttribute('data-maxLevel');

        const viewLevel = document.getElementById("viewLevel");
        viewLevel.innerHTML = maxLevel;

        const studyLevel = document.getElementById("studyLevel");
        studyLevel.max = maxLevel;
    });
});